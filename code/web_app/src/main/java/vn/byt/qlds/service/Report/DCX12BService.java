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
import java.util.HashMap;
import java.util.Map;

/*  DÂN SỐ TỪ 5 TUỔI TRỞ LÊN, DÂN SỐ CHƯA BIẾT ĐỌC,
    BIẾT VIẾT CHIA THEO CHIA THEO CTV */
@Component
public class DCX12BService extends ReportService {

    @Override
    public ReportTemplate generateReport(ReportRequest request) throws JsonProcessingException, NullPointerException, ParseException {
        this.reportTemplate = new DCX12();
        super.generateReport(request);
        JsonNode hits = getListAddress(request.regionId);
        JsonNode hitsCollaborator = findNameCTVByID(request.regionId);

        Map<Integer, Integer> idToCollaboratorId = new HashMap<>();
        Map<Integer, String> idToCollaboratorName = new HashMap<>();

        for (JsonNode element : hits) {
            Integer collaboratorId = element.findPath("collaboratorId").asInt();
            Integer addressId = element.findPath("id").asInt();
            idToCollaboratorId.put(addressId, collaboratorId);
        }

        for (JsonNode element : hitsCollaborator) {
            Integer collaboratorId = element.findPath("id").asInt();
            String fullName = element.findPath("fullName").asText();
            idToCollaboratorName.put(collaboratorId, fullName);
        }


        JsonNode buckets = counterPersonBySexId(request);
        for (JsonNode bucket : buckets) {
            Integer addressId = bucket.findPath("key").intValue();
            String collaboratorName = idToCollaboratorName.get(idToCollaboratorId.get(addressId));
            DCX12Sub dcx12Sub = transferFromJsonNode(bucket);
            dcx12Sub.setDisplayName(collaboratorName);
            reportTemplate.addSub(dcx12Sub);
        }
        return reportTemplate;
    }


    private JsonNode counterPersonBySexId(ReportRequest request) throws ParseException, JsonProcessingException {
        // moc 5 tuoi dc tinh =  ngay sinh + 5 < endTime => endTime-5 > ngay sinh
        LocalDate localDate = LocalDate.parse(request.endTime, DateTimeFormatter.ofPattern(format_date));
        Long ageMark = StringUtils.convertDateToLong(localDate.getDayOfMonth() + "/" + localDate.getMonthValue() + "/" + (localDate.getYear() - 5L), format_date);
        //endTime
        long endTime = StringUtils.convertDateToLong(request.endTime, format_date);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        boolQueryBuilder.must(QueryBuilders.matchQuery("regionId", request.regionId));
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        boolQueryBuilder.must(QueryBuilders.rangeQuery("dateOfBirth").lte(ageMark));
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
        AggregationBuilder aggregationAddress = AggregationBuilders
                .terms("address")
                .size(1000)
                .field("addressId")
                .subAggregation(aggregationSex)
                .subAggregation(aggregationEdu);
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.aggregation(aggregationAddress);
        String result = qldsRestTemplate.postForObject(
                url + "/person/_search?size=0",
                searchSourceBuilder.toString(),
                String.class);
        assert result != null;
        JsonNode jsonFemaleMarried = objectMapper.readTree(result);
        return jsonFemaleMarried.findPath("aggregations").findPath("buckets");
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

    private JsonNode findNameCTVByID(String regionId) throws JsonProcessingException {
        Map<String, Object> mapAddress = new HashMap<>();
        mapAddress.put("regionId", regionId);
        mapAddress.put("isDeleted", false);
        SearchSourceBuilder searchSourceBuilder = elasticSearchService.searchAndBuilder(mapAddress);
        searchSourceBuilder.size(max_size_category);
        searchSourceBuilder.fetchSource(new String[]{
                "id",
                "fullName"
        }, null);
        String result = qldsRestTemplate.postForObject(
                url + "/collaborator/_search",
                searchSourceBuilder.toString(),
                String.class);
        assert result != null;
        return objectMapper.readTree(result).path("hits").path("hits");
    }
}
