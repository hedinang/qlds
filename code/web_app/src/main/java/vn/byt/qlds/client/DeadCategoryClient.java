package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.dead.DeadCategory;
import vn.byt.qlds.model.dead.DeadRequest;
import vn.byt.qlds.model.dead.DeadResponse;
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
public class DeadCategoryClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public DeadCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/dead-category";
    }

    public Optional<DeadResponse> createDeadCategory(long userId, DeadRequest request) {
        long currentTime = System.currentTimeMillis();
        DeadCategory deadCategory = new DeadCategory(request);
        deadCategory.setIsDeleted(false);
        deadCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        deadCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        deadCategory.setUserCreated(userId);
        deadCategory.setUserLastUpdated(userId);
        DeadCategory result = restTemplate.postForObject(this.apiEndpointCommon, deadCategory, DeadCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<DeadResponse> getDeadCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        DeadCategory result = restTemplate.getForObject(url, DeadCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<DeadResponse> updateDeadCategory(long userId, DeadCategory deadCategory, DeadRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + deadCategory.getId();
        deadCategory.createFromRequest(request);
        deadCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        deadCategory.setUserLastUpdated(userId);
        DeadCategory result = restTemplate.putForObject(url, deadCategory, DeadCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteDeadCategory(long userId, DeadCategory deadCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + deadCategory.getId();
        deadCategory.setIsDeleted(true);
        deadCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        deadCategory.setUserLastUpdated(userId);
        restTemplate.putForObject(url, deadCategory, DeadCategory.class);
        return true;
    }

    public List<DeadResponse> getAllDeadCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<DeadCategory> contraceptives = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<DeadCategory>>() {
        });
        return contraceptives
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public List<DeadCategory> getAll(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return restTemplate.postForObject(url, query, ConvertList.DeadList.class);
    }

    public PageResponse<DeadResponse> getPage(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<DeadCategory> response = restTemplate.postForObject(url, query, PageResponse.class);
        List<DeadCategory> deadCategories = mapper.convertValue(response.getList(), new TypeReference<List<DeadCategory>>() {
        });
        List<DeadResponse> contraceptiveResponses = deadCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());

        PageResponse<DeadResponse> pageResponse = new PageResponse<>();
        pageResponse.setList(contraceptiveResponses);
        pageResponse.setTotal(response.getTotal());
        pageResponse.setPage(response.getPage());
        pageResponse.setLimit(response.getLimit());
        return pageResponse;
    }
}
