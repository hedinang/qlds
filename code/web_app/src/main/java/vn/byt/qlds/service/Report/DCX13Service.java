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
import vn.byt.qlds.model.report.DCX.DCX12.DCX12;
import vn.byt.qlds.model.report.DCX.DCX12.DCX12Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*  DÂN SỐ TỪ 5 TUỔI TRỞ LÊN, DÂN SỐ CHƯA BIẾT ĐỌC,
    BIẾT VIẾT CHIA THEO NHOM TUOI VA GIOI TINH */
@Component
public class DCX13Service extends ReportService {

    private static final int AGE_END = 80;
    private static final int AGE_START = 5;
    private static final int RANG_AGE = 5;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX12();
        super.generateReport(request);
        for (int age = AGE_START; age <= AGE_END; age += RANG_AGE) {
            Integer ageTo = age == AGE_END ? null: age+ 5;
            DCX12Sub dcx12Sub = counterPersonByAge(request, age, ageTo);
            reportTemplate.addSub(dcx12Sub);
        }

        return reportTemplate;
    }

    private DCX12Sub counterPersonByAge(ReportRequest request, int ageFrom, Integer ageTo) throws ParseException, JsonProcessingException {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long lteBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - ageFrom), format_date);

        if (ageTo != null) {
            Long gtBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - ageTo), format_date);
            boolQueryBuilder.must(QueryBuilders.rangeQuery("dateOfBirth").gt(gtBirthday));
        }
        boolQueryBuilder.must(QueryBuilders.rangeQuery("dateOfBirth").lte(lteBirthday));
        //endTime
        long endTime = StringUtils.convertDateToLong(request.endTime, format_date);
        boolQueryBuilder.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("startDate").lte(endTime));

        /*aggregation*/
        AggregationBuilder aggregationSex = AggregationBuilders
                .terms("sex")
                .field("sexId")
                .order(BucketOrder.count(true));
        AggregationBuilder aggregationEdu = AggregationBuilders
                .terms("education")
                .field("educationId")
                .includeExclude(new IncludeExclude(new String[]{
                        String.valueOf(Config.NOT_WRITE_READ),
                }, null))
                .subAggregation(aggregationSex);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(aggregationSex);
        searchSourceBuilder.aggregation(aggregationEdu);
        String result = qldsRestTemplate.postForObject(
                url + "/person/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert result != null;
        JsonNode jsonFemaleMarried = objectMapper.readTree(result);
        JsonNode jsonNode = jsonFemaleMarried.findPath("aggregations");
        DCX12Sub dcx12Sub = transferFromJsonNode(jsonNode);
        if (ageTo == null) {
            dcx12Sub.setDisplayName(String.format(Config.GTE_AGE, AGE_END));
        } else {
            dcx12Sub.setDisplayName(String.format(Config.FROM_AGE_TO, ageFrom, ageTo - 1));
        }
        return dcx12Sub;
    }

    private DCX12Sub transferFromJsonNode(JsonNode jsonNode) {
        DCX12Sub dcx12Sub = new DCX12Sub();
        JsonNode buckedSex = jsonNode.path("sex").findPath("buckets");
        for (JsonNode bucket : buckedSex) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx12Sub.setMale(docCount);
                    break;
                case Config.FEMALE:
                    dcx12Sub.setFemale(docCount);
                    break;
            }
        }

        JsonNode buckedEduSubs = jsonNode.path("education").findPath("buckets");
        for (JsonNode bucketEdu : buckedEduSubs) {
            int keyTypeCode = bucketEdu.findPath("key").intValue();
            if (keyTypeCode == Config.NOT_WRITE_READ)
            {
                JsonNode buckedSexEduSubs = bucketEdu.findPath("sex").findPath("buckets");
                for (JsonNode bucketSub : buckedSexEduSubs) {
                    int keySex = bucketSub.findPath("key").intValue();
                    int docCount = bucketSub.findPath("doc_count").intValue();
                    switch (keySex) {
                        case Config.MALE:
                            dcx12Sub.setNrwMale(docCount);
                            break;
                        case Config.FEMALE:
                            dcx12Sub.setNrwFemale(docCount);
                            break;
                    }
                }
            }
        }
        return dcx12Sub;
    }

}
