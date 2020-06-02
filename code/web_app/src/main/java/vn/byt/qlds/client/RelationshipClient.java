package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.relationship.Relationship;
import vn.byt.qlds.model.relationship.RelationshipRequest;
import vn.byt.qlds.model.relationship.RelationshipResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RelationshipClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public RelationshipClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/relationship";
    }

    public Optional<RelationshipResponse> createRelationship(long userId, RelationshipRequest request) {
        long currentTime = System.currentTimeMillis();
        Relationship relationship = new Relationship(request);
        relationship.setIsDeleted(false);
        relationship.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        relationship.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        relationship.setUserCreated(userId);
        relationship.setUserLastUpdated(userId);
        Relationship result = restTemplate.postForObject(this.apiEndpointCommon, relationship, Relationship.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<RelationshipResponse> getRelationship(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        Relationship result = restTemplate.getForObject(url, Relationship.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<RelationshipResponse> updateRelationship(long userId, Relationship relationship, RelationshipRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + relationship.getId();
        relationship.createFromRequest(request);
        relationship.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        relationship.setUserLastUpdated(userId);
        Relationship result = restTemplate.putForObject(url, relationship, Relationship.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteRelationship(long userId, Relationship relationship) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + relationship.getId();
        relationship.setIsDeleted(true);
        relationship.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        relationship.setUserLastUpdated(userId);
        restTemplate.putForObject(url, relationship, Relationship.class);
        return true;
    }

    public List<RelationshipResponse> getAllRelationship(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<Relationship> relationships = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<Relationship>>() {
        });
        return relationships
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<Relationship> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.RelationshipList.class);
    }


    public PageResponse<RelationshipResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<Relationship> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<Relationship> relationships = mapper.convertValue(response.getList(), new TypeReference<List<Relationship>>() {
        });
        List<RelationshipResponse> relationshipResponses = relationships
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<RelationshipResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(relationshipResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
