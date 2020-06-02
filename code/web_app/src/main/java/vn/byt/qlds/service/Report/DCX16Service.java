package vn.byt.qlds.service.Report;

import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCX.DCX;
import vn.byt.qlds.model.report.DCX.DCX16.DCX16Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/*DANH SÁCH TRẺ EM ĐẾN TUỔI VÀO LỚP 1*/
/*Tinh den nam tre em duoc 6 tuoi*/
/*01/01/(yyyy - 6) => 31/12/(yyyy-6)*/
@Component
public class DCX16Service extends ReportService {

    private static final int AGE = 6;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX() {
        };
        super.generateReport(request);

        JsonNode listAddress = getListAddress(request.regionId);
        DCX14Service.CategoryMapping categoryMapping = new DCX14Service.CategoryMapping();
        for (JsonNode address : listAddress) {
            String addressName = address.findPath("name").textValue();
            Integer addressId = address.findPath("id").asInt();
            categoryMapping.mapHouseHold = getMappingHouseHold(request.regionId, addressId);
            getListPersonByAddress(request, addressId, addressName, categoryMapping);
        }
        return reportTemplate;
    }

    public void getListPersonByAddress(ReportRequest request, Integer addressID, String addressName, DCX14Service.CategoryMapping mapping) throws ParseException, JsonProcessingException {
        /*tuổi từ x -> y => x < endtime - birthday < y*/
        String dateStart = "01/01/" + (request.year - AGE);
        String dateEnd = "31/12/" + (request.year - AGE);
        Long ageLte = StringUtils.convertDateToLong(dateEnd, format_date);
        Long ageGte = StringUtils.convertDateToLong(dateStart, format_date);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("addressId", addressID));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("dateOfBirth").lte(ageLte));
        boolQuery.must(QueryBuilders.rangeQuery("dateOfBirth").gte(ageGte));
        ObjectMapper objectMapper = new ObjectMapper();
        searchSourceBuilder.size(1000000);
        String resultPerson = qldsRestTemplate.postForObject(
                url + "/person/_search",
                searchSourceBuilder.query(boolQuery).toString(),
                String.class);
        assert resultPerson != null;
        JsonNode hits = objectMapper.readTree(resultPerson).path("hits").path("hits");
        for (JsonNode jsonNode : hits) {
            String fullName = jsonNode.findPath("fullName").textValue();
            String dateOfBirth = StringUtils.convertLongToDateString(jsonNode.findPath("dateOfBirth").longValue(), format_date);
            Integer houseHoldNumber = jsonNode.findPath("houseHoldId").intValue();
            String houseHoldName = mapping.mapHouseHold.get(houseHoldNumber);
            DCX16Sub dcx16Sub = new DCX16Sub();
            dcx16Sub.fullName = fullName;
            dcx16Sub.dateOfBirth = dateOfBirth;
            dcx16Sub.addressName = addressName;
            dcx16Sub.houseNumber = houseHoldName;
            reportTemplate.addSub(dcx16Sub);
        }
    }

    protected Map<Integer, String> getMappingHouseHold(final String regionId, final Integer addressId) throws JsonProcessingException {
        Map<Integer, String> idToName = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> query = new HashMap<>();
        query.put("isDeleted", false);
        query.put("regionId", regionId);
        query.put("addressId", addressId);
        SearchSourceBuilder searchSourceBuilder = elasticSearchService.searchAndBuilder(query);
        searchSourceBuilder.size(max_size_category);
        searchSourceBuilder.fetchSource(new String[]{
                "id",
                "houseHoldNumber"
        }, null);
        String resultEth = qldsRestTemplate.postForObject(
                url + "/house-hold/_search",
                searchSourceBuilder.toString(),
                String.class);
        assert resultEth != null;
        JsonNode hits = objectMapper.readTree(resultEth).path("hits").path("hits");
        for (JsonNode hit : hits) {
            Integer id = hit.findPath("id").asInt();
            String name = hit.findPath("houseHoldNumber").textValue();
            idToName.put(id, name);
        }
        return idToName;
    }

}
