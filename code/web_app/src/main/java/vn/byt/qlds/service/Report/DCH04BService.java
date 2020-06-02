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
import org.springframework.stereotype.Service;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCH.DCH04.DCH04;
import vn.byt.qlds.model.report.DCH.DCH04.DCH04Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/*DÂN SỐ NAM TỪ 13 TUỔI TRỞ LÊN CHIA THEO
  TÌNH TRẠNG HÔN NHÂN VÀ ĐỊA BÀN DÂN CƯ*/
@Service("DCH04BService")
public class DCH04BService extends ReportService {
    private final static int AGE_START = 13;
    @Override
    public ReportTemplate<DCH04Sub> generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCH04();
        super.generateReport(request);
        JsonNode hits = getCommune(request);
        Map<String, String> codeToCommuneName = new HashMap<>();
        for (JsonNode element : hits) {
            codeToCommuneName.put(element.findPath("code").textValue(),
                    element.findPath("name").textValue());
        }
        JsonNode buckets = counterMaleMaritalStatus(request);

        for (JsonNode bucket : buckets) {
            int totalEnd = bucket.findPath("doc_count").intValue();
            String regionCode = bucket.findPath("key").textValue();
            String regionName = codeToCommuneName.get(regionCode);
            DCH04Sub dch04Sub = transferFromJsonNode(bucket);
            dch04Sub.setDisplayName(regionName);
            reportTemplate.addSub(dch04Sub);
        }
        return reportTemplate;
    }

    protected JsonNode counterMaleMaritalStatus(ReportRequest request) throws ParseException, JsonProcessingException {
        // moc 13 tuoi dc tinh =  ngay sinh + 13 < endTime => endTime-13 > ngay sinh
        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long ageMark = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - AGE_START), format_date);
        //endTime
        long endTime = StringUtils.convertDateToLong(request.endTime, format_date);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolFemaleMarried = QueryBuilders.boolQuery();
        boolFemaleMarried.must(QueryBuilders.matchQuery("isDeleted", false));
        boolFemaleMarried.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolFemaleMarried.must(QueryBuilders.matchQuery("sexId", getSexId()));
        boolFemaleMarried.must(QueryBuilders.rangeQuery("dateOfBirth").lte(ageMark));
        boolFemaleMarried.must(QueryBuilders.rangeQuery("startDate").lte(endTime));
        /*aggregation*/
        AggregationBuilder aggregationMarital = AggregationBuilders
                .terms("marital")
                .field("maritalId")
                .includeExclude(new IncludeExclude(new String[]{
                        String.valueOf(Config.MARRIED),
                        String.valueOf(Config.SINGLE),
                        String.valueOf(Config.DIVORCE),
                        String.valueOf(Config.WIDOW),
                }, null))
                .order(BucketOrder.count(true));

        AggregationBuilder aggregationRegionId = AggregationBuilders
                .terms("regionId")
                .size(1000)
                .field("regionId.keyword")
                .subAggregation(aggregationMarital);

        searchSourceBuilder.query(boolFemaleMarried);
        searchSourceBuilder.aggregation(aggregationRegionId);

        String result = qldsRestTemplate.postForObject(
                url + "/person/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert result != null;
        JsonNode jsonFemaleMarried = objectMapper.readTree(result);
        return jsonFemaleMarried.findPath("aggregations").findPath("buckets");
    }

    private DCH04Sub transferFromJsonNode(JsonNode jsonNode) {
        DCH04Sub dch02Sub = new DCH04Sub();
        JsonNode buckedSubs = jsonNode.findPath("marital").findPath("buckets");
        for (JsonNode bucket : buckedSubs) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MARRIED:
                    dch02Sub.setMarried(docCount);
                    break;
                case Config.SINGLE:
                    dch02Sub.setSingle(docCount);
                    break;
                case Config.DIVORCE:
                    dch02Sub.setSeparateDivorce(docCount);
                    break;
                case Config.WIDOW:
                    dch02Sub.setWidow(docCount);
                    break;
            }
        }
        return dch02Sub;
    }



    protected int getSexId() {
        return Config.MALE;
    }
}
