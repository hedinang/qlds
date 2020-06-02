package vn.byt.qlds.service.Report;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.ElasticSearchService;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCH.DCH06.DCH06;
import vn.byt.qlds.model.report.DCH.DCH06.DCH06Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*DÂN SỐ CHIA THEO NHÓM TUỔI, THEO HUYEN VÀ HỌC VẤN */
@Component
public class DCH06Service extends ReportService {
    @Autowired
    ElasticSearchService elasticSearchService;
    private final int AGE_END = 80;
    private final int RANG_AGE = 5;

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCH06();
        super.generateReport(request);
        for (int age = 0; age <= AGE_END; age += RANG_AGE) {
            Integer ageTo = age == AGE_END ? null: age+ 5;
            reportTemplate.addSub(getEdu(request, age, ageTo));
        }
        return reportTemplate;
    }

    private DCH06Sub getEdu(ReportRequest request, int ageFrom, Integer ageTo) throws ParseException, JsonProcessingException {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolFeMale = QueryBuilders.boolQuery();
        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long lteBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - ageFrom), format_date);

        if (ageTo != null) {
            Long gtBirthday = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - ageTo), format_date);
            boolFeMale.must(QueryBuilders.rangeQuery("dateOfBirth").gt(gtBirthday));

        }
        boolFeMale.must(QueryBuilders.rangeQuery("dateOfBirth").lte(lteBirthday));
        boolFeMale.must(QueryBuilders.matchQuery("districtId", request.regionId));
        boolFeMale.must(QueryBuilders.matchQuery("isDeleted", false));
        boolFeMale.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));

        AggregationBuilder aggregationSex = AggregationBuilders
                .terms("education")
                .field("ethnicId")
                .order(BucketOrder.count(true));

        searchSourceBuilder.query(boolFeMale);
        searchSourceBuilder.aggregation(aggregationSex);

        String result = qldsRestTemplate.postForObject(
                url + "/person/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert result != null;
        JsonNode jsonPerson = objectMapper.readTree(result);
        JsonNode buckets = jsonPerson.findPath("aggregations").findPath("buckets");

        DCH06Sub dch06Sub = new DCH06Sub();
        dch06Sub.setFromAge(ageFrom);
        if (ageTo == null) {
            dch06Sub.setDisplayName(String.format(Config.GTE_AGE, AGE_END));
        } else {
            dch06Sub.setDisplayName(String.format(Config.FROM_AGE_TO, ageFrom, ageTo - 1));
            dch06Sub.setToAge(ageTo - 1);
        }

        for (JsonNode bucket : buckets) {
            int keyTypeCode = bucket.findPath("key").intValue();
            int docCount = bucket.findPath("doc_count").intValue();
            switch (keyTypeCode) {
                case Config.NOT_WRITE_READ:
                    dch06Sub.setNotWriteRead(docCount);
                    break;
                case Config.PRIMARY_SCHOOL:
                    dch06Sub.setPrimarySchool(docCount);
                    break;
                case Config.SECONDARY_SCHOOL:
                    dch06Sub.setSecondarySchool(docCount);
                    break;
                case Config.HIGH_SCHOOL:
                    dch06Sub.setHighSchool(docCount);
                    break;
                case Config.INTERMADICATE:
                    dch06Sub.setIntermediate(docCount);
                    break;
                case Config.COLLEGE:
                    dch06Sub.setCollege(docCount);
                    break;
                case Config.UNIVERSITY:
                    dch06Sub.setUniversity(docCount);
                    break;
                case Config.GT_UNIVERSITY:
                    dch06Sub.setGtUniversity(docCount);
                    break;
            }
        }
        return dch06Sub;
    }
}
