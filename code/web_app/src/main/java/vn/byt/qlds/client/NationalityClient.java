package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.nationlity.Nationality;
import vn.byt.qlds.model.nationlity.NationalityRequest;
import vn.byt.qlds.model.nationlity.NationalityResponse;
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
public class NationalityClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public NationalityClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/nationality";
    }

    public Optional<NationalityResponse> getNationality(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        Nationality result = restTemplate.getForObject(url, Nationality.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<NationalityResponse> createNationality(long useId, NationalityRequest request) {
        long currentTime = System.currentTimeMillis();
        Nationality nationality = new Nationality(request);
        nationality.setIsDeleted(false);
        nationality.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        nationality.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        nationality.setUserCreated(useId);
        nationality.setUserLastUpdated(useId);
        Nationality result = restTemplate.postForObject(this.apiEndpointCommon, nationality, Nationality.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteNationality(long userId, Nationality nationality) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + nationality.getNationalityCode();
        nationality.setIsDeleted(true);
        nationality.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        nationality.setUserLastUpdated(userId);
        restTemplate.putForObject(url, nationality, Nationality.class);
        return true;
    }

    public Optional<NationalityResponse> updateNationality(long userId, Nationality nationality, NationalityRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + nationality.getNationalityCode();
        nationality.createFromRequest(request);
        nationality.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        nationality.setUserLastUpdated(userId);
        Nationality result = restTemplate.putForObject(url, nationality, Nationality.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public List<NationalityResponse> getAllNationality(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<Nationality> nationalities = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<Nationality>>() {
        });
        return nationalities
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<Nationality> getAll(Map<String, Object> query) {

        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.NationalityList.class);
    }

    public PageResponse<NationalityResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<Nationality> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<Nationality> nationalities = mapper.convertValue(response.getList(), new TypeReference<List<Nationality>>() {
        });
        List<NationalityResponse> nationalityResponses = nationalities
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<NationalityResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(nationalityResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
