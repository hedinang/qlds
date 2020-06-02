package vn.byt.qlds.sync.core.ES;

import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ElasticSearchService {
    public SearchSourceBuilder searchAndBuilder(Map<String, Object> mConditions) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        for (String key : mConditions.keySet()) {
            boolQueryBuilder.must(QueryBuilders.matchQuery(key, mConditions.get(key)));
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
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

