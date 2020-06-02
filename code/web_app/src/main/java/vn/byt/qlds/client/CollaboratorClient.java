package vn.byt.qlds.client;

import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.collaborator.Collaborator;
import vn.byt.qlds.model.collaborator.CollaboratorRequest;
import vn.byt.qlds.model.collaborator.CollaboratorResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class CollaboratorClient {
    @Autowired
    TenantRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public CollaboratorClient(@Value("${apiEndpointProvince}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/collaborator";
    }

    public Optional<CollaboratorResponse> createCollaborator(String db, long userId, CollaboratorRequest request) throws ParseException {
        long currentTime = System.currentTimeMillis();
        Collaborator collaborator = new Collaborator(request);
        collaborator.setIsDeleted(false);
        collaborator.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        collaborator.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        collaborator.setUserCreated(userId);
        collaborator.setUserLastUpdated(userId);
        Collaborator result = restTemplate.postForObject(db, this.apiEndpointCommon, collaborator, Collaborator.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<CollaboratorResponse> getCollaborator(String db, Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        Collaborator result = restTemplate.getForObject(db, url, Collaborator.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteCollaborator(String db, long userId, Collaborator collaborator) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + collaborator.getId();
        collaborator.setIsDeleted(true);
        collaborator.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        collaborator.setUserLastUpdated(userId);
        restTemplate.putForObject(db, url, collaborator, Collaborator.class);
        return true;
    }

    public Optional<CollaboratorResponse> updateCollaborator(String db, long userId, Collaborator collaborator, CollaboratorRequest collaboratorRequest) throws ParseException {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + collaborator.getId();
        collaborator.createFromRequest(collaboratorRequest);
        collaborator.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        collaborator.setUserLastUpdated(userId);
        Collaborator resultUpdate = restTemplate.putForObject(db, url, collaborator, Collaborator.class);
        return Optional.ofNullable(toResponseService.transfer(resultUpdate));
    }

    public List<CollaboratorResponse> getAll(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<Collaborator> collaborators = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<Collaborator>>() {
        });
        return collaborators
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public PageResponse<CollaboratorResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/" + "search-page";
        PageResponse<Collaborator> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<Collaborator> collaborators = mapper.convertValue(response.getList(), new TypeReference<List<Collaborator>>() {
        });

        List<CollaboratorResponse> collaboratorResponses = collaborators
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        PageResponse<CollaboratorResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(collaboratorResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }

}
