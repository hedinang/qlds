package vn.byt.qlds.service.Report;

import vn.byt.qlds.client.ReportDesignClient;
import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.ElasticSearchService;
import vn.byt.qlds.model.report.ReportDesign;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public abstract class ReportService {
    protected static final String format_date = "dd/MM/yyyy";
    @Autowired
    public ReportDesignClient reportDesignClient;
    @Autowired
    public QldsRestTemplate qldsRestTemplate;
    @Value("${urlES}")
    protected String url;
    protected ReportTemplate reportTemplate;
    @Autowired
    ElasticSearchService elasticSearchService;
    protected static final int max_size_category = 10000;
    ObjectMapper objectMapper = new ObjectMapper();
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
//        reportTemplate = createReport();
        ReportDesign reportDesign = reportDesignClient
                .getReportDesign(request.rcId)
                .orElse(new ReportDesign());
        reportTemplate.caption1 = reportDesign.getCaption1();
        reportTemplate.caption2 = reportDesign.getCaption2();
        reportTemplate.caption3 = reportDesign.getCaption3();
        reportTemplate.footerL = reportDesign.getFooterL();
        reportTemplate.footerR = reportDesign.getFooterR();
        reportTemplate.headerL = reportDesign.getHeaderL();
        reportTemplate.headerR = reportDesign.getHeaderR();
        reportTemplate.subtitle = reportDesign.getSubtitle();
        reportTemplate.rcId = reportDesign.getRcId();

        reportTemplate.endTime = request.endTime;
        reportTemplate.startTime = request.startTime;
        reportTemplate.regionName = getAddress(request.rcId, request.regionId);
        reportTemplate.year = request.year;
        reportTemplate.quater = request.quarter;
        reportTemplate.month = request.month;
        return reportTemplate;
    }

    /*
     *  regionID: String mã unit-category có levels = 3// cấp xã/ thị trấn/ phường
     * */
    protected String getRegionAddress(String regionID) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonRegion = objectMapper.readTree(qldsRestTemplate.getForObject(url + "/unit-category/_search?q=code:" + regionID, String.class));
        JsonNode hitsRegion = jsonRegion.path("hits").path("hits");

        String districtID = hitsRegion.isEmpty() ? "" : hitsRegion.get(0).findPath("parent").textValue();
        String nameRegion = hitsRegion.isEmpty() ? "" : hitsRegion.get(0).findPath("name").textValue();

        JsonNode jsonDistrict = objectMapper.readTree(qldsRestTemplate.getForObject(url + "/unit-category/_search?q=code:" + districtID, String.class));
        JsonNode hitsDistrict = jsonDistrict.path("hits").path("hits");

        String provinceID = hitsDistrict.isEmpty() ? "" : hitsDistrict.get(0).findPath("parent").textValue();
        String nameDistrict = hitsDistrict.isEmpty() ? "" : hitsDistrict.get(0).findPath("name").textValue();

        JsonNode jsonProvince = objectMapper.readTree(qldsRestTemplate.getForObject(url + "/unit-category/_search?q=code:" + provinceID, String.class));
        JsonNode hitsProvince = jsonProvince.path("hits").path("hits");

        String nameProvince = hitsProvince.isEmpty() ? "" : hitsProvince.get(0).findPath("name").textValue();

        return String.format("%s, %s, %s", nameRegion, nameDistrict, nameProvince);

    }

    protected String getDistrictAddress(String districtID) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonDistrict = objectMapper.readTree(qldsRestTemplate.getForObject(url + "/unit-category/_search?q=code:" + districtID, String.class));
        JsonNode hitsDistrict = jsonDistrict.path("hits").path("hits");

        String provinceID = hitsDistrict.isEmpty() ? "" : hitsDistrict.get(0).findPath("parent").textValue();
        String nameDistrict = hitsDistrict.isEmpty() ? "" : hitsDistrict.get(0).findPath("name").textValue();

        JsonNode jsonProvince = objectMapper.readTree(qldsRestTemplate.getForObject(url + "/unit-category/_search?q=code:" + provinceID, String.class));
        JsonNode hitsProvince = jsonProvince.path("hits").path("hits");

        String nameProvince = hitsProvince.isEmpty() ? "" : hitsProvince.get(0).findPath("name").textValue();

        return String.format("%s, %s", nameDistrict, nameProvince);
    }

    protected String getProvinceAddress(String provinceID) throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonProvince = objectMapper.readTree(qldsRestTemplate.getForObject(url + "/unit-category/_search?q=code:" + provinceID, String.class));
        JsonNode hitsProvince = jsonProvince.path("hits").path("hits");

        String nameProvince = hitsProvince.isEmpty() ? "" : hitsProvince.get(0).findPath("name").textValue();
        return nameProvince;
    }

    protected String getAddress(String typeReport, String regionID) throws JsonProcessingException {
        if (typeReport.startsWith(Config.DCX) || typeReport.startsWith(Config.DSX)) return getRegionAddress(regionID);
        else if (typeReport.startsWith(Config.DSH) || typeReport.startsWith(Config.DCH))
            return getDistrictAddress(regionID);
        else if (typeReport.startsWith(Config.DST)) return getProvinceAddress(regionID);
        else return null;
    }

    protected JsonNode getListAddress(String regionId) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> mapAddress = new HashMap<>();
        mapAddress.put("regionId", regionId);
        mapAddress.put("isDeleted", false);
        SearchSourceBuilder searchSourceBuilder = elasticSearchService.searchAndBuilder(mapAddress);
        searchSourceBuilder.fetchSource(new String[]{
                "id",
                "name",
                "collaboratorId"
        }, null);
        searchSourceBuilder.size(max_size_category);
        String resultAddress = qldsRestTemplate.postForObject(
                url + "/address/_search",
                searchSourceBuilder.toString(),
                String.class);
        assert resultAddress != null;
        return objectMapper.readTree(resultAddress).path("hits").path("hits");
    }

    protected JsonNode getCommune(ReportRequest request) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> mapAddress = new HashMap<>();
        mapAddress.put("parent", request.regionId);
        mapAddress.put("isDeleted", false);
        mapAddress.put("levels", 3);
        SearchSourceBuilder searchSourceBuilder = elasticSearchService.searchAndBuilder(mapAddress);
        searchSourceBuilder.size(max_size_category);
        searchSourceBuilder.fetchSource(new String[]{
                "id",
                "code",
                "name"
        }, null);
        String result = qldsRestTemplate.postForObject(
                url + "/unit-category/_search",
                searchSourceBuilder.toString(),
                String.class);
        assert result != null;
        return objectMapper.readTree(result).path("hits").path("hits");
    }

    protected Map<Integer, String> getAllCategory(final String index, final String keyField, final String valueField) throws JsonProcessingException {
        Map<Integer, String> idToName = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> query = new HashMap<>();
        query.put("isDeleted", false);
        SearchSourceBuilder searchSourceBuilder = elasticSearchService.searchAndBuilder(query);
        searchSourceBuilder.size(max_size_category);
        searchSourceBuilder.fetchSource(new String[]{keyField, valueField}, null);
        String resultEth = qldsRestTemplate.postForObject(
                url + "/" + index + "/_search",
                searchSourceBuilder.toString(),
                String.class);
        assert resultEth != null;
        JsonNode hits = objectMapper.readTree(resultEth).path("hits").path("hits");
        for (JsonNode hit : hits) {
            Integer id = hit.findPath(keyField).asInt();
            String name = hit.findPath(valueField).textValue();
            idToName.put(id, name);
        }
        return idToName;
    }


}
