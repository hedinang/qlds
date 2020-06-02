package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.gender.Gender;
import vn.byt.qlds.model.gender.GenderRequest;
import vn.byt.qlds.model.gender.GenderResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class GenderClient {
    @Autowired
    QldsRestTemplate restTemplate;
    private String apiEndpointCommon;
    @Autowired
    TransferToResponseService toResponseService;
    public GenderClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/gender";
    }

    public GenderResponse createGender(Long userId, GenderRequest genderRequest) {
        long currentTime = System.currentTimeMillis();
        Gender gender = new Gender(genderRequest);
        gender.setIsDeleted(false);
        gender.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        gender.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        gender.setUserCreated(userId);
        gender.setUserLastUpdated(userId);
        Gender result = restTemplate.postForObject(this.apiEndpointCommon, gender, Gender.class);
        assert result != null;
        return toResponseService.transfer(result);
    }

    public Optional<GenderResponse> getGender(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        Gender result = restTemplate.getForObject(url, Gender.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<GenderResponse> updateGender(long userId, int id, GenderRequest genderRequest) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + id;
        Gender gender = restTemplate.getForObject(url, Gender.class);
        if (gender != null) {
            gender.createFromRequest(genderRequest);
            gender.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            gender.setUserLastUpdated(userId);
            Gender result = restTemplate.putForObject(url, gender, Gender.class);
            return Optional.ofNullable(toResponseService.transfer(result));
        }
        return Optional.empty();
    }

    public boolean deleteGender(long userId, int id) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + id;
        Gender gender = restTemplate.getForObject(url, Gender.class);
        if (gender != null) {
            gender.setUserLastUpdated(userId);
            gender.setIsDeleted(true);
            gender.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            restTemplate.putForObject(url, gender, Gender.class);
            return true;
        }
        return false;
    }

    public Integer count(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/count";
        return restTemplate.postForObject(url, query, Integer.class);
    }

    public PageResponse<GenderResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse p = restTemplate.postForObject(url, query, PageResponse.class);
        List<Gender> list = mapper.convertValue(p.getList(), new TypeReference<List>() {
        });
        List<GenderResponse> listGender = new ArrayList<>();
        PageResponse<GenderResponse> result = new PageResponse();
        for (Gender gender : list) {
            GenderResponse genderResponse = new GenderResponse();
            genderResponse.setGender(gender);
            listGender.add(genderResponse);
        }
        result.setList(listGender);
        result.setPage(p.getPage());
        result.setTotal(p.getTotal());
        return result;
    }

    public List<GenderResponse> getAll(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<Gender> list = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<Gender>>() {
        });
        List<GenderResponse> results = new ArrayList<>();
        for (Gender gender : list) {
            GenderResponse genderResponse = new GenderResponse();
            genderResponse.setGender(gender);
            results.add(genderResponse);
        }
        return results;
    }
}
