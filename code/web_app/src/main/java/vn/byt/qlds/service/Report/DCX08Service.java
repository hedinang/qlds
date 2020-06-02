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
import vn.byt.qlds.model.report.DCX.DCX08.DCX08;
import vn.byt.qlds.model.report.DCX.DCX08.DCX08Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*THỐNG KÊ SỐ NGƯỜI KẾT HÔN THEO NHÓM TUỔI VÀ GIỚI TÍNH*/
@Component
public class DCX08Service extends ReportService {
    private final static int AGE_START = 15;
    private final static int AGE_END = 80;
    private final static int RANG_AGE = 5;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX08();
        super.generateReport(request);
        for (int age = AGE_START; age <= AGE_END; age += RANG_AGE) {
            Integer ageTo = age == AGE_END ? null: age + 5;
            reportTemplate.addSub(getPersonKetHonByAge(request, age, ageTo));
        }
        return reportTemplate;
    }

    private DCX08Sub getPersonKetHonByAge(ReportRequest request, int ageFrom, Integer ageTo) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long lteBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - ageFrom), format_date);

        if (ageTo != null) {
            Long gtBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - ageTo), format_date);
            queryBuilder.must(QueryBuilders.rangeQuery("personRequest.dateOfBirth").gt(gtBirthday));

        }
        queryBuilder.must(QueryBuilders.matchQuery("changeTypeCode", Config.KET_HON));
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

        DCX08Sub dcx08Sub = new DCX08Sub();
        dcx08Sub.setFromAge(ageFrom);
        if (ageTo == null) {
            dcx08Sub.setDisplayName(String.format(Config.GTE_AGE, AGE_END));
        } else {
            dcx08Sub.setDisplayName(String.format(Config.FROM_AGE_TO, ageFrom, ageTo - 1));
            dcx08Sub.setToAge(ageTo - 1);
        }
        for (JsonNode bucket : buckets) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.MALE:
                    dcx08Sub.setMale(docCount);
                    break;
                case Config.FEMALE:
                    dcx08Sub.setFemale(docCount);
                    break;
            }
        }
        return dcx08Sub;
    }

}
