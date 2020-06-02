package vn.byt.qlds.service.Report;

import vn.byt.qlds.model.report.DSX.DSX01.DSX01;
import vn.byt.qlds.model.report.DSX.DSX01.DSX01Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
public class DSX01Service extends ReportService {

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DSX01();
        super.generateReport(request);

        LocalDate startDayOfMonth = LocalDate.of(request.year, request.month, 1);
        LocalDate endDayOfMonth = LocalDate.of(request.year, request.month, startDayOfMonth.lengthOfMonth());
        LocalDate filterLowBirthMother = LocalDate.of(request.year - 20, request.month, 1); // birthDay > ngay dau tien cua thang
        LocalDate filterHighBirthMother = LocalDate.of(request.year - 15, request.month, 30);// birthDay < ngay cuoi cung cua nam

        ObjectMapper objectMapper = new ObjectMapper();
        Map mapAddress = new HashMap();
        mapAddress.put("regionId", request.regionId);
        JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/address/_search", elasticSearchService.searchAndBuilder(mapAddress).toString(), String.class));
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        JsonNode hits = jsonNode.path("hits").path("hits");
        for (JsonNode element : hits) {
            DSX01Sub dsx01Sub = new DSX01Sub();
//            AddressRequest addressRequest = new Gson().fromJson(String.valueOf(element.path("_source")), AddressRequest.class);
//            dsx01Sub.setAddress(addressRequest.getTen());
//            // tong so tre sinh ra trong thang
//            BoolQueryBuilder boolChildBirth = QueryBuilders.boolQuery();
//            boolChildBirth.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolChildBirth.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolChildBirth.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolChildBirth.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolChildBirth.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonChildBirth = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChildBirth).toString(), String.class));
//            // tong so tre em nam sinh ra trong thang
//            BoolQueryBuilder boolBoyBirth = QueryBuilders.boolQuery();
//            boolBoyBirth.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolBoyBirth.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolBoyBirth.must(QueryBuilders.matchQuery("personRequest.genderRequest.genderId", 0)); // sau nay dong bo lai chuyen thanh 2
//            boolBoyBirth.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolBoyBirth.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolBoyBirth.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonBoyBirth = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolBoyBirth).toString(), String.class));
//            // tong so tre em nu sinh ra trong thang
//            BoolQueryBuilder boolGirlBirth = QueryBuilders.boolQuery();
//            boolGirlBirth.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolGirlBirth.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolGirlBirth.must(QueryBuilders.matchQuery("personRequest.genderRequest.genderId", 1));
//            boolGirlBirth.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolGirlBirth.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolGirlBirth.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonGirlBirth = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolGirlBirth).toString(), String.class));
//            // tong so tre trong gia dinh co >= 3 con
//            BoolQueryBuilder boolChildOfFamilyGt3 = QueryBuilders.boolQuery();
//            boolChildOfFamilyGt3.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolChildOfFamilyGt3.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("personRequest.personHealthyRequest.birthNumber").lte(3));
//            boolChildOfFamilyGt3.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonChildOfFamilyGt3 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChildOfFamilyGt3).toString(), String.class));
//            // tong so tre co me co tuoi <20 && >15
//            BoolQueryBuilder boolChildOfMotherAgeLte20 = QueryBuilders.boolQuery();
//            boolChildOfMotherAgeLte20.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("personRequest.dateOfBirthMother").gt(StringUtils.convertDateToLong(filterLowBirthMother.toString(), "yyyy-MM-dd")));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("personRequest.dateOfBirthMother").lt(StringUtils.convertDateToLong(filterHighBirthMother.toString(), "yyyy-MM-dd")));
//            JsonNode jsonChildOfMotherAgeLte20 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChildOfMotherAgeLte20).toString(), String.class));
//            // tong so phu nu dat vong
//            BoolQueryBuilder boolWomenUseContraceptiveRing = QueryBuilders.boolQuery();
//            boolWomenUseContraceptiveRing.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenUseContraceptiveRing.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolWomenUseContraceptiveRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", 2));
//            boolWomenUseContraceptiveRing.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolWomenUseContraceptiveRing.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenUseContraceptiveRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenUseContraceptiveRing).toString(), String.class));
//            // tong so phu nu thay vong
//            BoolQueryBuilder boolWomenInsteadRing = QueryBuilders.boolQuery();
//            boolWomenInsteadRing.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenInsteadRing.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolWomenInsteadRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", 3));
//            boolWomenInsteadRing.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolWomenInsteadRing.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenInsteadRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenInsteadRing).toString(), String.class));
//            // tong so phu nu stop ring
//            BoolQueryBuilder boolWomenStopContraceptiveRing = QueryBuilders.boolQuery();
//            boolWomenStopContraceptiveRing.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenStopContraceptiveRing.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolWomenStopContraceptiveRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", 4));
//            boolWomenStopContraceptiveRing.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolWomenStopContraceptiveRing.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenStopContraceptiveRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenStopContraceptiveRing).toString(), String.class));
//            //            int totalWomenStopContraceptiveRing;
//            BoolQueryBuilder boolWomenSterilization = QueryBuilders.boolQuery();
//            boolWomenSterilization.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenSterilization.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolWomenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", 5));
//            boolWomenSterilization.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolWomenSterilization.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenSterilization).toString(), String.class));
//            //            int totalMenSterilization; // triet san
//            BoolQueryBuilder boolMenSterilization = QueryBuilders.boolQuery();
//            boolMenSterilization.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolMenSterilization.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolMenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", 6));
//            boolMenSterilization.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolMenSterilization.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonMenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolMenSterilization).toString(), String.class));
//            //            int totalWomenUseImplantedDrug; // su dung thuoc cay tranh thai
//            BoolQueryBuilder boolWomenUseImplantedDrug = QueryBuilders.boolQuery();
//            boolWomenUseImplantedDrug.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenUseImplantedDrug.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolWomenUseImplantedDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", 10));
//            boolWomenUseImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolWomenUseImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenUseImplantedDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenUseImplantedDrug).toString(), String.class));
//            //            int totalWomenInsteadImplantedDrug;
//            BoolQueryBuilder boolWomenInsteadImplantedDrug = QueryBuilders.boolQuery();
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", 11));
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenInsteadImplantedDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenInsteadImplantedDrug).toString(), String.class));
//            //            int totalWomenStopImplantedDrug;
//            BoolQueryBuilder boolWomenStopImplantedDrug = QueryBuilders.boolQuery();
//            boolWomenStopImplantedDrug.must(QueryBuilders.matchQuery("personRequest.houseHoldRequest.addressRequest.id", addressRequest.getId()));
//            boolWomenStopImplantedDrug.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", request.regionId));
//            boolWomenStopImplantedDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", 12));
//            boolWomenStopImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfMonth.toString(), "yyyy-MM-dd")));
//            boolWomenStopImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfMonth.toString(), "yyyy-MM-dd")));
//            JsonNode jsonWomenStopImplantedDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenStopImplantedDrug).toString(), String.class));
//
//            int totalChild = jsonChildBirth.findPath("count").intValue();
//            int totalBoy = jsonBoyBirth.findPath("count").intValue();
//            int totalGirl = jsonGirlBirth.findPath("count").intValue();
//            int totalChildOfFamilyGt3 = jsonChildOfFamilyGt3.findPath("count").intValue();
//            int totalChildOfMotherAgeLt20 = jsonChildOfMotherAgeLte20.findPath("count").intValue();
//            int totalMenSterilization = jsonMenSterilization.findPath("count").intValue();
//            int totalWomenInsteadImplantedDrug = jsonWomenInsteadImplantedDrug.findPath("count").intValue();
//            int totalWomenInsteadRing = jsonWomenInsteadRing.findPath("count").intValue();
//            int totalWomenSterilization = jsonWomenSterilization.findPath("count").intValue();
//            int totalWomenStopContraceptiveRing = jsonWomenStopContraceptiveRing.findPath("count").intValue();
//            int totalWomenStopImplantedDrug = jsonWomenStopImplantedDrug.findPath("count").intValue();
//            int totalWomenUseContraceptiveRing = jsonWomenUseContraceptiveRing.findPath("count").intValue();
//            int totalWomenUseImplantedDrug = jsonWomenUseImplantedDrug.findPath("count").intValue();
//            dsx01Sub.setTotalBoy(totalBoy);
//            dsx01Sub.setTotalChild(totalChild);
//            dsx01Sub.setTotalChildOfFamilyGt3(totalChildOfFamilyGt3);
//            dsx01Sub.setTotalChildOfMotherAgeLt20(totalChildOfMotherAgeLt20);
//            dsx01Sub.setTotalGirl(totalGirl);
//            dsx01Sub.setTotalMenSterilization(totalMenSterilization);
//            dsx01Sub.setTotalWomenInsteadImplantedDrug(totalWomenInsteadImplantedDrug);
//            dsx01Sub.setTotalWomenInsteadRing(totalWomenInsteadRing);
//            dsx01Sub.setTotalWomenSterilization(totalWomenSterilization);
//            dsx01Sub.setTotalWomenStopContraceptiveRing(totalWomenStopContraceptiveRing);
//            dsx01Sub.setTotalWomenStopImplantedDrug(totalWomenStopImplantedDrug);
//            dsx01Sub.setTotalWomenUseContraceptiveRing(totalWomenUseContraceptiveRing);
//            dsx01Sub.setTotalWomenUseImplantedDrug(totalWomenUseImplantedDrug);
            reportTemplate.addSub(dsx01Sub);
        }
        return reportTemplate;
    }

}
