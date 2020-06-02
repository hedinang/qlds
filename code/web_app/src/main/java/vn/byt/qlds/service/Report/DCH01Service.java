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
import vn.byt.qlds.model.report.DCH.DCH01.DCH01;
import vn.byt.qlds.model.report.DCH.DCH01.DCH01Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DCH01Service extends ReportService {
    ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public ReportTemplate<DCH01Sub> generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCH01();
        super.generateReport(request);
        JsonNode hits = getCommune(request);
        Map<String, String> codeToAddressName = new HashMap<>();
        Map<String, DCH01Sub> codeToDCH01Sub = new HashMap<>();
        for (JsonNode element : hits) {
            codeToAddressName.put(element.findPath("code").textValue(),
                    element.findPath("name").textValue());
        }
        JsonNode buckets = counterSex(request);
        for (JsonNode bucket : buckets) {
            int totalPerson = bucket.findPath("doc_count").intValue();
            String regionCode = bucket.findPath("key").textValue();
            String regionName = codeToAddressName.get(regionCode);
            DCH01Sub dch01Sub = transferFromJsonNode(bucket);
            dch01Sub.setAddress(regionName);
            dch01Sub.setTotalPerson(totalPerson);
            codeToDCH01Sub.put(regionCode, dch01Sub);
        }

        JsonNode bucketsHouseHold = counterHouseHold(request);
        for (JsonNode bucket : bucketsHouseHold) {
            String regionCode = bucket.findPath("key").textValue();
            int totalHouseHold = bucket.findPath("doc_count").intValue();
            if (codeToDCH01Sub.get(regionCode) ==null){
                codeToDCH01Sub.put(regionCode, new DCH01Sub());
            }
            codeToDCH01Sub.get(regionCode).setTotalHouseHold(totalHouseHold);
        }

        reportTemplate.addSubs(codeToDCH01Sub.values());
        return reportTemplate;
    }

    private JsonNode counterSex(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));

        /*aggregation*/
        AggregationBuilder aggregationSex = AggregationBuilders
                .terms("sexId")
                .field("sexId")
                .order(BucketOrder.count(true));

        AggregationBuilder aggregationRegionId = AggregationBuilders
                .terms("regionId")
                .field("regionId.keyword")
                .size(1000)
                .subAggregation(aggregationSex);
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegionId);

        String result = qldsRestTemplate.postForObject(url + "/person/_search?size=0", searchSourceBuilder.toString(), String.class);
        assert result != null;
        JsonNode jsonMale = objectMapper.readTree(result);
        return jsonMale.findPath("aggregations").findPath("buckets");
    }

    private JsonNode counterHouseHold(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));
        /*aggregation*/
        AggregationBuilder aggregationRegionId = AggregationBuilders
                .terms("regionId")
                .field("regionId.keyword")
                .size(1000);
        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegionId);

        String result = qldsRestTemplate.postForObject(url + "/house-hold/_search?size=0", searchSourceBuilder.toString(), String.class);
        assert result != null;
        JsonNode jsonMale = objectMapper.readTree(result);
        return jsonMale.findPath("aggregations").findPath("buckets");
    }

    private DCH01Sub transferFromJsonNode(JsonNode jsonNode) {
        DCH01Sub dch01Sub = new DCH01Sub();
        JsonNode buckedSubs = jsonNode.findPath("sexId").findPath("buckets");
        for (JsonNode bucket : buckedSubs) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dch01Sub.setTotalMen(docCount);
                    break;
                case Config.FEMALE:
                    dch01Sub.setTotalWomen(docCount);
                    break;
            }
        }
        return dch01Sub;
    }
}
