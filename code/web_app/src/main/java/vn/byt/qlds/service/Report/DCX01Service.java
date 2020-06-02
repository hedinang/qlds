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
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCX.DCX01.DCX01;
import vn.byt.qlds.model.report.DCX.DCX01.DCX01Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DCX01Service extends ReportService {

    @Override
    public ReportTemplate<DCX01Sub> generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX01();
        super.generateReport(request);

        JsonNode hits = getListAddress(request.regionId);
        Map<Integer, DCX01Sub> codeToDCX01Sub = new HashMap<>();
        Map<Integer, String> idToAddressName = new HashMap<>();
        for (JsonNode element : hits) {
            String addressName = element.findPath("name").textValue();
            Integer addressId = element.findPath("id").intValue();
            idToAddressName.put(addressId, addressName);
        }

        JsonNode buckets = counterPersonAddress(request);
        for (JsonNode bucket : buckets) {
            int totalPerson = bucket.findPath("doc_count").intValue();
            Integer addressId = bucket.findPath("key").intValue();
            String addressName = idToAddressName.get(addressId);
            DCX01Sub dch01Sub = transferFromJsonNode(bucket);
            dch01Sub.setAddress(addressName);
            dch01Sub.setTotalPerson(totalPerson);
            codeToDCX01Sub.put(addressId, dch01Sub);
        }

        JsonNode bucketsHouseHold = counterHouseHold(request);
        for (JsonNode bucket : bucketsHouseHold) {
            Integer addressId = bucket.findPath("key").intValue();
            int totalHouseHold = bucket.findPath("doc_count").intValue();
            if (codeToDCX01Sub.get(addressId) ==null){
                codeToDCX01Sub.put(addressId, new DCX01Sub());
            }
            codeToDCX01Sub.get(addressId).setTotalHouseHold(totalHouseHold);
        }
        reportTemplate.addSubs(codeToDCX01Sub.values());

        return reportTemplate;
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

    private DCX01Sub transferFromJsonNode(JsonNode jsonNode) {
        DCX01Sub dcx01Sub = new DCX01Sub();
        JsonNode buckedSubs = jsonNode.findPath("sex").findPath("buckets");
        for (JsonNode bucket : buckedSubs) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx01Sub.setTotalMen(docCount);
                    break;
                case Config.FEMALE:
                    dcx01Sub.setTotalWomen(docCount);
                    break;
            }
        }
        return dcx01Sub;
    }
}
