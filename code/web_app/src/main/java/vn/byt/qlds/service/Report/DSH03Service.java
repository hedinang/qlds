package vn.byt.qlds.service.Report;

import vn.byt.qlds.model.report.DSH.DSH03.DSH03Sub;
import vn.byt.qlds.model.report.DSX.DSX03.DSX03;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;

@Component
public class DSH03Service extends ReportService {
    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DSX03();
        super.generateReport(request);
        LocalDate startDayOfYear = LocalDate.of(request.year, 1, 1);
        LocalDate endDayOfYear = LocalDate.of(request.year, 12, startDayOfYear.getDayOfYear());
        LocalDate filterLowWomen = LocalDate.of(request.year - 49, 1, 1); // birthDay > ngay dau tien cua nam
        LocalDate filterHighWomen = LocalDate.of(request.year - 15, 12, 30);// birthDay < ngay cuoi cung cua nam
        LocalDate filterLowBirthMother = LocalDate.of(request.year - 20, 1, 1); // birthDay > ngay dau tien cua nam
        LocalDate filterHighBirthMother = LocalDate.of(request.year - 15, 12, 30);// birthDay < ngay cuoi cung cua nam

        DSH03Sub dsh03Sub = new DSH03Sub();
//        ObjectMapper objectMapper = new ObjectMapper();
//        // lay danh sach cac xa trong co parent = regionId cua huyen truyen vao
//        // dung for de tinh tong ca huyen
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        //lay tong so ho gia dinh
//        BoolQueryBuilder boolHouseHold = QueryBuilders.boolQuery();
//        boolHouseHold.must(QueryBuilders.matchQuery("addressRequest.districtId", request.regionId));
//        boolHouseHold.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonHouseHold = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/house-hold/_count", searchSourceBuilder.query(boolHouseHold).toString(), String.class));
//        dsh03Sub.setTotalHouseHold(new Gson().fromJson(String.valueOf(jsonHouseHold.path("count")), Integer.class));
//        // lay tong so ho ko phai la ho tap the
//        BoolQueryBuilder boolNotBigHouseHold = QueryBuilders.boolQuery();
//        boolNotBigHouseHold.must(QueryBuilders.matchQuery("addressRequest.districtId", request.regionId));
//        boolNotBigHouseHold.must(QueryBuilders.matchQuery("isBigHouseHold", 0));
//        boolNotBigHouseHold.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonNotBigHouseHold = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/house-hold/_count", searchSourceBuilder.query(boolNotBigHouseHold).toString(), String.class));
//        dsh03Sub.setTotalNotBigHouseHold(new Gson().fromJson(String.valueOf(jsonNotBigHouseHold.path("count")), Integer.class));
//        //   Lay tong so nhan khau
//        // neu co khau nao chuyen di hoac xoa thi isDeleted = 1
//        // residenceCode = 3 la tam tru
//        // lay nguoi con song nen ko can check endDate
//        BoolQueryBuilder boolResident = QueryBuilders.boolQuery();
//        boolResident.must(QueryBuilders.matchQuery("districtId", request.regionId));
//        boolResident.mustNot(QueryBuilders.matchQuery("residenceRequest.residenceCode", Config.TAM_TRU));
//        boolResident.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//        boolResident.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//        boolResident.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonResident = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolResident).toString(), String.class));
//        dsh03Sub.setTotalResident(new Gson().fromJson(String.valueOf(jsonResident.path("count")), Integer.class));
//        // tong so phu nu
//        BoolQueryBuilder boolWomen = QueryBuilders.boolQuery();
//        boolWomen.must(QueryBuilders.matchQuery("districtId", request.regionId));
//        boolWomen.mustNot(QueryBuilders.matchQuery("genderRequest.genderId", Config.MALE));
//        boolWomen.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//        boolWomen.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//        JsonNode jsonWomen = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolWomen).toString(), String.class));
//        dsh03Sub.setTotalWomen(new Gson().fromJson(String.valueOf(jsonWomen.path("count")), Integer.class));
//        //          tong so phu nu co tuoi 15 den 49
//        BoolQueryBuilder boolWomenLt49 = QueryBuilders.boolQuery();
//        boolWomenLt49.must(QueryBuilders.matchQuery("districtId", request.regionId));
//        boolWomenLt49.mustNot(QueryBuilders.matchQuery("genderRequest.genderId", Config.MALE));
//        boolWomenLt49.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//        boolWomenLt49.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//        boolWomenLt49.must(QueryBuilders.rangeQuery("dateOfBirth").gte(StringUtils.convertDateToLong(filterLowWomen.toString(), "yyyy-MM-dd")));
//        boolWomenLt49.must(QueryBuilders.rangeQuery("dateOfBirth").lte(StringUtils.convertDateToLong(filterHighWomen.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenLt49 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolWomenLt49).toString(), String.class));
//        dsh03Sub.setTotalWomenLt49(new Gson().fromJson(String.valueOf(jsonWomenLt49.path("count")), Integer.class));
//        //          tong so phu nu co chong co tuoi tu 15 den 49
//        BoolQueryBuilder boolWomenLt49HaveHusband = QueryBuilders.boolQuery();
//        boolWomenLt49HaveHusband.must(QueryBuilders.matchQuery("districtId", request.regionId));
//        boolWomenLt49HaveHusband.must(QueryBuilders.matchQuery("maritalStatusRequest.maritalCode", Config.MARRIED));
//        boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("genderRequest.genderId", Config.MALE));
//        boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//        boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//        boolWomenLt49HaveHusband.must(QueryBuilders.rangeQuery("dateOfBirth").gte(StringUtils.convertDateToLong(filterLowWomen.toString(), "yyyy-MM-dd")));
//        boolWomenLt49HaveHusband.must(QueryBuilders.rangeQuery("dateOfBirth").lte(StringUtils.convertDateToLong(filterHighWomen.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenLt49HaveHusband = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolWomenLt49HaveHusband).toString(), String.class));
//        dsh03Sub.setTotalWomenLt49HaveHusband(new Gson().fromJson(String.valueOf(jsonWomenLt49HaveHusband.path("count")), Integer.class));
//        // tong so tre sinh ra
//        BoolQueryBuilder boolChild = QueryBuilders.boolQuery();
//        boolChild.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolChild.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//        boolChild.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolChild.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonChild = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChild).toString(), String.class));
//        dsh03Sub.setTotalChild(new Gson().fromJson(String.valueOf(jsonChild.path("count")), Integer.class));
//        // tong so tre em nam sinh ra trong thang
//        BoolQueryBuilder boolBoy = QueryBuilders.boolQuery();
//        boolBoy.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolBoy.must(QueryBuilders.matchQuery("personRequest.genderRequest.genderId", Config.FEMALE)); // sau nay dong bo lai chuyen thanh 2
//        boolBoy.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//        boolBoy.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolBoy.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonBoy = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolBoy).toString(), String.class));
//        dsh03Sub.setTotalBoy(new Gson().fromJson(String.valueOf(jsonBoy.path("count")), Integer.class));
//        // tong so tre em nu sinh ra trong thang
//        BoolQueryBuilder boolGirl = QueryBuilders.boolQuery();
//        boolGirl.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolGirl.must(QueryBuilders.matchQuery("personRequest.genderRequest.genderId", Config.MALE)); // sau nay dong bo lai chuyen thanh 2
//        boolGirl.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//        boolGirl.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolGirl.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonGirl = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolGirl).toString(), String.class));
//        dsh03Sub.setTotalGirl(new Gson().fromJson(String.valueOf(jsonGirl.path("count")), Integer.class));
//        // tong so tre trong gia dinh co >= 3 con
//        BoolQueryBuilder boolChildOfFamilyGt3 = QueryBuilders.boolQuery();
//        boolChildOfFamilyGt3.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("personRequest.personHealthyRequest.birthNumber").lte(3));
//        boolChildOfFamilyGt3.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//        boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolChildOfFamilyGt3.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonChildOfFamilyGt3 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChildOfFamilyGt3).toString(), String.class));
//        dsh03Sub.setTotalChildOfFamilyGt3(new Gson().fromJson(String.valueOf(jsonChildOfFamilyGt3.path("count")), Integer.class));
//        // tong so tre co me co tuoi <20 && >15
//        BoolQueryBuilder boolChildOfMotherAgeLte20 = QueryBuilders.boolQuery();
//        boolChildOfMotherAgeLte20.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolChildOfMotherAgeLte20.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.TRE_MOI_SINH));
//        boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("personRequest.dateOfBirthMother").gt(StringUtils.convertDateToLong(filterLowBirthMother.toString(), "yyyy-MM-dd")));
//        boolChildOfMotherAgeLte20.must(QueryBuilders.rangeQuery("personRequest.dateOfBirthMother").lt(StringUtils.convertDateToLong(filterHighBirthMother.toString(), "yyyy-MM-dd")));
//        JsonNode jsonChildOfMotherAgeLte20 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolChildOfMotherAgeLte20).toString(), String.class));
//        dsh03Sub.setTotalChildOfMotherAgeLt20(new Gson().fromJson(String.valueOf(jsonChildOfMotherAgeLte20.path("count")), Integer.class));
//
//        // so nguoi Config.DIE trong nam
//        BoolQueryBuilder boolDiePerson = QueryBuilders.boolQuery();
//        boolDiePerson.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolDiePerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//        boolDiePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.DIE));
//        boolDiePerson.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolDiePerson.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonDiePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolDiePerson).toString(), String.class));
//        dsh03Sub.setTotalDiePerson(new Gson().fromJson(String.valueOf(jsonDiePerson.path("count")), Integer.class));
//        // so nguoi ket hon trong nam
//        BoolQueryBuilder boolMarryPerson = QueryBuilders.boolQuery();
//        boolMarryPerson.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolMarryPerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//        boolMarryPerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.KET_HON));
//        boolMarryPerson.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolMarryPerson.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        boolMarryPerson.must(QueryBuilders.matchQuery("notes", Config.NOTE_DA_KET_HON));
//        JsonNode jsonMarryPerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolMarryPerson).toString(), String.class));
//        dsh03Sub.setTotalMarryPerson(new Gson().fromJson(String.valueOf(jsonMarryPerson.path("count")), Integer.class));
//        //so nguoi ly hon trong nam
//        BoolQueryBuilder boolDivorcePerson = QueryBuilders.boolQuery();
//        boolDivorcePerson.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolDivorcePerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//        boolDivorcePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.KET_HON));
//        boolDivorcePerson.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolDivorcePerson.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        boolDivorcePerson.must(QueryBuilders.matchQuery("notes", Config.NOTE_DA_LY_HON));
//        JsonNode jsonDivorcePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolDivorcePerson).toString(), String.class));
//        dsh03Sub.setTotalDivorcePerson(new Gson().fromJson(String.valueOf(jsonDivorcePerson.path("count")), Integer.class));
//        //so nguoi di ra  trong nam
//        BoolQueryBuilder boolGoPerson = QueryBuilders.boolQuery();
//        boolGoPerson.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolGoPerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//        boolGoPerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.CHUYEN_DI));
//        boolGoPerson.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolGoPerson.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonGoPerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolGoPerson).toString(), String.class));
//        dsh03Sub.setTotalGoPerson(new Gson().fromJson(String.valueOf(jsonGoPerson.path("count")), Integer.class));
//        // so nguoi chuyen den tu noi khac
//        BoolQueryBuilder boolComePerson = QueryBuilders.boolQuery();
//        boolComePerson.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolComePerson.mustNot(QueryBuilders.matchQuery("personRequest.residenceRequest.residenceCode", Config.TAM_TRU));
//        boolComePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Config.CHUYEN_DEN));
//        boolComePerson.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolComePerson.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonComePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolComePerson).toString(), String.class));
//        dsh03Sub.setTotalComePerson(new Gson().fromJson(String.valueOf(jsonComePerson.path("count")), Integer.class));
//        // so nu su dung vong tranh thai trong nam
//        BoolQueryBuilder boolWomenUseRing = QueryBuilders.boolQuery();
//        boolWomenUseRing.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolWomenUseRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.VONG_TRANH_THAI));
//        boolWomenUseRing.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolWomenUseRing.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenUseRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenUseRing).toString(), String.class));
//        dsh03Sub.setTotalWomenUseRing(new Gson().fromJson(String.valueOf(jsonWomenUseRing.path("count")), Integer.class));
//        // so nu thay vong tranh thai trong nam
//        BoolQueryBuilder boolWomenInsteadRing = QueryBuilders.boolQuery();
//        boolWomenInsteadRing.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolWomenInsteadRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THAY_VONG_TRANH_THAI));
//        boolWomenInsteadRing.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolWomenInsteadRing.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenInsteadRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenInsteadRing).toString(), String.class));
//        dsh03Sub.setTotalWomenInsteadRing(new Gson().fromJson(String.valueOf(jsonWomenInsteadRing.path("count")), Integer.class));
//        // so nu stop vong tranh thai trong nam
//        BoolQueryBuilder boolWomenStopRing = QueryBuilders.boolQuery();
//        boolWomenStopRing.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolWomenStopRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THOI_VONG_TRANH_THAI));
//        boolWomenStopRing.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolWomenStopRing.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenStopRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenStopRing).toString(), String.class));
//        dsh03Sub.setTotalWomenStopRing(new Gson().fromJson(String.valueOf(jsonWomenStopRing.path("count")), Integer.class));
//        // so nam triet san trong nam
//        BoolQueryBuilder boolMenSterilization = QueryBuilders.boolQuery();
//        boolMenSterilization.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolMenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NAM));
//        boolMenSterilization.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolMenSterilization.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonMenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolMenSterilization).toString(), String.class));
//        dsh03Sub.setTotalMenSterilization(new Gson().fromJson(String.valueOf(jsonMenSterilization.path("count")), Integer.class));
//        //so nu triet san
//        BoolQueryBuilder boolWomenSterilization = QueryBuilders.boolQuery();
//        boolWomenSterilization.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolWomenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NU));
//        boolWomenSterilization.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolWomenSterilization.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenSterilization).toString(), String.class));
//        dsh03Sub.setTotalWomenSterilization(new Gson().fromJson(String.valueOf(jsonWomenSterilization.path("count")), Integer.class));
////so nu su dung que cay
//        BoolQueryBuilder boolWomenUseImplantDrug = QueryBuilders.boolQuery();
//        boolWomenUseImplantDrug.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolWomenUseImplantDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_CAY));
//        boolWomenUseImplantDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolWomenUseImplantDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenUseImplantDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenUseImplantDrug).toString(), String.class));
//        dsh03Sub.setTotalWomenUseImplantDrug(new Gson().fromJson(String.valueOf(jsonWomenUseImplantDrug.path("count")), Integer.class));
//        //so nu thay que cay
//        BoolQueryBuilder boolWomenInsteadImplantDrug = QueryBuilders.boolQuery();
//        boolWomenInsteadImplantDrug.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolWomenInsteadImplantDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THAY_THUOC_CAY));
//        boolWomenInsteadImplantDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolWomenInsteadImplantDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenInsteadImplantDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenInsteadImplantDrug).toString(), String.class));
//        dsh03Sub.setTotalWomenInsteadImplantDrug(new Gson().fromJson(String.valueOf(jsonWomenInsteadImplantDrug.path("count")), Integer.class));
////so nu thoi que cay
//        BoolQueryBuilder boolWomenStopImplantDrug = QueryBuilders.boolQuery();
//        boolWomenStopImplantDrug.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolWomenStopImplantDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THOI_THUOC_CAY));
//        boolWomenStopImplantDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolWomenStopImplantDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonWomenStopImplantDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenStopImplantDrug).toString(), String.class));
//        dsh03Sub.setTotalWomenStopImplantDrug(new Gson().fromJson(String.valueOf(jsonWomenStopImplantDrug.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang  su dung BPTT
//        BoolQueryBuilder boolCoupleUseContraceptive = QueryBuilders.boolQuery();
//        boolCoupleUseContraceptive.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.VONG_TRANH_THAI));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THAY_VONG_TRANH_THAI));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NAM));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NU));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.BCS));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_UONG));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_TIEM));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_CAY));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THAY_THUOC_CAY));
//        boolCoupleUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.BIEN_PHAP_KHAC));
//        boolCoupleUseContraceptive.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleUseContraceptive.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleUseContraceptive = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleUseContraceptive).toString(), String.class));
//        dsh03Sub.setTotalCoupleUseContraceptive(new Gson().fromJson(String.valueOf(jsonCoupleUseContraceptive.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang  su dung dat vong tranh thai
//        // so nu su dung vong tranh thai trong nam
//        BoolQueryBuilder boolCoupleUseRing = QueryBuilders.boolQuery();
//        boolCoupleUseRing.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleUseRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.VONG_TRANH_THAI));
//        boolCoupleUseRing.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleUseRing.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleUseRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleUseRing).toString(), String.class));
//        dsh03Sub.setTotalCoupleUseRing(new Gson().fromJson(String.valueOf(jsonCoupleUseRing.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang  su dung triet san nam
//        BoolQueryBuilder boolCoupleMenSterilization = QueryBuilders.boolQuery();
//        boolCoupleMenSterilization.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleMenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NAM));
//        boolCoupleMenSterilization.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleMenSterilization.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleMenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleMenSterilization).toString(), String.class));
//        dsh03Sub.setTotalCoupleMenSterilization(new Gson().fromJson(String.valueOf(jsonCoupleMenSterilization.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang su dung triet san nu
//        BoolQueryBuilder boolCoupleWomenSterilization = QueryBuilders.boolQuery();
//        boolCoupleWomenSterilization.must(QueryBuilders.matchQuery("personRequest.regionId", request.regionId));
//        boolCoupleWomenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.TRIET_SAN_NU));
//        boolCoupleWomenSterilization.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleWomenSterilization.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleWomenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenSterilization).toString(), String.class));
//        dsh03Sub.setTotalCoupleWomenSterilization(new Gson().fromJson(String.valueOf(jsonCoupleWomenSterilization.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang su dung thuoc cay
//        BoolQueryBuilder boolCoupleUseImplantDrug = QueryBuilders.boolQuery();
//        boolCoupleUseImplantDrug.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleUseImplantDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_CAY));
//        boolCoupleUseImplantDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleUseImplantDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleUseImplantDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleUseImplantDrug).toString(), String.class));
//        dsh03Sub.setTotalCoupleUseImplantDrug(new Gson().fromJson(String.valueOf(jsonCoupleUseImplantDrug.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang  su dung thuoc tiem
//        BoolQueryBuilder boolCoupleUseInjectDrug = QueryBuilders.boolQuery();
//        boolCoupleUseInjectDrug.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleUseInjectDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_TIEM));
//        boolCoupleUseInjectDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleUseInjectDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleUseInjectDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleUseInjectDrug).toString(), String.class));
//        dsh03Sub.setTotalCoupleUseInjectDrug(new Gson().fromJson(String.valueOf(jsonCoupleUseInjectDrug.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang  su dung thuoc uong
//        BoolQueryBuilder boolCoupleDrinkDrug = QueryBuilders.boolQuery();
//        boolCoupleDrinkDrug.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleDrinkDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.THUOC_UONG));
//        boolCoupleDrinkDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleDrinkDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleDrinkDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleDrinkDrug).toString(), String.class));
//        dsh03Sub.setTotalCoupleUseDrinkDrug(new Gson().fromJson(String.valueOf(jsonCoupleDrinkDrug.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang  su dung BCS
//        BoolQueryBuilder boolCoupleUsePlastic = QueryBuilders.boolQuery();
//        boolCoupleUsePlastic.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleUsePlastic.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.BCS));
//        boolCoupleUsePlastic.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleUsePlastic.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleUsePlastic = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleUsePlastic).toString(), String.class));
//        dsh03Sub.setTotalCoupleUsePlastic(new Gson().fromJson(String.valueOf(jsonCoupleUsePlastic.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang  su dung bien phap khac
//        BoolQueryBuilder boolCoupleUseOtherMethod = QueryBuilders.boolQuery();
//        boolCoupleUseOtherMethod.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleUseOtherMethod.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.BIEN_PHAP_KHAC));
//        boolCoupleUseOtherMethod.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleUseOtherMethod.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleUseOtherMethod = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleUseOtherMethod).toString(), String.class));
//        dsh03Sub.setTotalCoupleUseOtherMethod(new Gson().fromJson(String.valueOf(jsonCoupleUseOtherMethod.path("count")), Integer.class));
//        // so cap vo chong trong do tuoi sinh de  hien dang khong su dung BPTT
//        BoolQueryBuilder boolCoupleNoUseContraceptive = QueryBuilders.boolQuery();
//        boolCoupleNoUseContraceptive.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
//        boolCoupleNoUseContraceptive.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", Config.KHONG_SU_DUNG));
//        boolCoupleNoUseContraceptive.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolCoupleNoUseContraceptive.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonCoupleNoUseContraceptive = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolCoupleNoUseContraceptive).toString(), String.class));
//        dsh03Sub.setTotalCoupleNoUseContraceptive(new Gson().fromJson(String.valueOf(jsonCoupleNoUseContraceptive.path("count")), Integer.class));
//// so cong tac vien
//        BoolQueryBuilder boolCTV = QueryBuilders.boolQuery();
//        boolCTV.must(QueryBuilders.matchQuery("districtId", request.regionId));
//        JsonNode jsonCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolCTV).toString(), String.class));
//        dsh03Sub.setTotalCTV(jsonCTV.findPath("count").intValue());
//        // so cong tac vien nu
//        BoolQueryBuilder boolWomenCTV = QueryBuilders.boolQuery();
//        boolWomenCTV.must(QueryBuilders.matchQuery("districtId", request.regionId));
//        boolWomenCTV.must(QueryBuilders.matchQuery("sexId", Config.MALE));
//        JsonNode jsonWomenCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolWomenCTV).toString(), String.class));
//        dsh03Sub.setTotalWomenCTV(dsh03Sub.totalWomenCTV + jsonWomenCTV.findPath("count").intValue());
//        // so cong tac vien moi thue trong nam
//        BoolQueryBuilder boolNewCTV = QueryBuilders.boolQuery();
//        boolNewCTV.must(QueryBuilders.matchQuery("districtId", request.regionId));
//        boolNewCTV.must(QueryBuilders.rangeQuery("hireDate").gte(StringUtils.convertDateToLong(startDayOfYear.toString(), "yyyy-MM-dd")));
//        boolNewCTV.must(QueryBuilders.rangeQuery("hireDate").lte(StringUtils.convertDateToLong(endDayOfYear.toString(), "yyyy-MM-dd")));
//        JsonNode jsonNewCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolNewCTV).toString(), String.class));
//        dsh03Sub.setTotalNewCTV(new Gson().fromJson(String.valueOf(jsonNewCTV.path("count").toString()), Integer.class));
        return reportTemplate.addSub(dsh03Sub);
    }
}