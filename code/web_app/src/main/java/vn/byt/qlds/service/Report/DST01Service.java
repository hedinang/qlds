package vn.byt.qlds.service.Report;

import vn.byt.qlds.model.report.DST.DST01.DST01;
import vn.byt.qlds.model.report.DST.DST01.DST01Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Component
public class DST01Service extends ReportService {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DST01();
        super.generateReport(request);
        LocalDate startDate = LocalDate.parse(request.startTime, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate endDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate filterLowBirthMother = LocalDate.of(startDate.getYear() - 20, startDate.getMonth(), 1); // birthDay > ngay dau tien cua thang
        LocalDate filterHighBirthMother = LocalDate.of(endDate.getYear() - 15, endDate.getMonth(), endDate.getDayOfMonth());// birthDay < ngay cuoi cung cua nam

        ObjectMapper objectMapper = new ObjectMapper();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        Map mapAddress = new HashMap();
        mapAddress.put("parent", request.regionId);
        JsonNode jsonNode = objectMapper.readTree(qldsRestTemplate.postForObject(url + "/unit-category/_search", elasticSearchService.searchAndBuilder(mapAddress).toString(), String.class));
        for (JsonNode element : jsonNode.path("hits").path("hits")) {
            DST01Sub DST01Sub = new DST01Sub();
//            DST01Sub.address = element.path("_source").path("ten").textValue();
//            // tong so tre sinh ra
//            BoolQueryBuilder boolChildBirth = QueryBuilders.boolQuery();
//            boolChildBirth.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolChildBirth.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolChildBirth.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolChildBirth.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonChildBirth = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChildBirth).toString(), String.class));
//            DST01Sub.setTotalChild(new Gson().fromJson(String.valueOf(jsonChildBirth.path("count")), Integer.class));
//            // tong so tre em nam sinh ra
//            BoolQueryBuilder boolBoyBirth = QueryBuilders.boolQuery();
//            boolBoyBirth.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolBoyBirth.must(QueryBuilders.matchQuery("personRequest.genderRequest.genderId", Config.FEMALE)); // sau nay dong bo lai chuyen thanh 2
//            boolBoyBirth.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolBoyBirth.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolBoyBirth.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonBoyBirth = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolBoyBirth).toString(), String.class));
//            DST01Sub.setTotalBoy(new Gson().fromJson(String.valueOf(jsonBoyBirth.path("count")), Integer.class));
//            // tong so tre em nu sinh ra trong thang
//            BoolQueryBuilder boolGirlBirth = QueryBuilders.boolQuery();
//            boolGirlBirth.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolGirlBirth.must(QueryBuilders.matchQuery("personRequest.genderRequest.genderId", Config.MALE));
//            boolGirlBirth.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolGirlBirth.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolGirlBirth.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonGirlBirth = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolGirlBirth).toString(), String.class));
//            DST01Sub.setTotalGirl(new Gson().fromJson(String.valueOf(jsonGirlBirth.path("count")), Integer.class));
//            // tong so tre trong gia dinh co >= 3 con
//            BoolQueryBuilder boolChildOfFamilyGt3 = QueryBuilders.boolQuery();
//            boolChildOfFamilyGt3.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("personRequest.personHealthyRequest.birthNumber").lte(3));
//            boolChildOfFamilyGt3.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonChildOfFamilyGt3 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChildOfFamilyGt3).toString(), String.class));
//            DST01Sub.setTotalChildOfFamilyGt3(new Gson().fromJson(String.valueOf(jsonChildOfFamilyGt3.path("count")), Integer.class));
//            // tong so tre co me co tuoi <20 && >15
//            BoolQueryBuilder boolChildOfMotherAgeLte20 = QueryBuilders.boolQuery();
//            boolChildOfMotherAgeLte20.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("personRequest.dateOfBirthMother").gt(StringUtils.convertDateToLong(filterLowBirthMother.toString(), "yyyy-MM-dd")));
//            boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("personRequest.dateOfBirthMother").lt(StringUtils.convertDateToLong(filterHighBirthMother.toString(), "yyyy-MM-dd")));
//            JsonNode jsonChildOfMotherAgeLte20 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChildOfMotherAgeLte20).toString(), String.class));
//            DST01Sub.setTotalChildOfMotherAgeLt20(new Gson().fromJson(String.valueOf(jsonChildOfMotherAgeLte20.path("count")), Integer.class));
//            // tong so phu nu dat vong
//            BoolQueryBuilder boolWomenUseContraceptiveRing = QueryBuilders.boolQuery();
//            boolWomenUseContraceptiveRing.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolWomenUseContraceptiveRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.VONG_TRANH_THAI));
//            boolWomenUseContraceptiveRing.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolWomenUseContraceptiveRing.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonWomenUseContraceptiveRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenUseContraceptiveRing).toString(), String.class));
//            DST01Sub.setTotalWomenUseContraceptiveRing(new Gson().fromJson(String.valueOf(jsonWomenUseContraceptiveRing.path("count")), Integer.class));
//            // tong so phu nu thay vong
//            BoolQueryBuilder boolWomenInsteadRing = QueryBuilders.boolQuery();
//            boolWomenInsteadRing.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolWomenInsteadRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THAY_VONG_TRANH_THAI));
//            boolWomenInsteadRing.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolWomenInsteadRing.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonWomenInsteadRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenInsteadRing).toString(), String.class));
//            DST01Sub.setTotalWomenInsteadRing(new Gson().fromJson(String.valueOf(jsonWomenInsteadRing.path("count")), Integer.class));
//            // tong so phu nu stop ring
//            BoolQueryBuilder boolWomenStopContraceptiveRing = QueryBuilders.boolQuery();
//            boolWomenStopContraceptiveRing.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolWomenStopContraceptiveRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THOI_VONG_TRANH_THAI));
//            boolWomenStopContraceptiveRing.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolWomenStopContraceptiveRing.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonWomenStopContraceptiveRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenStopContraceptiveRing).toString(), String.class));
//            DST01Sub.setTotalWomenStopContraceptiveRing(new Gson().fromJson(String.valueOf(jsonWomenStopContraceptiveRing.path("count")), Integer.class));
//            //            int boolWomenSterilization;
//            BoolQueryBuilder boolWomenSterilization = QueryBuilders.boolQuery();
//            boolWomenSterilization.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolWomenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NU));
//            boolWomenSterilization.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolWomenSterilization.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolWomenSterilization.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonWomenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenSterilization).toString(), String.class));
//            DST01Sub.setTotalWomenSterilization(new Gson().fromJson(String.valueOf(jsonWomenSterilization.path("count")), Integer.class));
//            //            int totalMenSterilization; // triet san
//            BoolQueryBuilder boolMenSterilization = QueryBuilders.boolQuery();
//            boolMenSterilization.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolMenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NAM));
//            boolMenSterilization.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolMenSterilization.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonMenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolMenSterilization).toString(), String.class));
//            DST01Sub.setTotalMenSterilization(new Gson().fromJson(String.valueOf(jsonMenSterilization.path("count")), Integer.class));
//            //            int totalWomenUseImplantedDrug; // su dung thuoc cay tranh thai
//            BoolQueryBuilder boolWomenUseImplantedDrug = QueryBuilders.boolQuery();
//            boolWomenUseImplantedDrug.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolWomenUseImplantedDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_CAY));
//            boolWomenUseImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolWomenUseImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonWomenUseImplantedDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenUseImplantedDrug).toString(), String.class));
//            DST01Sub.setTotalWomenUseImplantedDrug(new Gson().fromJson(String.valueOf(jsonWomenUseImplantedDrug.path("count")), Integer.class));
//            //            int totalWomenInsteadImplantedDrug;
//            BoolQueryBuilder boolWomenInsteadImplantedDrug = QueryBuilders.boolQuery();
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THAY_THUOC_CAY));
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolWomenInsteadImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonWomenInsteadImplantedDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenInsteadImplantedDrug).toString(), String.class));
//            DST01Sub.setTotalWomenInsteadImplantedDrug(new Gson().fromJson(String.valueOf(jsonWomenInsteadImplantedDrug.path("count")), Integer.class));
//            //            int totalWomenStopImplantedDrug;
//            BoolQueryBuilder boolWomenStopImplantedDrug = QueryBuilders.boolQuery();
//            boolWomenStopImplantedDrug.must(QueryBuilders.matchQuery("personRequest.districtId", element.path("_source").path("ma").textValue()));
//            boolWomenStopImplantedDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THOI_THUOC_CAY));
//            boolWomenStopImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, "dd/MM/yyyy")));
//            boolWomenStopImplantedDrug.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, "dd/MM/yyyy")));
//            JsonNode jsonWomenStopImplantedDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenStopImplantedDrug).toString(), String.class));
//            DST01Sub.setTotalWomenStopImplantedDrug(new Gson().fromJson(String.valueOf(jsonWomenStopImplantedDrug.path("count")), Integer.class));
            reportTemplate.addSub(DST01Sub);
        }
        return reportTemplate;
    }

}
