package vn.byt.qlds.service.Report;

import vn.byt.qlds.core.utils.Config;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.report.DCX.DCX20.DCX20;
import vn.byt.qlds.model.report.DCX.DCX20.DCX20Sub;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/*DÂN SỐ CHIA THEO TUỔI VÀ GIỚI TÍNH*/
@Component
public class DCX20Service extends ReportService {
    private static final int AGE_FROM = 1;
    private static final int AGE_TO = 100;
    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX20();
        super.generateReport(request);
        for (int age = AGE_FROM; age <= AGE_TO; age++) {
            int male = counterByAgeAndSexId(request, age, Config.MALE);
            int female = counterByAgeAndSexId(request, age, Config.FEMALE);
            DCX20Sub dcx20Sub = new DCX20Sub();
            dcx20Sub.setAge(age);
            dcx20Sub.setFemale(male);
            dcx20Sub.setMale(female);
            if (age == AGE_TO) {
                dcx20Sub.setDisplayName("Trên "+ age + " tuổi");

            } else {
                dcx20Sub.setDisplayName(age + " tuổi");
            }
            reportTemplate.addSub(dcx20Sub);
        }
        return reportTemplate;
    }

    private int counterByAgeAndSexId(ReportRequest request, int age, int sexId) throws ParseException, JsonProcessingException {
        /*tuổi từ x -> y => x < endtime - birthday < y*/
        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long ageLte = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - age), format_date);
        Long ageGte = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - age - 1L), format_date);
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        boolQuery.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolQuery.must(QueryBuilders.matchQuery("sexId", sexId));
        boolQuery.must(QueryBuilders.rangeQuery("startDate").lte(StringUtils.convertDateToLong(request.endTime, format_date)));

        if (age == AGE_TO) {
            boolQuery.must(QueryBuilders.rangeQuery("dateOfBirth").lt(ageLte));
        } else {
            boolQuery.must(QueryBuilders.rangeQuery("dateOfBirth").lt(ageLte));
            boolQuery.must(QueryBuilders.rangeQuery("dateOfBirth").gte(ageGte));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String resultCounter = qldsRestTemplate.postForObject(url + "/person/_count", searchSourceBuilder.query(boolQuery).toString(), String.class);
        assert resultCounter != null;
        JsonNode jsonCounter = objectMapper.readTree(resultCounter);
        return jsonCounter.findPath("count").asInt();
    }

}
