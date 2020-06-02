package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.contraceptive.Contraceptive;
import vn.byt.qlds.model.contraceptive.ContraceptiveRequest;
import vn.byt.qlds.model.contraceptive.ContraceptiveResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ContraceptiveCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public ContraceptiveCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/contraceptive-category";
    }

    public Optional<ContraceptiveResponse> createContraceptive(long userId, ContraceptiveRequest request) {
        long currentTime = System.currentTimeMillis();
        Contraceptive contraceptive = new Contraceptive(request);
        contraceptive.setIsDeleted(false);
        contraceptive.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        contraceptive.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        contraceptive.setUserCreated(userId);
        contraceptive.setUserLastUpdated(userId);
        Contraceptive result = restTemplate.postForObject(this.apiEndpointCommon, contraceptive, Contraceptive.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<ContraceptiveResponse> getContraceptiveCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        Contraceptive result = restTemplate.getForObject(url, Contraceptive.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Contraceptive findByCode(String code) {
        if (code == null) return null;
        Map<String, Object> request = new HashMap<>();
        request.put("code", code);
        List<Contraceptive> contraceptives = getAll(request);
        if (contraceptives != null && !contraceptives.isEmpty()) return contraceptives.get(0);
        else return null;
    }

    public Optional<ContraceptiveResponse> updateContraceptiveCategory(long userId, Contraceptive contraceptive, ContraceptiveRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + contraceptive.getId();
        contraceptive.createFromRequest(request);
        contraceptive.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        contraceptive.setUserLastUpdated(userId);
        Contraceptive result = restTemplate.putForObject(url, contraceptive, Contraceptive.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteContraceptiveCategory(long userId, Contraceptive contraceptive) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + contraceptive.getId();
        contraceptive.setIsDeleted(true);
        contraceptive.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        contraceptive.setUserLastUpdated(userId);
        restTemplate.putForObject(url, contraceptive, Contraceptive.class);
        return true;
    }

    public List<ContraceptiveResponse> getAllContraceptiveCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<Contraceptive> contraceptives = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<Contraceptive>>() {
        });
        return contraceptives
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<Contraceptive> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.ContraceptiveList.class);
    }

    public PageResponse<ContraceptiveResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<Contraceptive> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<Contraceptive> contraceptives = mapper.convertValue(response.getList(), new TypeReference<List<Contraceptive>>() {
        });
        List<ContraceptiveResponse> contraceptiveResponses = contraceptives
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<ContraceptiveResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(contraceptiveResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
