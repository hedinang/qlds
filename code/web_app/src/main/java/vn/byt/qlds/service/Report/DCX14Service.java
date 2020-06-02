package vn.byt.qlds.service.Report;

import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCX.DCX14.DCX14;
import vn.byt.qlds.model.report.DCX.DCX14.DCX14Sub;
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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/*DANH SÁCH CÔNG DÂN TỪ 18 TUỔI TRỞ LÊN*/
@Component
public class DCX14Service extends ReportService {
    private final int AGE_START = 18;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX14();
        super.generateReport(request);
        CategoryMapping categoryMapping = new CategoryMapping();
        categoryMapping.mapEthic = getAllCategory(Config.INDEX_ETH, "id","name");
        categoryMapping.mapGender = getAllCategory(Config.INDEX_GENDER, "id","name");
        JsonNode listAddress = getListAddress(request.regionId);
        for (JsonNode address: listAddress) {
            String addressName = address.findPath("name").textValue();
            Integer addressId = address.findPath("id").asInt();
            categoryMapping.mapHouseHold = getMappingHouseHold(request.regionId, addressId);
            getListPersonByAddress(request, addressId, addressName,categoryMapping);
        }
        return reportTemplate;
    }

    public void getListPersonByAddress(ReportRequest request, Integer addressID, String addressName, CategoryMapping mapping) throws ParseException, JsonProcessingException {
        /*tuổi từ x -> y => x < endtime - birthday < y*/
        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long ageLte = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - 18L), format_date);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("addressId", addressID));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));
        boolQuery.must(QueryBuilders.rangeQuery("dateOfBirth").lte(ageLte));
        ObjectMapper objectMapper = new ObjectMapper();
        searchSourceBuilder.size(1000000);
        String resultPerson = qldsRestTemplate.postForObject(
                url + "/person/_search",
                searchSourceBuilder.query(boolQuery).toString(),
                String.class);
        assert resultPerson != null;
        JsonNode hits = objectMapper.readTree(resultPerson).path("hits").path("hits");
        for (JsonNode jsonNode : hits) {
            Integer houseHoldNumber = jsonNode.findPath("houseHoldId").intValue();
            String houseHoldName = mapping.mapHouseHold.get(houseHoldNumber);
            String fullName = jsonNode.findPath("fullName").textValue();
            String dateOfBirth = StringUtils.convertLongToDateString(jsonNode.findPath("dateOfBirth").longValue(), format_date);
            Integer sexId = jsonNode.findPath("sexId").asInt();
            String sexName = mapping.mapGender.get(sexId);
            Integer ethnicId = jsonNode.findPath("ethnicId").asInt();
            String ethnicName = mapping.mapEthic.get(ethnicId);

            DCX14Sub dcx14Sub = new DCX14Sub();
            dcx14Sub.houseHoldNumber = String.valueOf(houseHoldNumber);
            dcx14Sub.fullName = fullName;
            dcx14Sub.dateOfBirth = dateOfBirth;
            dcx14Sub.gender = sexName;
            dcx14Sub.ethenic = ethnicName;
            dcx14Sub.houseHoldName = houseHoldName;
            ((DCX14) reportTemplate).addSub(addressID, addressName, dcx14Sub);
        }
    }

    protected Map<Integer, String> getMappingHouseHold(final String regionId, final  Integer addressId) throws JsonProcessingException {
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

    static class CategoryMapping {
        Map<Integer, String> mapEthic;
        Map<Integer, String> mapGender;
        Map<Integer, String> mapHouseHold;
    }
}
