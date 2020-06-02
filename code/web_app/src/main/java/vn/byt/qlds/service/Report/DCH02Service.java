package vn.byt.qlds.service.Report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.IncludeExclude;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCH.DCH02.DCH02;
import vn.byt.qlds.model.report.DCH.DCH02.DCH02Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

@Component
public class DCH02Service extends ReportService {
    @Override
    public ReportTemplate<DCH02Sub> generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCH02();
        super.generateReport(request);
        JsonNode hitsCommune = getCommune(request);
        Map<String, String> codeToAddressName = new HashMap<>();
        for (JsonNode element : hitsCommune) {
            codeToAddressName.put(element.findPath("code").textValue(),
                    element.findPath("name").textValue());
        }

        JsonNode buckets = counterChangeTypeCode(request);

        for (JsonNode bucket : buckets) {
            int totalEnd = bucket.findPath("doc_count").intValue();
            String regionCode = bucket.findPath("key").textValue();
            String regionName = codeToAddressName.get(regionCode);
            DCH02Sub dch02Sub = transferFromJsonNode(bucket);
            dch02Sub.setAddress(regionName);
            dch02Sub.setTotalEnd(totalEnd);
            reportTemplate.addSub(dch02Sub);
        }

        return reportTemplate;
    }

    private JsonNode counterChangeTypeCode(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("personRequest.districtId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQuery.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, format_date)));
        boolQuery.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));

        /*aggregation*/
        AggregationBuilder aggregationTypeCode = AggregationBuilders
                .terms("ChangeTypeCode")
                .field("changeTypeCode.keyword")
                .includeExclude(new IncludeExclude(new String[]{
                        String.valueOf(Config.TRE_MOI_SINH),
                        String.valueOf(Config.DIE),
                        String.valueOf(Config.CHUYEN_DI),
                        String.valueOf(Config.CHUYEN_DEN),
                }, null))
                .order(BucketOrder.count(true));

        AggregationBuilder aggregationRegionId = AggregationBuilders
                .terms("regionId")
                .size(1000)
                .field("personRequest.regionId.keyword")
                .subAggregation(aggregationTypeCode);

        searchSourceBuilder.query(boolQuery);
        searchSourceBuilder.aggregation(aggregationRegionId);

        String resultCount = qldsRestTemplate.postForObject(
                url + "/person-history/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert resultCount != null;
        JsonNode jsonChangeTypeCode = objectMapper.readTree(resultCount);
        return jsonChangeTypeCode.findPath("aggregations").findPath("buckets");
    }

    private DCH02Sub transferFromJsonNode(JsonNode jsonNode) {
        DCH02Sub dch02Sub = new DCH02Sub();
        JsonNode buckedSubs = jsonNode.findPath("ChangeTypeCode").findPath("buckets");
        for (JsonNode bucket : buckedSubs) {
            int keyTypeCode = Integer.parseInt(bucket.findPath("key").textValue());
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.TRE_MOI_SINH:
                    dch02Sub.setTotalBirth(docCount);
                    break;
                case Config.DIE:
                    dch02Sub.setTotalDie(docCount);
                    break;
                case Config.CHUYEN_DI:
                    dch02Sub.setTotalGo(docCount);
                    break;
                case Config.CHUYEN_DEN:
                    dch02Sub.setTotalCome(docCount);
                    break;
            }
        }
        return dch02Sub;
    }


}
