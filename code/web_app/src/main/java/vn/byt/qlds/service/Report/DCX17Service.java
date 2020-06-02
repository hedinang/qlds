package vn.byt.qlds.service.Report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCX.DCX17.DCX17;
import vn.byt.qlds.model.report.DCX.DCX17.DCX17Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/*THỐNG KÊ SỐ NGƯỜI CHẾT THEO TUỔI VÀ GIỚI TÍNH*/
@Component
public class DCX17Service extends ReportService {
    private static final int AGE_FROM = 1;
    private static final int AGE_TO = 100;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX17();
        super.generateReport(request);
        JsonNode buckets = counterDieByAge(request);
        Map<Integer, DCX17Sub> ageToDCX17Sub = new HashMap<>();
        for (JsonNode bucket : buckets) {
            int key = bucket.path("key").asInt();
            DCX17Sub dcx17Sub = transferFromJsonNode(bucket);
            if (key >= AGE_TO){
                DCX17Sub dcx17Sub1 = ageToDCX17Sub.get(AGE_TO);
                if (dcx17Sub1 == null){
                    dcx17Sub1 = new DCX17Sub();
                    dcx17Sub1.setAge(AGE_TO);
                    dcx17Sub1.setDisplayName(AGE_TO +" tuổi trở lên ");
                    ageToDCX17Sub.put(AGE_TO, dcx17Sub1);
                }
                DCX17Sub dcx17Sub2 = ageToDCX17Sub.get(AGE_TO);
                dcx17Sub2.setMale(dcx17Sub2.getMale() + dcx17Sub.getMale());
                dcx17Sub2.setFemale(dcx17Sub2.getFemale() + dcx17Sub.getFemale());
            }else{
                dcx17Sub.setDisplayName(key +" tuổi");
                dcx17Sub.setAge(key);
                ageToDCX17Sub.put(key, dcx17Sub);
            }
        }
        reportTemplate.addSubs(ageToDCX17Sub.values());
        return reportTemplate;
    }

    private JsonNode counterDieByAge(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolMale = QueryBuilders.boolQuery();
        boolMale.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolMale.must(QueryBuilders.matchQuery("isDeleted", false));
        boolMale.must(QueryBuilders.matchQuery("changeTypeCode", Config.DIE));
        boolMale.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, format_date)));
        boolMale.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));
        AggregationBuilder aggregationSex = AggregationBuilders
                .terms("sex")
                .field("personRequest.sexId");
        AggregationBuilder aggregationAge = AggregationBuilders
                .terms("ageUpdate")
                .field("updateInAge")
                .size(1000)
                .order(BucketOrder.count(true))
                .subAggregation(aggregationSex);
        searchSourceBuilder.query(boolMale);
        searchSourceBuilder.aggregation(aggregationAge);

        String resultCounter = qldsRestTemplate.postForObject(url + "/person-history/_search?size=0", searchSourceBuilder.toString(), String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("aggregations").path("ageUpdate").path("buckets");
    }

    private DCX17Sub transferFromJsonNode(JsonNode jsonNode) {
        DCX17Sub dcx17Sub = new DCX17Sub();
        JsonNode buckedSex = jsonNode.path("sex").findPath("buckets");
        for (JsonNode bucket : buckedSex) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx17Sub.setMale(docCount);
                    break;
                case Config.FEMALE:
                    dcx17Sub.setFemale(docCount);
                    break;
            }
        }
        return dcx17Sub;
    }


}
