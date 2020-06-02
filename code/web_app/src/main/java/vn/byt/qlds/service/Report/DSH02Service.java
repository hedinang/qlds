package vn.byt.qlds.service.Report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.IncludeExclude;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DSH.DSH02.DSH02;
import vn.byt.qlds.model.report.DSH.DSH02.DSH02Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class DSH02Service extends ReportService {

    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, ParseException {
        this.reportTemplate = new DSH02();
        int monthStart = request.quarter * 3 - 2;
        int monthEnd = request.quarter * 3;
        String formatDate = "%2d/%02d/%d";
        request.startTime = String.format(formatDate, 1, monthStart, request.year);
        request.endTime = String.format(formatDate, YearMonth.of(request.year, monthEnd).lengthOfMonth(), monthEnd, request.year);
        super.generateReport(request);
        Map<String, DSH02Sub> codeToDSH02Sub = new HashMap<>();
        JsonNode hitsCommune = getCommune(request);
        JsonNode jsonHouseHold = counterHouseHold(request);
        JsonNode jsonResidence = counterResidence(request);
        JsonNode jsonFemaleFrom15To49 = counterFemaleFrom15To49(request);
        JsonNode jsonPersonChange = counterPersonChangeQuarter(request);
        JsonNode jsonMarital = counterPersonMaritalChangeQuarter(request);
        JsonNode jsonFamilyContraceptive = counterFamilyUsedContraceptive(request);

        for (JsonNode commune : hitsCommune) {
            String communeName = commune.findPath("name").asText();
            String communeId = commune.findPath("code").asText();
            DSH02Sub dsh02Sub = new DSH02Sub();
            dsh02Sub.setAddress(communeName);
            codeToDSH02Sub.put(communeId, dsh02Sub);
        }

        /*1. Tổng số hộ dân cư : Đếm số lượng hộ theo địa chỉ tương ứng = Hộ gia đình + hộ tập thể*/
        for (JsonNode houseHold : jsonHouseHold) {
            String regionId = houseHold.path("key").asText();
            int count = houseHold.path("doc_count").asInt();
            if (codeToDSH02Sub.get(regionId) == null) codeToDSH02Sub.put(regionId, new DSH02Sub());
            DSH02Sub dsh02Sub = codeToDSH02Sub.get(regionId);
            dsh02Sub.setTotalHouseHold(count);
            JsonNode bigHouseHolds = houseHold.path("BigHouseHold").path("buckets");

            for (JsonNode bigHouseHold : bigHouseHolds) {
                int key = houseHold.path("key").asInt();
                int countBigHouseHold = bigHouseHold.path("doc_count").asInt();
                if (key == 0) dsh02Sub.setTotalNotBigHouseHold(countBigHouseHold);
            }
        }

        /*2. Tổng số nhân khẩu thường trú : Đếm số người dân có tình trạng cư trú = thường trú tại các đơn vị địa chỉ tương ứng*/
        for (JsonNode residence : jsonResidence) {
            String regionId = residence.path("key").asText();
            int count = residence.path("doc_count").asInt();
            if (codeToDSH02Sub.get(regionId) == null) codeToDSH02Sub.put(regionId, new DSH02Sub());
            DSH02Sub dsh02Sub = codeToDSH02Sub.get(regionId);
            dsh02Sub.setTotalResident(count);
        }

        /*3.  Số nữ 15-49 : đếm số cá nhân nữ thường trú có độ tuổi từ 15-49
         * 4. Số nữ 15-49 tuổi có chồng : Đếm số cá nhân nữ thường trú có độ tuổi từ 15-49 và có tình trạng hôn nhân = có vợ/ chồng
         * */
        for (JsonNode femaleFrom15To49 : jsonFemaleFrom15To49) {
            String regionId = femaleFrom15To49.path("key").asText();
            int count = femaleFrom15To49.path("doc_count").asInt();
            if (codeToDSH02Sub.get(regionId) == null) codeToDSH02Sub.put(regionId, new DSH02Sub());
            DSH02Sub dsh02Sub = codeToDSH02Sub.get(regionId);
            dsh02Sub.setTotalWomenLt49(count);
            JsonNode maritalBuckets = femaleFrom15To49.path("marital").path("buckets");

            for (JsonNode maritalBucket : maritalBuckets) {
                int key = maritalBucket.path("key").asInt();
                int countMarried = maritalBucket.path("doc_count").asInt();
                if (key == Config.MARRIED) dsh02Sub.setTotalWomenLt49HaveHusband(countMarried);
            }
        }

        /*5. Số người chết trong quý : Đếm số cá nhân tử vong và có ngày tử vong trong quý
         * 8. số người chuyển đi khỏi địa phương trong quý : Đếm số người chuyển đi khỏi xã/ phường tại các nghiệp vụ : chuyển hộ, tách hộ ra ngoài phạm vi xã/ phường
         * 9. Số người chuyển đến từ xã khác trong quý : Đếm số người chuyển đến từ các danh sách chuyển đến tại nghiệp vụ chuyển hộ, tách hộ :
         * */
        for (JsonNode personChange : jsonPersonChange) {
            String regionId = personChange.path("key").asText();
            if (codeToDSH02Sub.get(regionId) == null) codeToDSH02Sub.put(regionId, new DSH02Sub());
            DSH02Sub dsh02Sub = codeToDSH02Sub.get(regionId);
            JsonNode maritalBuckets = personChange.path("changeTypeCode").path("buckets");
            for (JsonNode maritalBucket : maritalBuckets) {
                int key = maritalBucket.path("key").asInt();
                int docCount = maritalBucket.path("doc_count").asInt();
                if (key == Config.DIE) dsh02Sub.setTotalDiePerson(docCount);
                if (key == Config.CHUYEN_DI) dsh02Sub.setTotalGoPerson(docCount);
                if (key == Config.CHUYEN_DEN) dsh02Sub.setTotalComePerson(docCount);
            }
        }

        /*6. Số người kết hôn trong quý : Đếm trạng thái kết hôn trong quý : thông tin hôn nhân có ngày kết hôn trong quý
         * 7. Số người ly hôn trong quý : Đếm kiểu ly hôn trong quý: thông tin hôn nhân có ngày ly hôn
         */
        for (JsonNode marital : jsonMarital) {
            String regionId = marital.path("key").asText();
            if (codeToDSH02Sub.get(regionId) == null) codeToDSH02Sub.put(regionId, new DSH02Sub());
            DSH02Sub dsh02Sub = codeToDSH02Sub.get(regionId);
            JsonNode maritalBuckets = marital.path("notes").path("buckets");
            for (JsonNode maritalBucket : maritalBuckets) {
                String key = maritalBucket.path("key").asText();
                int docCount = maritalBucket.path("doc_count").asInt();
                if (key.equals(Config.NOTE_DA_LY_HON)) dsh02Sub.setTotalDivorcePerson(docCount);
                if (key.equals(Config.NOTE_DA_KET_HON)) dsh02Sub.setTotalMarryPerson(docCount);
            }
        }

        /*
        10. Số cặp vợ chồng trong độ tuổi sinh đẻ hiện đang sử dụng BPTT tính đến cuối quý (cặp) :
         Đếm số lượng cặp vợ chồng sử dụng BPTT, tại 1 hộ chỉ đếm 1 lần cho vợ chồng, 1 hộ không đếm 2 lần cho 2 người
        * */
        for (JsonNode contraceptive : jsonFamilyContraceptive) {
            String regionId = contraceptive.path("key").asText();
            int count = contraceptive.path("doc_count").asInt();
            if (codeToDSH02Sub.get(regionId) == null) codeToDSH02Sub.put(regionId, new DSH02Sub());
            DSH02Sub dsh02Sub = codeToDSH02Sub.get(regionId);
            dsh02Sub.setTotalUseContraceptive(count);

            JsonNode contraceptiveBuckets = contraceptive.path("contraceptive").path("buckets");
            for (JsonNode contraceptiveBucket : contraceptiveBuckets) {
                int key = contraceptiveBucket.path("key").asInt();
                int docCount = contraceptiveBucket.path("doc_count").asInt();
                switch (key) {
                    case Config.VONG_TRANH_THAI:
                        dsh02Sub.setTotalUseRing(docCount);
                        break;
                    case Config.TRIET_SAN_NAM:
                        dsh02Sub.setTotalMenSterilization(docCount);
                        break;
                    case Config.TRIET_SAN_NU:
                        dsh02Sub.setTotalWomenSterilization(docCount);
                        break;
                    case Config.THUOC_CAY:
                        dsh02Sub.setTotalUseImplantDrug(docCount);
                        break;
                    case Config.THUOC_TIEM:
                        dsh02Sub.setTotalUseInjectDrug(docCount);
                        break;
                    case Config.THUOC_UONG:
                        dsh02Sub.setTotalUseDrinkDrug(docCount);
                        break;
                    case Config.BCS:
                        dsh02Sub.setTotalUsePlastic(docCount);
                        break;
                    case Config.BIEN_PHAP_KHAC:
                        dsh02Sub.setTotalUseOtherMethod(docCount);
                        break;
                    case Config.KHONG_SU_DUNG:
                        dsh02Sub.setTotalNoUseContraceptive(docCount);
                        break;
                }
            }
        }


//        for (JsonNode commune : hitsCommune) {
//            String communeName = commune.findPath("name").textValue();
//            String regionCommuneId = commune.findPath("code").textValue();
//            int totalHouseHold = counterHouseHold(regionCommuneId, request, null);
//            int totalNotBigHouseHold = counterHouseHold(regionCommuneId, request, false);
//            int totalResident = counterResidence(regionCommuneId, request);
//            int totalWomenLt49 = counterFemaleFrom15To49(regionCommuneId, request, false);
//            int totalWomenLt49HasHusband = counterFemaleFrom15To49(regionCommuneId, request, true);
//            int totalPersonDieQuarter = counterPersonChangeQuarter(regionCommuneId, request, Config.DIE, "");
//            int totalPersonMarriedQuarter = counterPersonChangeQuarter(regionCommuneId, request, Config.KET_HON, Config.NOTE_DA_KET_HON);
//            int totalPersonDivorceQuarter = counterPersonChangeQuarter(regionCommuneId, request, Config.KET_HON, Config.NOTE_DA_LY_HON);
//            int totalPersonGoQuarter = counterPersonChangeQuarter(regionCommuneId, request, Config.CHUYEN_DI, "");
//            int totalPersonComeQuarter = counterPersonChangeQuarter(regionCommuneId, request, Config.CHUYEN_DEN, "");
//            int totalPersonContraceptiveId2 = counterFamilyUsedContraceptive(regionCommuneId, request, Config.VONG_TRANH_THAI);
//            int totalPersonContraceptiveId5 = counterFamilyUsedContraceptive(regionCommuneId, request, Config.TRIET_SAN_NAM);
//            int totalPersonContraceptiveId6 = counterFamilyUsedContraceptive(regionCommuneId, request, Config.TRIET_SAN_NU);
//            int totalPersonContraceptiveId10 = counterFamilyUsedContraceptive(regionCommuneId, request, Config.THUOC_CAY);
//            int totalPersonContraceptiveId9 = counterFamilyUsedContraceptive(regionCommuneId, request, Config.THUOC_TIEM);
//            int totalPersonContraceptiveId8 = counterFamilyUsedContraceptive(regionCommuneId, request, Config.THUOC_UONG);
//            int totalPersonContraceptiveId7 = counterFamilyUsedContraceptive(regionCommuneId, request, Config.BCS);
//            int totalPersonContraceptiveId13 = counterFamilyUsedContraceptive(regionCommuneId, request, Config.BIEN_PHAP_KHAC);
//            int totalPersonNoContraceptiveId = counterFamilyUsedContraceptive(regionCommuneId, request, Config.KHONG_SU_DUNG);
//            int totalUseContraceptive = totalPersonContraceptiveId2
//                    + totalPersonContraceptiveId5
//                    + totalPersonContraceptiveId6
//                    + totalPersonContraceptiveId10
//                    + totalPersonContraceptiveId9
//                    + totalPersonContraceptiveId8
//                    + totalPersonContraceptiveId7
//                    + totalPersonContraceptiveId13;
//
//            int totalCTV = counterCollaborator(regionCommuneId, request, null, false);
//            int totalFemaleCTV = counterCollaborator(regionCommuneId, request, Config.FEMALE, false);
//            int totalCTVInQuarter = counterCollaborator(regionCommuneId, request, null, true);
//
//            DSH02Sub dsh02Sub = new DSH02Sub();
//            dsh02Sub.setAddress(communeName);
//            dsh02Sub.setTotalHouseHold(totalHouseHold);
//            dsh02Sub.setTotalNotBigHouseHold(totalNotBigHouseHold);
//            dsh02Sub.setTotalResident(totalResident);
//            dsh02Sub.setTotalWomenLt49(totalWomenLt49);
//            dsh02Sub.setTotalWomenLt49HaveHusband(totalWomenLt49HasHusband);
//            dsh02Sub.setTotalResident(totalResident);
//            dsh02Sub.setTotalDiePerson(totalPersonDieQuarter);
//            dsh02Sub.setTotalMarryPerson(totalPersonMarriedQuarter);
//            dsh02Sub.setTotalDivorcePerson(totalPersonDivorceQuarter);
//            dsh02Sub.setTotalGoPerson(totalPersonGoQuarter);
//            dsh02Sub.setTotalComePerson(totalPersonComeQuarter);
//            dsh02Sub.setTotalComePerson(totalPersonComeQuarter);
//
//            dsh02Sub.setTotalUseRing(totalPersonContraceptiveId2);
//            dsh02Sub.setTotalMenSterilization(totalPersonContraceptiveId5);
//            dsh02Sub.setTotalWomenSterilization(totalPersonContraceptiveId6);
//            dsh02Sub.setTotalUseImplantDrug(totalPersonContraceptiveId10);
//            dsh02Sub.setTotalUseInjectDrug(totalPersonContraceptiveId9);
//            dsh02Sub.setTotalUseDrinkDrug(totalPersonContraceptiveId8);
//            dsh02Sub.setTotalUsePlastic(totalPersonContraceptiveId7);
//            dsh02Sub.setTotalUseOtherMethod(totalPersonContraceptiveId13);
//            dsh02Sub.setTotalNoUseContraceptive(totalPersonNoContraceptiveId);
//            dsh02Sub.setTotalUseContraceptive(totalUseContraceptive);
//
//            dsh02Sub.setTotalCTV(totalCTV);
//            dsh02Sub.setTotalWomenCTV(totalFemaleCTV);
//            dsh02Sub.setTotalNewCTV(totalCTVInQuarter);
//
//            reportTemplate.addSub(dsh02Sub);
//        }
        reportTemplate.addSubs(codeToDSH02Sub.values());
        return reportTemplate;
    }

    /*tiêu chí 1*/
    private JsonNode counterHouseHold(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolHouseHold = QueryBuilders.boolQuery();
        boolHouseHold.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolHouseHold.must(QueryBuilders.rangeQuery("startDate").lte(timeLte));
        boolHouseHold.must(QueryBuilders.rangeQuery("startDate").gte(timeGte));

        AggregationBuilder aggregationBig = AggregationBuilders
                .terms("BigHouseHold")
                .field("isBigHouseHold");
        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000)
                .subAggregation(aggregationBig);
        searchSourceBuilder.query(boolHouseHold);
        searchSourceBuilder.aggregation(aggregationRegion);

        String resultCounter = qldsRestTemplate.postForObject(
                url + "/house-hold/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");
    }

    /*tiêu chí 2*/
    private JsonNode counterResidence(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolResident = QueryBuilders.boolQuery();
        boolResident.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolResident.mustNot(QueryBuilders.matchQuery("residenceId", Config.TAM_TRU));
        boolResident.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
        boolResident.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
        boolResident.must(QueryBuilders.rangeQuery("startDate").lte(timeLte));
        boolResident.must(QueryBuilders.rangeQuery("startDate").gte(timeGte));

        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000);
        searchSourceBuilder.query(boolResident);
        searchSourceBuilder.aggregation(aggregationRegion);

        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person/_search?size=0",
                searchSourceBuilder.query(boolResident).toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");
    }

    /*tiêu chi 3 4*/
    private JsonNode counterFemaleFrom15To49(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);

        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long lteBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - 15), format_date);
        Long gtBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - 49), format_date);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("sexId", Config.FEMALE));
        boolQuery.mustNot(QueryBuilders.matchQuery("residenceId", Config.TAM_TRU));
        boolQuery.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
        boolQuery.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
        boolQuery.must(QueryBuilders.rangeQuery("dateOfBirth").gt(gtBirthday));
        boolQuery.must(QueryBuilders.rangeQuery("dateOfBirth").lte(lteBirthday));
        boolQuery.must(QueryBuilders.rangeQuery("startDate").lte(timeLte)); // tính đến cuối quý xxx của năm yyy

        AggregationBuilder aggregationMarital = AggregationBuilders
                .terms("marital")
                .field("maritalId")
                .includeExclude(new IncludeExclude(new String[]{
                        String.valueOf(Config.MARRIED),
                }, null));
        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000)
                .subAggregation(aggregationMarital);
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegion);
        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");
    }

    /*tiêu chí 5 8 9 */
    private JsonNode counterPersonChangeQuarter(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
        boolQuery.mustNot(QueryBuilders.matchQuery("personRequest.residenceId", Config.TAM_TRU));
        boolQuery.must(QueryBuilders.rangeQuery("dateUpdate").lte(timeLte));
        boolQuery.must(QueryBuilders.rangeQuery("dateUpdate").gte(timeGte));

        AggregationBuilder aggregationBig = AggregationBuilders
                .terms("changeTypeCode")
                .field("changeTypeCode.keyword")
                .includeExclude(new IncludeExclude(
                        new String[]{
                                String.valueOf(Config.DIE),
                                String.valueOf(Config.CHUYEN_DI),
                                String.valueOf(Config.CHUYEN_DEN)
                        }
                        , null));
        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000)
                .subAggregation(aggregationBig);

        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegion);

        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person-history/_search?size=0",
                searchSourceBuilder.query(boolQuery).toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");

    }

    /*6 7*/
    private JsonNode counterPersonMaritalChangeQuarter(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
        boolQuery.mustNot(QueryBuilders.matchQuery("personRequest.residenceId", Config.TAM_TRU));
        boolQuery.mustNot(QueryBuilders.matchQuery("changeTypeCode", Config.KET_HON));
        boolQuery.must(QueryBuilders.rangeQuery("dateUpdate").lte(timeLte));
        boolQuery.must(QueryBuilders.rangeQuery("dateUpdate").gte(timeGte));

        AggregationBuilder aggregationNote = AggregationBuilders
                .terms("notes")
                .field("personRequest.notes.keyword")
                .includeExclude(new IncludeExclude(
                        null
                        , new String[]{""})
                );
        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000)
                .subAggregation(aggregationNote);

        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegion);

        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person-history/_search?size=0",
                searchSourceBuilder.query(boolQuery).toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");

    }

    /*tiếu chí 10... Số cặp vợ chồng trong độ tuổi sinh đẻ hiện đang sử
      dụng BPTT tính đến cuối quý (cặp)*/
    private JsonNode counterFamilyUsedContraceptive(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolQuery.must(QueryBuilders.rangeQuery("contraDate").gte(timeGte));
        boolQuery.must(QueryBuilders.rangeQuery("contraDate").lte(timeLte));

        AggregationBuilder aggregationContraceptive = AggregationBuilders
                .terms("contraceptive")
                .field("contraceptiveId");
        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000)
                .subAggregation(aggregationContraceptive);
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegion);

        String resultCounter = qldsRestTemplate.postForObject(
                url + "/family-planning-history/_search?size=0",
                searchSourceBuilder.query(boolQuery).toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");
    }

    /*tiêu chí 11.a 11.b không sử dụng biện pháp tránh thai*/
    private int counterFamilyNotUsedContraceptiveAndTwoChild(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("contraceptiveId", Config.KHONG_SU_DUNG));
        boolQuery.must(QueryBuilders.rangeQuery("contraDate").gte(timeGte));
        boolQuery.must(QueryBuilders.rangeQuery("contraDate").lte(timeLte));

        String resultCounter = qldsRestTemplate.postForObject(
                url + "/family-planning-history/_count",
                searchSourceBuilder.query(boolQuery).toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("count").asInt();
    }

    /*tiêu chí 12*/
    private int counterCollaborator(ReportRequest request, Integer sexId, boolean isInQuarter) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        if (sexId != null) {
            boolQuery.must(QueryBuilders.matchQuery("sexId", sexId));
        }
        if (isInQuarter) {
            boolQuery.must(QueryBuilders.rangeQuery("hireDate").gte(timeGte));
        }
        boolQuery.must(QueryBuilders.rangeQuery("hireDate").lte(timeLte));
        String resultCounter = qldsRestTemplate.postForObject(
                url + "/collaborator/_count",
                searchSourceBuilder.query(boolQuery).toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("count").asInt();
    }

}
