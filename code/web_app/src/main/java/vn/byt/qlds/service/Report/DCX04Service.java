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
import vn.byt.qlds.model.report.DCX.DCX04.DCX04;
import vn.byt.qlds.model.report.DCX.DCX04.DCX04Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/*DÂN SỐ NỮ TỪ 13 TUỔI TRỞ LÊN CHIA THEO
  TÌNH TRẠNG HÔN NHÂN VÀ ĐỊA BÀN DÂN CƯ*/
@Component
public class DCX04Service extends ReportService {
    private final int AGE_START = 13;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX04();
        super.generateReport(request);
        JsonNode hits = getListAddress(request.regionId);
        Map<Integer, String> idToAddressName = new HashMap<>();
        for (JsonNode element : hits) {
            idToAddressName.put(element.findPath("id").asInt(),
                    element.findPath("name").textValue());
        }
        JsonNode buckets = counterMaleMaritalStatus(request);

        for (JsonNode bucket : buckets) {
            int totalEnd = bucket.findPath("doc_count").intValue();
            Integer addressId = bucket.findPath("key").asInt();
            String addressName = idToAddressName.get(addressId);
            DCX04Sub dcx04Sub = transferFromJsonNode(bucket);
            dcx04Sub.setDisplayName(addressName);
            reportTemplate.addSub(dcx04Sub);
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
        boolFemaleMarried.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolFemaleMarried.must(QueryBuilders.matchQuery("sexId", Config.FEMALE));
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
                .terms("address")
                .size(1000)
                .field("addressId")
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

    private DCX04Sub transferFromJsonNode(JsonNode jsonNode) {
        DCX04Sub dcx04Sub = new DCX04Sub();
        JsonNode buckedSubs = jsonNode.findPath("marital").findPath("buckets");
        for (JsonNode bucket : buckedSubs) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MARRIED:
                    dcx04Sub.setMarried(docCount);
                    break;
                case Config.SINGLE:
                    dcx04Sub.setSingle(docCount);
                    break;
                case Config.DIVORCE:
                    dcx04Sub.setSeparateDivorce(docCount);
                    break;
                case Config.WIDOW:
                    dcx04Sub.setWidow(docCount);
                    break;
            }
        }
        return dcx04Sub;
    }


}
