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
import vn.byt.qlds.model.report.DCX.DCX19.DCX19;
import vn.byt.qlds.model.report.DCX.DCX19.DCX19Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/*THỐNG KÊ SỐ NGƯỜI LY HÔN THEO TUỔI VÀ GIỚI TÍNH*/
@Component
public class DCX19Service extends ReportService {
    private static final int AGE_FROM = 18;
    private static final int AGE_TO = 80;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX19();
        super.generateReport(request);
        JsonNode buckets = counterLyHonByAge(request);
        Map<Integer, DCX19Sub> ageToDCX19Sub = new HashMap<>();
        for (JsonNode bucket : buckets) {
            int key = bucket.path("key").asInt();
            DCX19Sub dcx19Sub = transferFromJsonNode(bucket);
            if (key < AGE_FROM) continue;
            if (key >= AGE_TO){
                DCX19Sub dcx19Sub1 = ageToDCX19Sub.get(AGE_TO);
                if (dcx19Sub1 == null){
                    dcx19Sub1 = new DCX19Sub();
                    dcx19Sub1.setAge(AGE_TO);
                    dcx19Sub1.setDisplayName(AGE_TO +" tuổi trở lên ");
                    ageToDCX19Sub.put(AGE_TO, dcx19Sub1);
                }
                DCX19Sub dcx19Sub2 = ageToDCX19Sub.get(AGE_TO);
                dcx19Sub2.setMale(dcx19Sub2.getMale() + dcx19Sub.getMale());
                dcx19Sub2.setFemale(dcx19Sub2.getFemale() + dcx19Sub.getFemale());
            }else{
                dcx19Sub.setDisplayName(key +" tuổi");
                dcx19Sub.setAge(key);
                ageToDCX19Sub.put(key, dcx19Sub);
            }
        }

        reportTemplate.addSubs(ageToDCX19Sub.values());

        return reportTemplate;
    }


    private JsonNode counterLyHonByAge(ReportRequest request) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolMale = QueryBuilders.boolQuery();
        boolMale.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolMale.must(QueryBuilders.matchQuery("isDeleted", false));
        boolMale.must(QueryBuilders.matchQuery("changeTypeCode", Config.KET_HON));
        boolMale.must(QueryBuilders.matchQuery("notes", Config.NOTE_DA_LY_HON));
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

    private DCX19Sub transferFromJsonNode(JsonNode jsonNode) {
        DCX19Sub dcx19Sub = new DCX19Sub();
        JsonNode buckedSex = jsonNode.path("sex").findPath("buckets");
        for (JsonNode bucket : buckedSex) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx19Sub.setMale(docCount);
                    break;
                case Config.FEMALE:
                    dcx19Sub.setFemale(docCount);
                    break;
            }
        }
        return dcx19Sub;
    }

}
