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
import vn.byt.qlds.model.report.DCX.DCX18.DCX18;
import vn.byt.qlds.model.report.DCX.DCX18.DCX18Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/*THỐNG KÊ SỐ NGƯỜI KẾT HÔN THEO TUỔI VÀ GIỚI TÍNH*/
@Component
public class DCX18Service extends ReportService {

    private static final int AGE_FROM = 18;
    private static final int AGE_TO = 80;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX18();
        super.generateReport(request);
        JsonNode buckets = counterKetHonByAge(request);
        Map<Integer, DCX18Sub> ageToDCX18Sub = new HashMap<>();
        for (JsonNode bucket : buckets) {
            int key = bucket.path("key").asInt();
            DCX18Sub dcx18Sub = transferFromJsonNode(bucket);
            if (key < AGE_FROM) continue;
            if (key >= AGE_TO){
                DCX18Sub dcx18Sub1 = ageToDCX18Sub.get(AGE_TO);
                if (dcx18Sub1 == null){
                    dcx18Sub1 = new DCX18Sub();
                    dcx18Sub1.setAge(AGE_TO);
                    dcx18Sub1.setDisplayName(AGE_TO +" tuổi trở lên ");
                    ageToDCX18Sub.put(AGE_TO, dcx18Sub1);
                }
                DCX18Sub dcx18Sub2 = ageToDCX18Sub.get(AGE_TO);
                dcx18Sub2.setMale(dcx18Sub2.getMale() + dcx18Sub.getMale());
                dcx18Sub2.setFemale(dcx18Sub2.getFemale() + dcx18Sub.getFemale());
            }else{
                dcx18Sub.setDisplayName(key +" tuổi");
                dcx18Sub.setAge(key);
                ageToDCX18Sub.put(key, dcx18Sub);
            }
        }

        reportTemplate.addSubs(ageToDCX18Sub.values());
        return reportTemplate;
    }
    private JsonNode counterKetHonByAge(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolMale = QueryBuilders.boolQuery();
        boolMale.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolMale.must(QueryBuilders.matchQuery("isDeleted", false));
        boolMale.must(QueryBuilders.matchQuery("changeTypeCode", Config.KET_HON));
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

    private DCX18Sub transferFromJsonNode(JsonNode jsonNode) {
        DCX18Sub dcx18Sub = new DCX18Sub();
        JsonNode buckedSex = jsonNode.path("sex").findPath("buckets");
        for (JsonNode bucket : buckedSex) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx18Sub.setMale(docCount);
                    break;
                case Config.FEMALE:
                    dcx18Sub.setFemale(docCount);
                    break;
            }
        }
        return dcx18Sub;
    }

}
