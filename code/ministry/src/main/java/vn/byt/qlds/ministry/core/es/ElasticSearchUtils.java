package vn.byt.qlds.ministry.core.es;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ElasticSearchUtils {
    public ElasticSearchUtils() {

    }

    public SearchSourceBuilder searchAndBuilder(Map<String, Object> mConditions) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : mConditions.keySet()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(key, mConditions.get(key)));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        searchSourceBuilder.size(1000);
        searchSourceBuilder.from(0);
//        searchSourceBuilder.
        return searchSourceBuilder;

    }

    public SearchSourceBuilder searchOrBuilder(Map<String, Object> mConditions) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : mConditions.keySet()) {
            boolQueryBuilder.should(QueryBuilders.matchQuery(key, mConditions.get(key)));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        return searchSourceBuilder;

    }
}

