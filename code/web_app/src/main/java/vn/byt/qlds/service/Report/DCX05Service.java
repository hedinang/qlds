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
import vn.byt.qlds.client.NationCategoryClient;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCX.DCX05.DCX05;
import vn.byt.qlds.model.report.DCX.DCX05.DCX05Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.util.Map;

/*DÂN SỐ CHIA THEO DÂN TỘC VÀ GIỚI TÍNH*/
@Component
public class DCX05Service extends ReportService {
    @Autowired
    NationCategoryClient nationCategoryClient;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX05();
        super.generateReport(request);
        Map<Integer, String> mapEth = getAllCategory(Config.INDEX_ETH, "id", "name");
        JsonNode buckets = counterEthnicBySex(request);
        for (JsonNode bucket :
                buckets) {
            Integer key = bucket.findPath("key").intValue();
            DCX05Sub dcx05Sub = transferFromJsonNode(bucket);
            dcx05Sub.setNationCategoryName(mapEth.get(key));
            reportTemplate.addSub(dcx05Sub);
        }
        return reportTemplate;
    }


    private JsonNode counterEthnicBySex(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        ObjectMapper objectMapper = new ObjectMapper();
        BoolQueryBuilder boolSex = QueryBuilders.boolQuery();
        boolSex.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolSex.must(QueryBuilders.matchQuery("isDeleted", false));
        boolSex.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));
        /*aggregation*/
        AggregationBuilder aggregationSex = AggregationBuilders
                .terms("sex")
                .field("sexId")
                .order(BucketOrder.count(true));

        AggregationBuilder aggregationEthnic = AggregationBuilders
                .terms("ethnic")
                .size(55)
                .field("ethnicId")
                .subAggregation(aggregationSex);

        searchSourceBuilder.query(boolSex);
        searchSourceBuilder.aggregation(aggregationEthnic);
        String resultCount = qldsRestTemplate.postForObject(
                url + "/person/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert resultCount != null;
        JsonNode jsonChangeTypeCode = objectMapper.readTree(resultCount);
        return jsonChangeTypeCode.findPath("aggregations").findPath("buckets");
    }

    private DCX05Sub transferFromJsonNode(JsonNode jsonNode) {
        DCX05Sub dcx05Sub = new DCX05Sub();
        JsonNode buckedSubs = jsonNode.findPath("sex").findPath("buckets");
        for (JsonNode bucket : buckedSubs) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx05Sub.setMale(docCount);
                    break;
                case Config.FEMALE:
                    dcx05Sub.setFemale(docCount);
                    break;
            }
        }
        return dcx05Sub;
    }
}
