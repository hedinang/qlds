package vn.byt.qlds.service.Report;//package vn.byt.qlds.service.Report;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//import org.elasticsearch.index.query.BoolQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import vn.byt.qlds.client.ReportDesignClient;
//import vn.byt.qlds.configuration.TenantRestTemplate;
//import vn.byt.qlds.core.es.ElasticSearchService;
//import vn.byt.qlds.core.utils.StringUtils;
//import vn.byt.qlds.model.UnitCategory;
//import vn.byt.qlds.model.report.DST.DST02.DST02;
//import vn.byt.qlds.model.report.DST.DST02.DST02Sub;
//import vn.byt.qlds.model.response.ReportDesignResponse;
//
//import java.text.ParseException;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class DST02Service {
//
//    @Autowired
//    ElasticSearchService elasticSearchService;
//    @Autowired
//    TenantRestTemplate tenantRestTemplate;
//    @Autowired
//    ReportDesignClient reportDesignClient;
//    @Value("${urlES}")
//    public String url;
//    private static int FA = 1;
//    private static int CoVoChong = 2;
//    private static int LyHon = 4;
//    private static int LyThan = 5;
//    private static int Goa = 6;
//    private static int Nam = 2;
//    private static int Nu = 1;
//    private static int TamTru = 3;
//    private static int Chet = 16;
//    private static int DiKhoi = 13;
//    private static int ChuyenDen = 14;
//    private static int HonNhan = 18;
//    private static String Ket_Hon = "MarCode2";
//    private static String Ly_Hon = "MarCode4";
//    private static int KhongSuDung = 1;
//    private static int VongTranhThai = 2;
//    private static int ThayVongTranhThai = 3;
//    private static int ThoiVongTranhThai = 4;
//    private static int TrietSanNam = 5;
//    private static int TrietSanNu = 6;
//    private static int BCS = 7;
//    private static int ThuocUong = 8;
//    private static int ThuocTiem = 9;
//    private static int ThuocCay = 10;
//    private static int ThayThuocCay = 11;
//    private static int ThoiThuocCay = 12;
//    private static int BienPhapKhac = 13;
//
//
//    public DST02 generateDST02(DST02Request DST02Request) throws ParseException, JsonProcessingException {
//        // get address theo regionId
//        LocalDate startDayOfQuater;
//        LocalDate endDayOfQuater;
//        LocalDate filterLowWomen;
//        LocalDate filterHighWomen;
//
//        switch (DST02Request.getQuater()) {
//            case 2:
//                startDayOfQuater = LocalDate.of(DST02Request.getYear(), 4, 1);
//                endDayOfQuater = LocalDate.of(DST02Request.getYear(), 6, 30);
//                filterLowWomen = LocalDate.of(DST02Request.getYear() - 49, 4, 1); // birthDay > ngay dau tien cua thang
//                filterHighWomen = LocalDate.of(DST02Request.getYear() - 15, 6, 30);// birthDay < ngay cuoi cung cua nam
//                break;
//            case 3:
//                startDayOfQuater = LocalDate.of(DST02Request.getYear(), 7, 1);
//                endDayOfQuater = LocalDate.of(DST02Request.getYear(), 9, 30);
//                filterLowWomen = LocalDate.of(DST02Request.getYear() - 49, 7, 1); // birthDay > ngay dau tien cua thang
//                filterHighWomen = LocalDate.of(DST02Request.getYear() - 15, 9, 30);// birthDay < ngay cuoi cung cua nam
//                break;
//            case 4:
//                startDayOfQuater = LocalDate.of(DST02Request.getYear(), 10, 1);
//                endDayOfQuater = LocalDate.of(DST02Request.getYear(), 12, 31);
//                filterLowWomen = LocalDate.of(DST02Request.getYear() - 49, 10, 1); // birthDay > ngay dau tien cua thang
//                filterHighWomen = LocalDate.of(DST02Request.getYear() - 15, 12, 30);// birthDay < ngay cuoi cung cua nam
//                break;
//            default:// mac dinh la 1
//                startDayOfQuater = LocalDate.of(DST02Request.getYear(), 1, 1);
//                endDayOfQuater = LocalDate.of(DST02Request.getYear(), 3, 31);
//                filterLowWomen = LocalDate.of(DST02Request.getYear() - 49, 1, 1); // birthDay > ngay dau tien cua thang
//                filterHighWomen = LocalDate.of(DST02Request.getYear() - 15, 3, 30);// birthDay < ngay cuoi cung cua nam
//        }
//        List<DST02Sub> DST02SubList = new ArrayList<>();
//        ObjectMapper objectMapper = new ObjectMapper();
//        Map mapAddress = new HashMap();
//        mapAddress.put("parent", DST02Request.getRegionId());
//        JsonNode jsonNode = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/unit-category/_search", elasticSearchService.searchAndBuilder(mapAddress).toString(), String.class));
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        DST02Sub T = new DST02Sub();// toan xa
//        for (JsonNode element : jsonNode.path("hits").path("hits")) {
//            DST02Sub DST02Sub = new DST02Sub();
//            UnitCategory unitCategory = new Gson().fromJson(String.valueOf(element.path("_source")), UnitCategory.class);
//            DST02Sub.setAddress(unitCategory.getTen());
//            //lay tong so ho gia dinh
//            BoolQueryBuilder boolHouseHold = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolHouseHold.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonHouseHold = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/house-hold/_count", searchSourceBuilder.query(boolHouseHold).toString(), String.class));
//            DST02Sub.setTotalHouseHold(new Gson().fromJson(String.valueOf(jsonHouseHold.path("count")), Integer.class));
//            T.setTotalHouseHold(T.getTotalHouseHold() + DST02Sub.getTotalHouseHold());
//            // lay tong so ho ko phai la ho tap the
//            BoolQueryBuilder boolNotBigHouseHold = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolNotBigHouseHold.must(QueryBuilders.matchQuery("isBigHouseHold", 0));
//            boolNotBigHouseHold.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonNotBigHouseHold = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/house-hold/_count", searchSourceBuilder.query(boolNotBigHouseHold).toString(), String.class));
//            DST02Sub.setTotalNotBigHouseHold(new Gson().fromJson(String.valueOf(jsonNotBigHouseHold.path("count")), Integer.class));
//            T.setTotalNotBigHouseHold(T.getTotalNotBigHouseHold() + DST02Sub.getTotalNotBigHouseHold());
//            //   Lay tong so nhan khau
//            // neu co khau nao chuyen di hoac xoa thi isDeleted = 1
//            // residenceCode = 3 la tam tru
//            // lay nguoi con song nen ko can check endDate
//            BoolQueryBuilder boolResident = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolResident.mustNot(QueryBuilders.matchQuery("residenceCode", TamTru));
//            boolResident.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//            boolResident.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//            boolResident.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonResident = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolResident).toString(), String.class));
//            DST02Sub.setTotalResident(new Gson().fromJson(String.valueOf(jsonResident.path("count")), Integer.class));
//            T.setTotalResident(T.getTotalResident() + DST02Sub.getTotalResident());
//            //          tong so phu nu co tuoi 15 den 49
//            BoolQueryBuilder boolWomenLt49 = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolWomenLt49.mustNot(QueryBuilders.matchQuery("sexId", Nu));
//            boolWomenLt49.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//            boolWomenLt49.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//            boolWomenLt49.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(filterLowWomen.toString(), "dd/MM/yyyy")));
//            boolWomenLt49.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(filterHighWomen.toString(), "dd/MM/yyyy")));
//            JsonNode jsonWomenLt49 = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolWomenLt49).toString(), String.class));
//            DST02Sub.setTotalWomenLt49(new Gson().fromJson(String.valueOf(jsonWomenLt49.path("count")), Integer.class));
//            T.setTotalWomenLt49(T.getTotalWomenLt49() + DST02Sub.getTotalWomenLt49());
//            //          tong so phu nu co chong co tuoi tu 15 den 49
//            BoolQueryBuilder boolWomenLt49HaveHusband = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolWomenLt49HaveHusband.must(QueryBuilders.matchQuery("maritalCode", CoVoChong));
//            boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("sexId", Nu));
//            boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("personStatus", "G"));
//            boolWomenLt49HaveHusband.mustNot(QueryBuilders.matchQuery("personStatus", "D"));
//            boolWomenLt49HaveHusband.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(filterLowWomen.toString(), "dd/MM/yyyy")));
//            boolWomenLt49HaveHusband.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(filterHighWomen.toString(), "dd/MM/yyyy")));
//            JsonNode jsonWomenLt49HaveHusband = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolWomenLt49HaveHusband).toString(), String.class));
//            DST02Sub.setTotalWomenLt49HaveHusband(new Gson().fromJson(String.valueOf(jsonWomenLt49HaveHusband.path("count")), Integer.class));
//            T.setTotalWomenLt49HaveHusband(T.getTotalWomenLt49HaveHusband() + DST02Sub.getTotalWomenLt49HaveHusband());
//            // so nguoi chet trong quy
//            BoolQueryBuilder boolDiePerson = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolDiePerson.mustNot(QueryBuilders.matchQuery("residenceCode", TamTru));
//            boolDiePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", Chet));
//            boolDiePerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolDiePerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonDiePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolDiePerson).toString(), String.class));
//            DST02Sub.setTotalDiePerson(new Gson().fromJson(String.valueOf(jsonDiePerson.path("count")), Integer.class));
//            T.setTotalDiePerson(T.getTotalDiePerson() + DST02Sub.getTotalDiePerson());
//            // so nguoi ket hon trong quy
//            BoolQueryBuilder boolMarryPerson = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolMarryPerson.mustNot(QueryBuilders.matchQuery("residenceCode", TamTru));
//            boolMarryPerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", HonNhan));
//            boolMarryPerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolMarryPerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolMarryPerson.must(QueryBuilders.matchQuery("notes", Ket_Hon));
//            JsonNode jsonMarryPerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolMarryPerson).toString(), String.class));
//            DST02Sub.setTotalMarryPerson(new Gson().fromJson(String.valueOf(jsonMarryPerson.path("count")), Integer.class));
//            T.setTotalMarryPerson(T.getTotalMarryPerson() + DST02Sub.getTotalMarryPerson());
//            //so nguoi ly hon trong quy
//            BoolQueryBuilder boolDivorcePerson = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolDivorcePerson.mustNot(QueryBuilders.matchQuery("residenceCode", TamTru));
//            boolDivorcePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", HonNhan));
//            boolDivorcePerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolDivorcePerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolDivorcePerson.must(QueryBuilders.matchQuery("notes", Ly_Hon));
//            JsonNode jsonDivorcePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolDivorcePerson).toString(), String.class));
//            DST02Sub.setTotalDivorcePerson(new Gson().fromJson(String.valueOf(jsonDivorcePerson.path("count")), Integer.class));
//            T.setTotalDivorcePerson(T.getTotalDivorcePerson() + DST02Sub.getTotalDivorcePerson());
//            //so nguoi di ra  trong quy
//            BoolQueryBuilder boolGoPerson = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolGoPerson.mustNot(QueryBuilders.matchQuery("residenceCode", TamTru));
//            boolGoPerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", DiKhoi));
//            boolGoPerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolGoPerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonGoPerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolGoPerson).toString(), String.class));
//            DST02Sub.setTotalGoPerson(new Gson().fromJson(String.valueOf(jsonGoPerson.path("count")), Integer.class));
//            T.setTotalGoPerson(T.getTotalGoPerson() + DST02Sub.getTotalGoPerson());
//            // so nguoi chuyen den tu noi khac
//            BoolQueryBuilder boolComePerson = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolComePerson.mustNot(QueryBuilders.matchQuery("residenceCode", TamTru));
//            boolComePerson.must(QueryBuilders.matchQuery("reasonChangeRequest.changeTypeCode", ChuyenDen));
//            boolComePerson.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolComePerson.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonComePerson = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/person-history/_count", searchSourceBuilder.query(boolComePerson).toString(), String.class));
//            DST02Sub.setTotalComePerson(new Gson().fromJson(String.valueOf(jsonComePerson.path("count")), Integer.class));
//            T.setTotalComePerson(T.getTotalComePerson() + DST02Sub.getTotalComePerson());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung BPTT
//            BoolQueryBuilder boolUseContraceptive = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", VongTranhThai));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", ThayVongTranhThai));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", TrietSanNam));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", TrietSanNu));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", BCS));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", ThuocUong));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", ThuocTiem));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", ThuocCay));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", ThayThuocCay));
//            boolUseContraceptive.should(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", BienPhapKhac));
//            boolUseContraceptive.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolUseContraceptive.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonUseContraceptive = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseContraceptive).toString(), String.class));
//            DST02Sub.setTotalUseContraceptive(new Gson().fromJson(String.valueOf(jsonUseContraceptive.path("count")), Integer.class));
//            T.setTotalUseContraceptive(T.getTotalUseContraceptive() + DST02Sub.getTotalUseContraceptive());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung dat vong tranh thai
//            BoolQueryBuilder boolUseRing = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolUseRing.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", VongTranhThai));
//            boolUseRing.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolUseRing.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonUseRing = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseRing).toString(), String.class));
//            DST02Sub.setTotalUseRing(new Gson().fromJson(String.valueOf(jsonUseRing.path("count")), Integer.class));
//            T.setTotalUseRing(T.getTotalUseRing() + DST02Sub.getTotalUseRing());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung triet san nam
//            BoolQueryBuilder boolMenSterilization = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolMenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", TrietSanNam));
//            boolMenSterilization.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolMenSterilization.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonMenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolMenSterilization).toString(), String.class));
//            DST02Sub.setTotalMenSterilization(new Gson().fromJson(String.valueOf(jsonMenSterilization.path("count")), Integer.class));
//            T.setTotalMenSterilization(T.getTotalMenSterilization() + DST02Sub.getTotalMenSterilization());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung triet san nu
//            BoolQueryBuilder boolWomenSterilization = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolWomenSterilization.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", TrietSanNu));
//            boolWomenSterilization.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolWomenSterilization.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonWomenSterilization = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolWomenSterilization).toString(), String.class));
//            DST02Sub.setTotalWomenSterilization(new Gson().fromJson(String.valueOf(jsonWomenSterilization.path("count")), Integer.class));
//            T.setTotalWomenSterilization(T.getTotalWomenSterilization() + DST02Sub.getTotalWomenSterilization());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung thuoc cay
//            BoolQueryBuilder boolUseImplantDrug = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolUseImplantDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", ThuocCay));
//            boolUseImplantDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolUseImplantDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonUseImplantDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseImplantDrug).toString(), String.class));
//            DST02Sub.setTotalUseImplantDrug(new Gson().fromJson(String.valueOf(jsonUseImplantDrug.path("count")), Integer.class));
//            T.setTotalUseImplantDrug(T.getTotalUseImplantDrug() + DST02Sub.getTotalUseImplantDrug());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung thuoc tiem
//            BoolQueryBuilder boolUseInjectDrug = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolUseInjectDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", ThuocTiem));
//            boolUseInjectDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolUseInjectDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonUseInjectDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseInjectDrug).toString(), String.class));
//            DST02Sub.setTotalUseInjectDrug(new Gson().fromJson(String.valueOf(jsonUseInjectDrug.path("count")), Integer.class));
//            T.setTotalUseDrinkDrug(T.getTotalUseInjectDrug() + DST02Sub.getTotalUseInjectDrug());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung thuoc uong
//
//            BoolQueryBuilder boolDrinkDrug = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolDrinkDrug.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", ThuocUong));
//            boolDrinkDrug.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolDrinkDrug.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonDrinkDrug = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolDrinkDrug).toString(), String.class));
//            DST02Sub.setTotalUseDrinkDrug(new Gson().fromJson(String.valueOf(jsonUseInjectDrug.path("count")), Integer.class));
//            T.setTotalUseDrinkDrug(T.getTotalUseDrinkDrug() + DST02Sub.getTotalUseDrinkDrug());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung BCS
//            BoolQueryBuilder boolUsePlastic = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolUsePlastic.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", BCS));
//            boolUsePlastic.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolUsePlastic.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonUsePlastic = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUsePlastic).toString(), String.class));
//            DST02Sub.setTotalUsePlastic(new Gson().fromJson(String.valueOf(jsonUsePlastic.path("count")), Integer.class));
//            T.setTotalUsePlastic(T.getTotalUsePlastic() + DST02Sub.getTotalUsePlastic());
//            // so cap vo chong trong do tuoi sinh de  hien dang  su dung bien phap khac
//
//            BoolQueryBuilder boolUseOtherMethod = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolUseOtherMethod.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", BienPhapKhac));
//            boolUseOtherMethod.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolUseOtherMethod.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonUseOtherMethod = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolUseOtherMethod).toString(), String.class));
//            DST02Sub.setTotalUseOtherMethod(new Gson().fromJson(String.valueOf(jsonUseOtherMethod.path("count")), Integer.class));
//            T.setTotalUseOtherMethod(T.getTotalUseOtherMethod() + DST02Sub.getTotalUseOtherMethod());
//            // so cap vo chong trong do tuoi sinh de  hien dang khong su dung BPTT
//            BoolQueryBuilder boolNoUseContraceptive = QueryBuilders.boolQuery();
//            boolHouseHold.must(QueryBuilders.matchQuery("unitCategoryRequest.ma", unitCategory.getId()));
//            boolNoUseContraceptive.must(QueryBuilders.matchQuery("contraceptiveCategoryRequest.id", KhongSuDung));
//            boolNoUseContraceptive.must(QueryBuilders.rangeQuery("startDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//            boolNoUseContraceptive.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//            JsonNode jsonNoUseContraceptive = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/family-planning-history/_count", searchSourceBuilder.query(boolNoUseContraceptive).toString(), String.class));
//            DST02Sub.setTotalNoUseContraceptive(new Gson().fromJson(String.valueOf(jsonNoUseContraceptive.path("count")), Integer.class));
//            T.setTotalNoUseContraceptive(T.getTotalNoUseContraceptive() + DST02Sub.getTotalNoUseContraceptive());
//            DST02SubList.add(DST02Sub);
//        }
//        // so cap vo chong trong do tuoi sinh de  hien dang khong su dung BPTT co 2 con 1 be
//        BoolQueryBuilder boolTwoChild = QueryBuilders.boolQuery();
//        boolTwoChild.must(QueryBuilders.matchQuery("regionId", DST02Request.getRegionId()));
//        boolTwoChild.must(QueryBuilders.matchQuery("rptYear", DST02Request.getYear()));
//        boolTwoChild.must(QueryBuilders.matchQuery("rptQuarter", DST02Request.getQuater()));
//        JsonNode jsonTwoChild = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/statistic/_search", searchSourceBuilder.query(boolTwoChild).toString(), String.class));
//        T.setTotalTwoChild(new Gson().fromJson(String.valueOf(jsonTwoChild.path("hit").path("hit").get(0).path("_source").path("twoChildrenPair")), Integer.class));
//// so cap vo chong trong do tuoi sinh de  hien dang khong su dung BPTT co hon 3 con
//        BoolQueryBuilder boolThreeChild = QueryBuilders.boolQuery();
//        boolThreeChild.must(QueryBuilders.matchQuery("regionId", DST02Request.getRegionId()));
//        boolThreeChild.must(QueryBuilders.matchQuery("rptYear", DST02Request.getYear()));
//        boolThreeChild.must(QueryBuilders.matchQuery("rptQuarter", DST02Request.getQuater()));
//        JsonNode jsonThreeChild = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/statistic/_search", searchSourceBuilder.query(boolTwoChild).toString(), String.class));
//        T.setTotalThreeChild(new Gson().fromJson(String.valueOf(jsonTwoChild.path("hit").path("hit").get(0).path("_source").path("moreThanThreeChildrenPair")), Integer.class));
//// so cong tac vien
//        BoolQueryBuilder boolCTV = QueryBuilders.boolQuery();
//        boolCTV.must(QueryBuilders.matchQuery("regionId", DST02Request.getRegionId()));
//        JsonNode jsonCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolCTV).toString(), String.class));
//        T.setTotalCTV(new Gson().fromJson(String.valueOf(jsonCTV.path("count").toString()), Integer.class));
//// so cong tac vien nu
//        BoolQueryBuilder boolWomenCTV = QueryBuilders.boolQuery();
//        boolWomenCTV.must(QueryBuilders.matchQuery("regionId", DST02Request.getRegionId()));
//        boolWomenCTV.must(QueryBuilders.matchQuery("sexId", Nu));
//        JsonNode jsonWomenCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolWomenCTV).toString(), String.class));
//        T.setTotalWomenCTV(new Gson().fromJson(String.valueOf(jsonWomenCTV.path("count").toString()), Integer.class));
//// so cong tac vien moi thue trong quy
//        BoolQueryBuilder boolNewCTV = QueryBuilders.boolQuery();
//        boolNewCTV.must(QueryBuilders.matchQuery("regionId", DST02Request.getRegionId()));
//        boolNewCTV.must(QueryBuilders.rangeQuery("hireDate").gte(StringUtils.convertDateToLong(startDayOfQuater.toString(), "dd/MM/yyyy")));
//        boolNewCTV.must(QueryBuilders.rangeQuery("hireDate").lte(StringUtils.convertDateToLong(endDayOfQuater.toString(), "dd/MM/yyyy")));
//        JsonNode jsonNewCTV = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/collaborator/_count", searchSourceBuilder.query(boolNewCTV).toString(), String.class));
//        T.setTotalNewCTV(new Gson().fromJson(String.valueOf(jsonNewCTV.path("count").toString()), Integer.class));
//        DST02SubList.add(T);
//        DST02 DST02 = new DST02();
//        ReportDesignResponse reportDesignResponse = reportDesignClient.getReportDesign(DST02Request.getRcId());
//        DST02.setCaption1(reportDesignResponse.getCaption1());
//        DST02.setCaption2(reportDesignResponse.getCaption2());
//        DST02.setCaption3(reportDesignResponse.getCaption3());
//        DST02.setQuater(DST02Request.getQuater());
//        DST02.setYear(DST02Request.getYear());
//        DST02.setFooterL(reportDesignResponse.getFooterL());
//        DST02.setFooterR(reportDesignResponse.getFooterR());
//        DST02.setHeaderL(reportDesignResponse.getHeaderL());
//        DST02.setHeaderR(reportDesignResponse.getHeaderR());
//        DST02.setRcId(reportDesignResponse.getRcId());
//        BoolQueryBuilder boolRegion = QueryBuilders.boolQuery();
//        boolRegion.must(QueryBuilders.matchQuery("ma", DST02Request.getRegionId()));
//        Map mapUnitCategory = new HashMap();
//        mapUnitCategory.put("ma", DST02Request.getRegionId());
//        JsonNode jsonRegion = objectMapper.readTree(tenantRestTemplate.postForObject(url + "/unit-category/_search", elasticSearchService.searchAndBuilder(mapUnitCategory).toString(), String.class));
//        DST02.setRegionName(new Gson().fromJson(String.valueOf(jsonRegion.path("hits").path("hits").get(0).path("_source").path("ten")), String.class));
//        DST02.setShowFooter(reportDesignResponse.getShowFooter());
//        DST02.setSubtitle(reportDesignResponse.getSubtitle());
//        DST02.setDST02SubList(DST02SubList);
//        return DST02;
//
//    }
//}
