package vn.byt.qlds.client;

import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.person.PersonHistory;
import vn.byt.qlds.model._province.person.PersonHistoryRequest;
import vn.byt.qlds.model._province.person.PersonHistoryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PersonHistoryClient {
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    private String apiEndpointProvince;
    @Autowired
    TransferToResponseService toResponseService;
    public PersonHistoryClient(@Value("${apiEndpointProvince}") String apiEndpointProvince) {
        this.apiEndpointProvince = apiEndpointProvince + "/person-history";
    }

    public PersonHistory createPersonHistory(String db, long userId, PersonHistoryRequest request) {
        long currentTime = System.currentTimeMillis();
        PersonHistory personHistory = new PersonHistory(request);
        personHistory.setIsDeleted(false);
        personHistory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        personHistory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        personHistory.setUserCreated(userId);
        personHistory.setUserLastUpdated(userId);
        return tenantRestTemplate.postForObject(db, this.apiEndpointProvince, personHistory, PersonHistory.class);
    }

    public PersonHistory getPersonHistory(String db, Integer id) {
        String url = this.apiEndpointProvince + "/" + id;
        return tenantRestTemplate.getForObject(db, url, PersonHistory.class);
    }

    public boolean deletePersonHistory(String db, long userId, PersonHistory personHistory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + personHistory.getChangeId();
        personHistory.setIsDeleted(true);
        personHistory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        personHistory.setUserLastUpdated(userId);
        tenantRestTemplate.putForObject(db, url, personHistory, PersonHistory.class);
        return true;
    }

    public PersonHistory updatePersonHistory(String db, long userId, PersonHistory personHistory, PersonHistoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + personHistory.getChangeId();
        personHistory.createFromRequest(request);
        personHistory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        personHistory.setUserLastUpdated(userId);
        return tenantRestTemplate.putForObject(db, url, request, PersonHistory.class);
    }

    public List<PersonHistory> getAllPersonHistory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointProvince + "/all";
        return mapper.convertValue(
                tenantRestTemplate.postForObject(url, query, List.class),
                new TypeReference<List<PersonHistory>>() {
                });
    }

    public PageResponse<PersonHistoryResponse> getPage(Map<String, Object> query) {
        String url = this.apiEndpointProvince + "/" + "search-page";
        PageResponse<PersonHistory> personHistoryPages = tenantRestTemplate.postForObject(url, query, ConvertList.PagePersonHistoryList.class);
        List<PersonHistoryResponse> personHistoryList = personHistoryPages
                .getList()
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<PersonHistoryResponse> results = new PageResponse<>();
        results.setPage(personHistoryPages.getPage());
        results.setTotal(personHistoryPages.getTotal());
        results.setLimit(personHistoryPages.getLimit());
        results.setList(personHistoryList);
        return results;

    }

}

