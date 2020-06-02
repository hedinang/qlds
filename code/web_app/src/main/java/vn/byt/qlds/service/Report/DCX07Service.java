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
import vn.byt.qlds.model.report.DCX.DCX07.DCX07;
import vn.byt.qlds.model.report.DCX.DCX07.DCX07Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*THỐNG KÊ SỐ NGƯỜI CHẾT THEO NHÓM TUỔI VÀ GIỚI TÍNH*/
@Component
public class DCX07Service extends ReportService {

    private final int AGE_START = 0;
    private final int AGE_END = 80;
    private final int RANG_AGE = 5;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX07();
        super.generateReport(request);
        for (int age = 0; age <= AGE_END; age += RANG_AGE) {
            Integer ageTo = age == AGE_END ? null: age+ 5;
            reportTemplate.addSub(getPersonDieByAge(request, age, ageTo));
        }
        return reportTemplate;
    }

    private DCX07Sub getPersonDieByAge(ReportRequest request, int ageFrom, Integer ageTo) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long lteBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - ageFrom), format_date);

        if (ageTo != null) {
            Long gtBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - ageTo), format_date);
            queryBuilder.must(QueryBuilders.rangeQuery("personRequest.dateOfBirth").gt(gtBirthday));

        }
        queryBuilder.must(QueryBuilders.matchQuery("changeTypeCode", Config.DIE));
        queryBuilder.must(QueryBuilders.rangeQuery("personRequest.dateOfBirth").lte(lteBirthday));
        queryBuilder.must(QueryBuilders.matchQuery("regionId", request.regionId));
        queryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        queryBuilder.must(QueryBuilders.rangeQuery("dateUpdate").gte(StringUtils.convertDateToLong(request.startTime, format_date)));
        queryBuilder.must(QueryBuilders.rangeQuery("dateUpdate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));

        AggregationBuilder aggregationSex = AggregationBuilders
                .terms("sex")
                .field("personRequest.sexId")
                .order(BucketOrder.count(true));

        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.aggregation(aggregationSex);

        String result = qldsRestTemplate.postForObject(
                url + "/person-history/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert result != null;
        JsonNode jsonPerson = objectMapper.readTree(result);
        JsonNode buckets = jsonPerson.findPath("aggregations").findPath("buckets");

        DCX07Sub dcx07Sub = new DCX07Sub();
        dcx07Sub.setFromAge(ageFrom);
        if (ageTo == null) {
            dcx07Sub.setDisplayName(String.format(Config.GTE_AGE, AGE_END));
        } else {
            dcx07Sub.setDisplayName(String.format(Config.FROM_AGE_TO, ageFrom, ageTo - 1));
            dcx07Sub.setToAge(ageTo - 1);
        }
        for (JsonNode bucket : buckets) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx07Sub.setMale(docCount);
                    break;
                case Config.FEMALE:
                    dcx07Sub.setFemale(docCount);
                    break;
            }
        }
        return dcx07Sub;
    }

}
