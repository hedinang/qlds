package vn.byt.qlds.service.Report;

import vn.byt.qlds.client.ReportDesignClient;
import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.model.report.DSX.DSX02.DSX02;
import vn.byt.qlds.model.report.DSX.DSX02.DSX02Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class DSX02Service extends ReportService {

    @Autowired
    QldsRestTemplate tenantRestTemplate;
    @Autowired
    ReportDesignClient reportDesignClient;
    @Value("${urlES}")
    public String url;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DSX02();
        super.generateReport(request);
        LocalDate startDayOfQuater;
        LocalDate endDayOfQuater;
        LocalDate filterLowWomen;
        LocalDate filterHighWomen;

        switch (request.quarter) {

            case 2:
                startDayOfQuater = LocalDate.of(request.year, 4, 1);
                endDayOfQuater = LocalDate.of(request.year, 6, 30);
                filterLowWomen = LocalDate.of(request.year - 49, 4, 1); // birthDay > ngay dau tien cua thang
                filterHighWomen = LocalDate.of(request.year - 15, 6, 30);// birthDay < ngay cuoi cung cua nam
                break;
            case 3:
                startDayOfQuater = LocalDate.of(request.year, 7, 1);
                endDayOfQuater = LocalDate.of(request.year, 9, 30);
                filterLowWomen = LocalDate.of(request.year - 49, 7, 1); // birthDay > ngay dau tien cua thang
                filterHighWomen = LocalDate.of(request.year - 15, 9, 30);// birthDay < ngay cuoi cung cua nam
                break;
            case 4:
                startDayOfQuater = LocalDate.of(request.year, 10, 1);
                endDayOfQuater = LocalDate.of(request.year, 12, 31);
                filterLowWomen = LocalDate.of(request.year - 49, 10, 1); // birthDay > ngay dau tien cua thang
                filterHighWomen = LocalDate.of(request.year - 15, 12, 30);// birthDay < ngay cuoi cung cua nam
                break;
            default:// mac dinh la 1
                startDayOfQuater = LocalDate.of(request.year, 1, 1);
                endDayOfQuater = LocalDate.of(request.year, 3, 31);
                filterLowWomen = LocalDate.of(request.year - 49, 1, 1); // birthDay > ngay dau tien cua thang
                filterHighWomen = LocalDate.of(request.year - 15, 3, 30);// birthDay < ngay cuoi cung cua nam
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Map mapAddress = new HashMap();
        mapAddress.put("regionId", request.regionId);
        JsonNode jsonNode = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/address/_search", elasticSearchService.searchAndBuilder(mapAddress).toString(), String.class));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        for (JsonNode element : jsonNode.path("hits").path("hits")) {

            DSX02Sub dsx02Sub = new DSX02Sub();
//            AddressRequest addressRequest = new Gson().fromJson(String.valueOf(element.path("_source")), AddressRequest.class);
//            dsx02Sub.setAddress(addressRequest.getTen());
//            //lay tong so ho gia dinh
//            BoolQueryBuilder boolHouseHold = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("addressRequest.id", addressRequest.getId()));
//            boolHouseHold.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonHouseHold = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/house-hold/_count", searchSourceBuilder.query(boolHouseHold).toString(), String.class));
//            // lay tong so ho ko phai la ho tap the
//            BoolQueryBuilder boolNotBigHouseHold = QueryBuilders.boolQuery();
//            boolNotBigHouseHold.must(QueryBuilders.matchQuery("addressRequest.id", addressRequest.getId()));
//            boolNotBigHouseHold.must(QueryBuilders.matchQuery("isBigHouseHold", 0));
//            boolNotBigHouseHold.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonNotBigHouseHold = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/house-hold/_count", searchSourceBuilder.query(boolNotBigHouseHold).toString(), String.class));
//            //   Lay tong so nhan khau
//            // neu co khau nao chuyen di hoac xoa thi isDeleted = 1
//            // residenceCode = 3 la tam tru
//            // lay nguoi con song nen ko can check endDate
//            BoolQueryBuilder boolResident = QueryBuilders.boolQuery();
//            boolResident.must(QueryBuilders.matchQuery("houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolResident.mustNot(QueryBuilders.matchQuery("residenceRequest.residenceCode", Config.TAM_TRU));
//            boolResident.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//            boolResident.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//            boolResident.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonResident = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolResident).toString(), String.class));
//            //          tong so phu nu co tuoi 15 den 49
//            BoolQueryBuilder boolWomenLt49 = QueryBuilders.boolQuery();
//            boolWomenLt49.must(QueryBuilders.matchQuery("houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenLt49.mustNot(QueryBuilders.matchQuery("genderRequest.genderId", Config.MALE));
//            boolWomenLt49.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//            boolWomenLt49.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//            boolWomenLt49.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(filterLowWomen.toString(), "yyyy-MM-dd")));
//            boolWomenLt49.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(filterHighWomen.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenLt49 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolWomenLt49).toString(), String.class));
//            //          tong so phu nu co chong co tuoi tu 15 den 49
//            BoolQueryBuilder boolWomenLt49HaveHusband = QueryBuilders.boolQuery();
//            boolWomenLt49HaveHusband.must(QueryBuilders.matchQuery("houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenLt49HaveHusband.must(QueryBuilders.matchQuery("maritalStatusRequest.maritalCode", Config.MARRIED));
//            boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("genderRequest.genderId", Config.MALE));
//            boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//            boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//            boolWomenLt49HaveHusband.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(filterLowWomen.toString(), "yyyy-MM-dd")));
//            boolWomenLt49HaveHusband.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(filterHighWomen.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenLt49HaveHusband = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolWomenLt49HaveHusband).toString(), String.class));
//            // so nguoi chet trong quy
//            BoolQueryBuilder boolDiePerson = QueryBuilders.boolQuery();
//            boolDiePerson.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolDiePerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//            boolDiePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.DIE));
//            boolDiePerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolDiePerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonDiePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolDiePerson).toString(), String.class));
//            // so nguoi ket hon trong quy
//            BoolQueryBuilder boolMarryPerson = QueryBuilders.boolQuery();
//            boolMarryPerson.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolMarryPerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//            boolMarryPerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.KET_HON));
//            boolMarryPerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolMarryPerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolMarryPerson.must(QueryBuilders.matchQuery("notes", Config.NOTE_DA_KET_HON));
//            JsonNode jsonMarryPerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolMarryPerson).toString(), String.class));
//            //so nguoi ly hon trong quy
//            BoolQueryBuilder boolDivorcePerson = QueryBuilders.boolQuery();
//            boolDivorcePerson.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolDivorcePerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//            boolDivorcePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.KET_HON));
//            boolDivorcePerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolDivorcePerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolDivorcePerson.must(QueryBuilders.matchQuery("notes", Config.NOTE_DA_LY_HON));
//            JsonNode jsonDivorcePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolDivorcePerson).toString(), String.class));
//            //so nguoi di ra  trong quy
//            BoolQueryBuilder boolGoPerson = QueryBuilders.boolQuery();
//            boolGoPerson.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolGoPerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//            boolGoPerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.CHUYEN_DI));
//            boolGoPerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolGoPerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonGoPerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolGoPerson).toString(), String.class));
//            // so nguoi chuyen den tu noi khac
//            BoolQueryBuilder boolComePerson = QueryBuilders.boolQuery();
//            boolComePerson.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolComePerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//            boolComePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.CHUYEN_DEN));
//            boolComePerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolComePerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonComePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolComePerson).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung BPTT
//            BoolQueryBuilder boolUseContraceptive = QueryBuilders.boolQuery();
//            boolUseContraceptive.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.VONG_TRANH_THAI));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THAY_VONG_TRANH_THAI));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NAM));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NU));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.BCS));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_UONG));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_TIEM));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_CAY));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THAY_THUOC_CAY));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.BIEN_PHAP_KHAC));
//            boolUseContraceptive.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolUseContraceptive.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonUseContraceptive = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseContraceptive).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung dat vong tranh thai
//            BoolQueryBuilder boolUseRing = QueryBuilders.boolQuery();
//            boolUseRing.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolUseRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.VONG_TRANH_THAI));
//            boolUseRing.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolUseRing.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonUseRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseRing).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung triet san nam
//            BoolQueryBuilder boolMenSterilization = QueryBuilders.boolQuery();
//            boolMenSterilization.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolMenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NAM));
//            boolMenSterilization.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolMenSterilization.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonMenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolMenSterilization).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung triet san nu
//            BoolQueryBuilder boolWomenSterilization = QueryBuilders.boolQuery();
//            boolWomenSterilization.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NU));
//            boolWomenSterilization.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolWomenSterilization.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenSterilization).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung thuoc cay
//            BoolQueryBuilder boolUseImplantDrug = QueryBuilders.boolQuery();
//            boolUseImplantDrug.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolUseImplantDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_CAY));
//            boolUseImplantDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolUseImplantDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonUseImplantDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseImplantDrug).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung thuoc tiem
//            BoolQueryBuilder boolUseInjectDrug = QueryBuilders.boolQuery();
//            boolUseInjectDrug.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolUseInjectDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_TIEM));
//            boolUseInjectDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolUseInjectDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonUseInjectDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseInjectDrug).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung thuoc uong
//
//            BoolQueryBuilder boolDrinkDrug = QueryBuilders.boolQuery();
//            boolDrinkDrug.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolDrinkDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_UONG));
//            boolDrinkDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolDrinkDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonDrinkDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolDrinkDrug).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung BCS
//            BoolQueryBuilder boolUsePlastic = QueryBuilders.boolQuery();
//            boolUsePlastic.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolUsePlastic.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.BCS));
//            boolUsePlastic.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolUsePlastic.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonUsePlastic = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUsePlastic).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung bien phap khac
//
//            BoolQueryBuilder boolUseOtherMethod = QueryBuilders.boolQuery();
//            boolUseOtherMethod.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolUseOtherMethod.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.BIEN_PHAP_KHAC));
//            boolUseOtherMethod.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolUseOtherMethod.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonUseOtherMethod = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseOtherMethod).toString(), String.class));
//            // so cap vo chong trong do tuoi sinh de hien dang khong su dung BPTT
//            BoolQueryBuilder boolNoUseContraceptive = QueryBuilders.boolQuery();
//            boolNoUseContraceptive.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolNoUseContraceptive.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.KHONG_SU_DUNG));
//            boolNoUseContraceptive.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//            boolNoUseContraceptive.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//            JsonNode jsonNoUseContraceptive = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolNoUseContraceptive).toString(), String.class));
//
//            int totalWomenSterilization = jsonWomenSterilization.path("count").intValue();
//            int totalWomenLt49HaveHusband = jsonWomenLt49HaveHusband.path("count").intValue();
//            int totalWomenLt49 = jsonWomenLt49.path("count").intValue();
//            int totalUseRing = jsonUseRing.path("count").intValue();
//            int totalUsePlastic = jsonUsePlastic.path("count").intValue();
//            int totalUseOtherMethod = jsonUseOtherMethod.path("count").intValue();
//            int totalUseInjectDrug = jsonUseInjectDrug.path("count").intValue();
//            int totalUseImplantDrug = jsonUseImplantDrug.path("count").intValue();
//            int totalUseDrinkDrug = jsonDrinkDrug.path("count").intValue();
//            int totalUseContraceptive = jsonUseContraceptive.path("count").intValue();
//            int totalResident = jsonResident.path("count").intValue();
//            int totalNoUseContraceptive = jsonNoUseContraceptive.path("count").intValue();
//            int totalNotBigHouseHold = jsonNotBigHouseHold.path("count").intValue();
//            int totalMenSterilization = jsonMenSterilization.path("count").intValue();
//            int totalMarryPerson = jsonMarryPerson.path("count").intValue();
//            int totalHouseHold = jsonHouseHold.path("count").intValue();
//            int totalGoPerson = jsonGoPerson.path("count").intValue();
//            int totalDivorcePerson = jsonDivorcePerson.path("count").intValue();
//            int totalDiePerson = jsonDiePerson.path("count").intValue();
//            int totalComePerson = jsonComePerson.path("count").intValue();
//            dsx02Sub.setTotalWomenSterilization(totalWomenSterilization);
//            dsx02Sub.setTotalWomenLt49HaveHusband(totalWomenLt49HaveHusband);
//            dsx02Sub.setTotalWomenLt49(totalWomenLt49);
//            // dsx02Sub.setTotalWomenCTV();
//            dsx02Sub.setTotalUseRing(totalUseRing);
//            dsx02Sub.setTotalUsePlastic(totalUsePlastic);
//            dsx02Sub.setTotalUseOtherMethod(totalUseOtherMethod);
//            dsx02Sub.setTotalUseInjectDrug(totalUseInjectDrug);
//            dsx02Sub.setTotalUseImplantDrug(totalUseImplantDrug);
//            dsx02Sub.setTotalUseDrinkDrug(totalUseDrinkDrug);
//            dsx02Sub.setTotalUseContraceptive(totalUseContraceptive);
//            //  dsx02Sub.setTotalTwoChild();
//            //dsx02Sub.setTotalThreeChild();
//            dsx02Sub.setTotalResident(totalResident);
//            dsx02Sub.setTotalNoUseContraceptive(totalNoUseContraceptive);
//            dsx02Sub.setTotalNotBigHouseHold(totalNotBigHouseHold);
//            //dsx02Sub.setTotalNewCTV();
//            dsx02Sub.setTotalMenSterilization(totalMenSterilization);
//            dsx02Sub.setTotalMarryPerson(totalMarryPerson);
//            dsx02Sub.setTotalHouseHold(totalHouseHold);
//            dsx02Sub.setTotalGoPerson(totalGoPerson);
//            dsx02Sub.setTotalDivorcePerson(totalDivorcePerson);
//            dsx02Sub.setTotalDiePerson(totalDiePerson);
//            // dsx02Sub.setTotalCTV();
//            dsx02Sub.setTotalComePerson(totalComePerson);
            reportTemplate.addSub(dsx02Sub);

        }
        // so cap vo chong trong do tuoi sinh de  hien dang khong su dung BPTT co 2 con 1 be
//
//        BoolQueryBuilder boolTwoChild = QueryBuilders.boolQuery();
//        boolTwoChild.must(QueryBuilders.matchQuery("regionId", request.regionId));
//        boolTwoChild.must(QueryBuilders.matchQuery("rptYear", request.year));
//        boolTwoChild.must(QueryBuilders.matchQuery("rptQuarter", request.quarter));
//        JsonNode jsonTwoChild = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/statistic/_search", searchSourceBuilder.query(boolTwoChild).toString(), String.class));
//
//        // so cap vo chong trong do tuoi sinh de  hien dang khong su dung BPTT co hon 3 con
//        BoolQueryBuilder boolThreeChild = QueryBuilders.boolQuery();
//        boolThreeChild.must(QueryBuilders.matchQuery("regionId", request.regionId));
//        boolThreeChild.must(QueryBuilders.matchQuery("rptYear", request.year));
//        boolThreeChild.must(QueryBuilders.matchQuery("rptQuarter", request.quarter));
//        JsonNode jsonThreeChild = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/statistic/_search", searchSourceBuilder.query(boolTwoChild).toString(), String.class));
//
//// so cong tac vien
//        BoolQueryBuilder boolCTV = QueryBuilders.boolQuery();
//        boolCTV.must(QueryBuilders.matchQuery("regionId", request.regionId));
//        JsonNode jsonCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolCTV).toString(), String.class));
//// so cong tac vien nu
//        BoolQueryBuilder boolWomenCTV = QueryBuilders.boolQuery();
//        boolWomenCTV.must(QueryBuilders.matchQuery("regionId", request.regionId));
//        boolWomenCTV.must(QueryBuilders.matchQuery("sexId", Config.MALE));
//        JsonNode jsonWomenCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolWomenCTV).toString(), String.class));
//// so cong tac vien moi thue trong quy
//        BoolQueryBuilder boolNewCTV = QueryBuilders.boolQuery();
//        boolNewCTV.must(QueryBuilders.matchQuery("regionId", request.regionId));
//        boolNewCTV.must(QueryBuilders.rangeQuery("hireDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "yyyy-MM-dd")));
//        boolNewCTV.must(QueryBuilders.rangeQuery("hireDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "yyyy-MM-dd")));
//        JsonNode jsonNewCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolNewCTV).toString(), String.class));
//        DSX02Sub dsx02SubTotal = new DSX02Sub();
//        try {
//            dsx02SubTotal.setTotalTwoChild(new Gson().fromJson(String.valueOf(jsonTwoChild.path("hits").path("hits").get(0).path("_source").path("twoChildrenPair")), Integer.class));
//
//        } catch (Exception e) {
//            dsx02SubTotal.setTotalTwoChild(0);
//        }
//        try {
//            dsx02SubTotal.setTotalThreeChild(new Gson().fromJson(String.valueOf(jsonThreeChild.path("hits").path("hits").get(0).path("_source").path("moreThanThreeChildrenPair")), Integer.class));
//
//        } catch (Exception e) {
//            dsx02SubTotal.setTotalThreeChild(0);
//        }
//        dsx02SubTotal.setTotalCTV(new Gson().fromJson(String.valueOf(jsonCTV.path("count").toString()), Integer.class));
//        dsx02SubTotal.setTotalWomenCTV(new Gson().fromJson(String.valueOf(jsonWomenCTV.path("count").toString()), Integer.class));
//        dsx02SubTotal.setTotalNewCTV(new Gson().fromJson(String.valueOf(jsonNewCTV.path("count").toString()), Integer.class));

//        reportTemplate.setTotal(dsx02SubTotal);

        return reportTemplate;
    }


}
