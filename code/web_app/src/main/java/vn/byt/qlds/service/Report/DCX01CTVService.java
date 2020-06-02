package vn.byt.qlds.service.Report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.client.CollaboratorClient;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCX.DCX01CTV.DCX01CTV;
import vn.byt.qlds.model.report.DCX.DCX01CTV.DCX01CTVSub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DCX01CTVService extends ReportService {
    @Autowired
    CollaboratorClient collaboratorClient;
    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX01CTV();
        super.generateReport(request);
        JsonNode hitsAddress = getListAddress(request.regionId);
        JsonNode hitsCollaborator = findNameCTVByID(request.regionId);
        Map<Integer, DCX01CTVSub> codeToDCX01CTVSub = new HashMap<>();
        Map<Integer, Integer> idToCollaboratorId = new HashMap<>();
        Map<Integer, String> idToCollaboratorName = new HashMap<>();

        for (JsonNode element : hitsAddress) {
            Integer collaboratorId = element.findPath("collaboratorId").asInt();
            Integer addressId = element.findPath("id").asInt();
            idToCollaboratorId.put(addressId, collaboratorId);
        }

        for (JsonNode element : hitsCollaborator) {
            Integer collaboratorId = element.findPath("id").asInt();
            String fullName = element.findPath("fullName").asText();
            idToCollaboratorName.put(collaboratorId, fullName);
        }

        JsonNode buckets = counterPersonAddress(request);
        for (JsonNode bucket : buckets) {
            int totalPerson = bucket.findPath("doc_count").intValue();
            Integer addressId = bucket.findPath("key").intValue();
            String collaboratorName = idToCollaboratorName.get(idToCollaboratorId.get(addressId));
            DCX01CTVSub dcx01CTVSub = transferFromJsonNode(bucket);
            dcx01CTVSub.setCollaboratorName(collaboratorName);
            dcx01CTVSub.setTotalPerson(totalPerson);
            codeToDCX01CTVSub.put(addressId, dcx01CTVSub);
        }

        JsonNode bucketsHouseHold = counterHouseHold(request);
        for (JsonNode bucket : bucketsHouseHold) {
            Integer addressId = bucket.findPath("key").intValue();
            int totalHouseHold = bucket.findPath("doc_count").intValue();
            if (codeToDCX01CTVSub.get(addressId) == null){
                codeToDCX01CTVSub.put(addressId, new DCX01CTVSub());
            }
            codeToDCX01CTVSub.get(addressId).setTotalHouseHold(totalHouseHold);
        }
        reportTemplate.addSubs(codeToDCX01CTVSub.values());


        return reportTemplate;
    }

    private JsonNode findNameCTVByID(String regionId) throws JsonProcessingException {
        Map<String, Object> mapAddress = new HashMap<>();
        mapAddress.put("regionId", regionId);
        mapAddress.put("isDeleted", false);
        SearchSourceBuilder searchSourceBuilder = elasticSearchService.searchAndBuilder(mapAddress);
        searchSourceBuilder.size(max_size_category);
        searchSourceBuilder.fetchSource(new String[]{
                "id",
                "fullName"
        }, null);
        String result = qldsRestTemplate.postForObject(
                url + "/collaborator/_search",
                searchSourceBuilder.toString(),
                String.class);
        assert result != null;
        return objectMapper.readTree(result).path("hits").path("hits");
    }

    /*counter sex id, region id, address id*/
    private JsonNode counterPersonAddress(ReportRequest request) throws ParseException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));


        /*aggregation*/
        AggregationBuilder aggregationSex = AggregationBuilders
                .terms("sex")
                .field("sexId")
                .order(BucketOrder.count(true));

        AggregationBuilder aggregationAddress = AggregationBuilders
                .terms("address")
                .field("addressId")
                .size(1000)
                .subAggregation(aggregationSex);

        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationAddress);
        String resultCounter = qldsRestTemplate.postForObject(
                url + "/person/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").findPath("buckets");
    }

    private JsonNode counterHouseHold(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));
        /*aggregation*/
        AggregationBuilder aggregationRegionId = AggregationBuilders
                .terms("address")
                .field("addressId")
                .size(1000);
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegionId);

        String result = qldsRestTemplate.postForObject(url + "/house-hold/_search?size=0", searchSourceBuilder.toString(), String.class);
        assert result != null;
        JsonNode jsonMale = objectMapper.readTree(result);
        return jsonMale.findPath("aggregations").findPath("buckets");
    }

    private DCX01CTVSub transferFromJsonNode(JsonNode jsonNode) {
        DCX01CTVSub dcx01CTVSub = new DCX01CTVSub();
        JsonNode buckedSubs = jsonNode.findPath("sex").findPath("buckets");
        for (JsonNode bucket : buckedSubs) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx01CTVSub.setTotalMen(docCount);
                    break;
                case Config.FEMALE:
                    dcx01CTVSub.setTotalWomen(docCount);
                    break;
            }
        }
        return dcx01CTVSub;
    }
}
