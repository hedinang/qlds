package vn.byt.qlds.core.es;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import vn.byt.qlds.configuration.ESRestTemplate;
import vn.byt.qlds.core.es.EsResponse;
import vn.byt.qlds.model.response.PageResponse;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ElasticSearchService {
    @Autowired
    ESRestTemplate esRestTemplate;
    @Value("${urlES}")
    private String urlES;
    private static final int page_default = 1;
    private static final int limit_default = 10;
    private static final String key_sort = "es_sort";
    private static final String key_sort_order = "es_sort_order";
    private static final String key_like = "es_key_like";
    private static final String value_like = "es_value_like";
    private static final String key_page = "page";
    private static final String key_limit = "limit";


    @Nullable
    public <T> T findOneById(String index, String id) {
        String url = String.format("%s/%s/_doc/%s", urlES, index, id);
        EsResponse<T> responseEntity = esRestTemplate.getForObject(url, EsResponse.class);
        assert responseEntity != null;
        return (T) responseEntity._source;
    }

    public <T> List<T> findAll(String index, Map<String, Object> request, String[] excludeProperty) throws IOException {
        ArrayList<T> items = new ArrayList<>();
        String url = String.format("%s/%s/_search/", urlES, index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = searchAndBuilder(request);
        searchSourceBuilder.fetchSource(null, excludeProperty);
        if (request != null && request.containsKey(key_sort)) {
            SortOrder sortOrder = SortOrder.ASC;
            String direction = request.containsKey(key_sort_order) ? (String) request.get(key_sort_order) : "esc";
            if (direction.equals("desc"))
                sortOrder = SortOrder.DESC;
            searchSourceBuilder.sort((String) request.get(key_sort), sortOrder);
        }
        searchSourceBuilder.query(queryBuilder);
        searchSourceBuilder.size(10000);
        ObjectMapper mapper = new ObjectMapper();
        String body = searchSourceBuilder.toString();
        JsonNode jsonNode = mapper.readTree(esRestTemplate.postForObject(url, body, String.class));
        JsonNode hits = jsonNode.path("hits").path("hits");
        for (JsonNode hit : hits) {
            JsonNode _source = hit.path("_source");
            T item = mapper.convertValue(_source, new TypeReference<T>() {
            });
            items.add(item);
        }
        return items;
    }

    public <T> List<T> findAll(String index, Map<String, Object> request) throws IOException {
        return findAll(index, request, new String[]{});
    }

    public <T> PageResponse<T> findPaging(String index, Map<String, Object> esRequest, String[] excludeProperty) throws IOException {
        int page = page_default;
        int limit = limit_default;
        if (esRequest != null) {
            page = esRequest.get("page") == null ? page_default : (int) esRequest.get("page");
            limit = esRequest.get("limit") == null ? limit_default : (int) esRequest.get("limit");
        }
        String url = String.format("%s/%s/_search/", urlES, index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        BoolQueryBuilder queryBuilder = searchAndBuilder(esRequest);
        searchSourceBuilder.query(queryBuilder);
        int realPage = Math.max(page - 1, 0);
        searchSourceBuilder.from(realPage * limit);
        searchSourceBuilder.size(limit);
        searchSourceBuilder.fetchSource(null, excludeProperty);
        if (esRequest != null && esRequest.containsKey(key_sort)) {
            SortOrder sortOrder = SortOrder.ASC;
            String direction = esRequest.containsKey(key_sort_order) ? (String) esRequest.get(key_sort_order) : "asc";
            if (direction.equals("desc"))
                sortOrder = SortOrder.DESC;
            searchSourceBuilder.sort((String) esRequest.get(key_sort), sortOrder);
        }

        ObjectMapper mapper = new ObjectMapper();
        String body = searchSourceBuilder.toString();
        JsonNode jsonNode = mapper.readTree(esRestTemplate.postForObject(url, body, String.class));
        JsonNode hits = jsonNode.path("hits").path("hits");
        ArrayList<T> items = new ArrayList<>();
        for (JsonNode hit : hits) {
            JsonNode _source = hit.path("_source");
            T item = mapper.convertValue(_source, new TypeReference<T>() {
            });
            items.add(item);
        }

        SearchSourceBuilder counterSourceBuilder = new SearchSourceBuilder();
        String urlCounter = String.format("%s/%s/_count/", urlES, index);

        String bodyCounter = counterSourceBuilder.query(countAnBuilder(esRequest)).toString();
        JsonNode jsonCounter = mapper.readTree(esRestTemplate.postForObject(urlCounter, bodyCounter, String.class));
        int total = jsonCounter.path("count").asInt();
        PageResponse<T> pageResponse = new PageResponse<>(items, page, total);
        pageResponse.limit = limit;
        return pageResponse;
    }

    public <T> PageResponse<T> findPaging(String index, Map<String, Object> esRequest) throws IOException {
        return findPaging(index, esRequest, new String[]{});
    }

    public int count(String index, Map<String, Object> esRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        SearchSourceBuilder counterSourceBuilder = new SearchSourceBuilder();
        String urlCounter = String.format("%s/%s/_count/", urlES, index);
        String bodyCounter = counterSourceBuilder.query(countAnBuilder(esRequest)).toString();
        JsonNode jsonCounter = mapper.readTree(esRestTemplate.postForObject(urlCounter, bodyCounter, String.class));
        return jsonCounter.path("count").asInt();
    }


    private BoolQueryBuilder searchAndBuilder(Object request) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (request != null) {
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> mConditions = mapper.convertValue(request, Map.class);
            for (String key : mConditions.keySet()) {
                if (!key.equals(key_page)
                        && !key.equals(key_limit)
                        && !key.equals(key_sort)
                        && !key.equals(key_sort_order)
                        && !key.equals(key_like)
                        && !key.equals(value_like))
                    boolQueryBuilder.must(QueryBuilders.matchQuery(key, mConditions.get(key)));

                if (key.equals(key_like)) {
                    if (mConditions.containsKey(value_like)) {
                        Object valueLike = mConditions.get(value_like);
                        if (valueLike instanceof String && !((String) valueLike).isEmpty()) {
                            boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery((String) mConditions.get(key_like), valueLike));
                        } else if (valueLike instanceof Number) {
                            //todo tạm thời chưa cho search like theo số
//                        boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery((String) mConditions.get(key_like), valueLike));
                        }
                    }
                }
            }
        }
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        return boolQueryBuilder;
    }

    private BoolQueryBuilder countAnBuilder(Map<String, Object> query) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (query != null) {
            for (String key : query.keySet()) {
                if (key.equals(key_page)
                        || key.equals(key_limit)
                        || key.equals(key_sort)
                        || key.equals(key_sort_order)
                        || key.equals(value_like))
                    continue;
                if (key.equals(key_like)) {
                    if (query.containsKey(value_like)) {
                        Object valueLike = query.get(value_like);
                        if (valueLike instanceof String && !((String) valueLike).isEmpty()) {
                            boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery((String) query.get(key_like), valueLike));
                        } else if (valueLike instanceof Number) {
                            //todo tạm thời chưa cho search like theo số
//                        boolQueryBuilder.must(QueryBuilders.matchPhrasePrefixQuery((String) mConditions.get(key_like), valueLike));
                        }
                    }
                } else {
                    boolQueryBuilder.must(QueryBuilders.matchQuery(key, query.get(key)));
                }
            }
        }
        boolQueryBuilder.must(QueryBuilders.matchQuery("isDeleted", false));
        return boolQueryBuilder;
    }

}
