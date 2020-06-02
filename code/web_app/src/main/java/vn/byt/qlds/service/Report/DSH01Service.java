package vn.byt.qlds.service.Report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DSH.DSH01.DSH01;
import vn.byt.qlds.model.report.DSH.DSH01.DSH01Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;

@Component
public class DSH01Service extends ReportService {

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DSH01();
        request.startTime = "1/1/" + request.year;
        request.endTime = YearMonth.of(request.year, request.month).lengthOfMonth() + "/" + request.month + "/" + request.year;
        super.generateReport(request);
        Map<String, DSH01Sub> codeToDSH01Sub = new HashMap<>();
        JsonNode hitsCommune = getCommune(request);
        JsonNode jsonChild = counterChildWithSexId(request);
        JsonNode jsonChildGteThird = counterChildGteThird(request);
        JsonNode jsonMotherLte20 = counterMotherLte20(request);
        JsonNode jsonContraceptive = counterContraceptive(request);
        for (JsonNode commune :
                hitsCommune) {
            String regionCode = commune.findPath("code").asText();
            String regionName = commune.findPath("name").asText();
            DSH01Sub dsh01Sub = new DSH01Sub();
            dsh01Sub.setAddress(regionName);
            codeToDSH01Sub.put(regionCode, dsh01Sub);
        }

        for (JsonNode child : jsonChild) {
            String key = child.path("key").asText();
            int total = child.path("doc_count").asInt();
            DSH01Sub dsh01Sub = codeToDSH01Sub.get(key);
            dsh01Sub.setTotalChild(total);
            JsonNode buckedSex = child.path("sex").path("buckets");
            for (JsonNode bucket : buckedSex) {
                int keyTypeCode = bucket.findPath("key").intValue();
                int docCount = bucket.findPath("doc_count").intValue();
                switch (keyTypeCode) {
                    case Config.MALE:
                        dsh01Sub.setTotalBoy(docCount);
                        break;
                    case Config.FEMALE:
                        dsh01Sub.setTotalGirl(docCount);
                        break;
                }
            }
        }

        for (JsonNode childGteThird : jsonChildGteThird) {
            String key = childGteThird.path("key").asText();
            int total = childGteThird.path("doc_count").asInt();
            DSH01Sub dsh01Sub = codeToDSH01Sub.get(key);
            dsh01Sub.setTotalChildOfFamilyGt3(total);
        }

        for (JsonNode motherLte20 : jsonMotherLte20) {
            String key = motherLte20.path("key").asText();
            int total = motherLte20.path("doc_count").asInt();
            DSH01Sub dsh01Sub = codeToDSH01Sub.get(key);
            dsh01Sub.setTotalChildOfMotherAgeLt20(total);
        }

        for (JsonNode contraceptive : jsonContraceptive) {
            String key = contraceptive.path("key").asText();
            int total = contraceptive.path("doc_count").asInt();
            DSH01Sub dsh01Sub = codeToDSH01Sub.get(key);

            JsonNode buckedContraceptive = contraceptive.path("contraceptive").path("buckets");
            for (JsonNode bucket : buckedContraceptive) {
                int keyTypeCode = bucket.findPath("key").intValue();
                int docCount = bucket.findPath("doc_count").intValue();
                switch (keyTypeCode) {
                    case Config.VONG_TRANH_THAI:
                        dsh01Sub.setTotalWomenUseContraceptiveRing(docCount);
                        break;
                    case Config.THAY_VONG_TRANH_THAI:
                        dsh01Sub.setTotalWomenInsteadRing(docCount);
                        break;
                    case Config.THOI_VONG_TRANH_THAI:
                        dsh01Sub.setTotalWomenStopContraceptiveRing(docCount);
                        break;
                    case Config.TRIET_SAN_NAM:
                        dsh01Sub.setTotalMenSterilization(docCount);
                        break;
                    case Config.TRIET_SAN_NU:
                        dsh01Sub.setTotalWomenSterilization(docCount);
                        break;
                    case Config.THUOC_CAY:
                        dsh01Sub.setTotalWomenUseImplantedDrug(docCount);
                        break;
                    case Config.THAY_THUOC_CAY:
                        dsh01Sub.setTotalWomenInsteadImplantedDrug(docCount);
                        break;
                    case Config.THOI_THUOC_CAY:
                        dsh01Sub.setTotalWomenStopImplantedDrug(docCount);
                        break;
                }
            }
        }
        reportTemplate.addSubs(codeToDSH01Sub.values());
        return reportTemplate;
    }

    /*regionId: mã xã, đếm trẻ mới sinh*/
    private JsonNode counterChildWithSexId(ReportRequest request) throws JsonProcessingException, ParseException {
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolChildBirth = QueryBuilders.boolQuery();
        boolChildBirth.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
        boolChildBirth.must(QueryBuilders.matchQuery("changeTypeCode", Config.TRE_MOI_SINH));
        boolChildBirth.must(QueryBuilders.matchQuery("isDeleted", false));
        boolChildBirth.must(QueryBuilders.rangeQuery("dateUpdate").gte(timeGte));
        boolChildBirth.must(QueryBuilders.rangeQuery("dateUpdate").lte(timeLte));

        AggregationBuilder aggregationSex = AggregationBuilders
                .terms("sex")
                .field("personRequest.sexId");
        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000)
                .subAggregation(aggregationSex);
        searchSourceBuilder.query(boolChildBirth);
        searchSourceBuilder.aggregation(aggregationRegion);


        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person-history/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");
    }

    /*Số trẻ em sinh ra là con thứ 3 trở lên : Số trẻ em được sinh ra : có ngày sinh trong khoảng thời gian lọc báo cáo + là con thứ 3 trở lên trong thông tin SKSS*/
    private JsonNode counterChildGteThird(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolQuery.must(QueryBuilders.rangeQuery("birthNumber").lte(3));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("genDate").gte(timeGte));
        boolQuery.must(QueryBuilders.rangeQuery("genDate").lte(timeLte));

        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000);
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegion);

        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person-healthy/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");
    }

    /*Số trẻ em sinh ra của phụ nữ dưới 20 tuổi : Số trẻ em được sinh ra : có ngày sinh trong khoảng thời gian lọc báo cáo + tại thời điểm sinh mẹ của trẻ < 20 tuổi*/
    private JsonNode counterMotherLte20(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("genDate").gte(timeGte));
        boolQuery.must(QueryBuilders.rangeQuery("genDate").lte(timeLte));
        boolQuery.must(QueryBuilders.rangeQuery("genInAge").lte(20)); //
        AggregationBuilder aggregationRegion = AggregationBuilders
                .terms("region")
                .field("regionId.keyword")
                .size(1000);
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegion);
        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person-healthy/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");
    }

    private JsonNode counterContraceptive(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        long timeGte = StringUtils.convertDateToLong(request.startTime, format_date);
        long timeLte = StringUtils.convertDateToLong(request.endTime, format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
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
}
