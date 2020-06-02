package vn.byt.qlds.client;

import vn.byt.qlds.core.TenantRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.person.PersonHealthy;
import vn.byt.qlds.model._province.person.PersonHealthyRequest;
import vn.byt.qlds.model._province.person.PersonHealthyResponse;
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
public class PersonHealthyClient {
    @Autowired
    TenantRestTemplate tenantRestTemplate;
    private String apiEndpointProvince;
    @Autowired
    TransferToResponseService toResponseService;

    public PersonHealthyClient(@Value("${apiEndpointProvince}") String apiEndpointProvince) {
        this.apiEndpointProvince = apiEndpointProvince + "/person-healthy";
    }

    public Optional<PersonHealthyResponse> createPersonHealthy(String db, long userId, PersonHealthyRequest personHealthyRequest) {
        long currentTime = System.currentTimeMillis();
        PersonHealthy personHealthy = new PersonHealthy(personHealthyRequest);
        personHealthy.setIsDeleted(false);
        personHealthy.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        personHealthy.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        personHealthy.setUserCreated(userId);
        personHealthy.setUserLastUpdated(userId);
        PersonHealthy result = tenantRestTemplate.postForObject(db, this.apiEndpointProvince, personHealthy, PersonHealthy.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<PersonHealthyResponse> getPersonHealthy(String db, Integer id) {
        String url = this.apiEndpointProvince + "/" + id;
        PersonHealthy result = tenantRestTemplate.getForObject(db, url, PersonHealthy.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<PersonHealthyResponse> updatePersonHealthy(String db, long userId, PersonHealthy personHealthy, PersonHealthyRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + personHealthy.getGenerateId();
        personHealthy.createFromRequest(request);
        personHealthy.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        personHealthy.setUserLastUpdated(userId);
        PersonHealthy result = tenantRestTemplate.putForObject(db, url, personHealthy, PersonHealthy.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deletePersonHealthy(String db, long userId, PersonHealthy personHealthy) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointProvince + "/" + personHealthy.getGenerateId();
        personHealthy.setIsDeleted(true);
        personHealthy.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        personHealthy.setUserLastUpdated(userId);
        PersonHealthy unit = tenantRestTemplate.putForObject(db, url, personHealthy, PersonHealthy.class);
        return unit != null;
    }

    public List<PersonHealthyResponse> getAllPersonHealthy(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointProvince + "/all";
        List<PersonHealthy> personHealthies = mapper.convertValue(tenantRestTemplate.postForObject(url, query, List.class), new TypeReference<List<PersonHealthy>>() {
        });
        return personHealthies
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public PageResponse<PersonHealthyResponse> getPage(Map<String, Object> query) {
        String url = this.apiEndpointProvince + "/" + "search-page";
        PageResponse<PersonHealthy> personHealthyPages = tenantRestTemplate.postForObject(url, query, PageResponse.class);
        PageResponse<PersonHealthyResponse> results = new PageResponse<>();
        results.setPage(personHealthyPages.getPage());
        ObjectMapper mapper = new ObjectMapper();
        List<PersonHealthy> personHealthies = mapper.convertValue(personHealthyPages.getList(), new TypeReference<List<PersonHealthy>>() {
        });
        List<PersonHealthyResponse> list = personHealthies
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        results.setList(list);
        results.setTotal(personHealthyPages.getTotal());
        results.setLimit(personHealthyPages.getLimit());
        return results;
    }
}
