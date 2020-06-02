package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageRequest;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.region.RegionChange;
import vn.byt.qlds.model.region.RegionChangeRequest;
import vn.byt.qlds.model.region.RegionChangeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RegionChangeClient {
    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    private String apiEndpointCommon;

    public RegionChangeClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/region-change";
    }

    public Optional<RegionChangeResponse> createRegionChange(long userId, RegionChangeRequest request) {
        long currentTime = System.currentTimeMillis();
        RegionChange regionChange = new RegionChange();
        regionChange.createFromRequest(request);
        regionChange.setIsDeleted(false);
        regionChange.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        regionChange.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        regionChange.setUserCreated(userId);
        regionChange.setUserLastUpdated(userId);
        RegionChange result = restTemplate.postForObject(this.apiEndpointCommon, regionChange, RegionChange.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<RegionChangeResponse> getRegionChange(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        RegionChange result = restTemplate.getForObject(url, RegionChange.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<RegionChangeResponse> updateRegionChange(long userId, RegionChange regionChange, RegionChangeRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + regionChange.getId();
        regionChange.createFromRequest(request);
        regionChange.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        regionChange.setUserLastUpdated(userId);
        RegionChange result = restTemplate.putForObject(url, regionChange, RegionChange.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public boolean deleteRegionChange(long userId, RegionChange regionChange) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + regionChange.getId();
        regionChange.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        regionChange.setUserLastUpdated(userId);
        regionChange.setIsDeleted(true);
        restTemplate.putForObject(url, regionChange, RegionChange.class);
        return true;
    }

    public List<RegionChangeResponse> getAllRegionChange() {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<RegionChange> list = mapper.convertValue(restTemplate.getForObject(url, List.class), new TypeReference<List<RegionChange>>() {
        });
        return list
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public PageResponse<RegionChangeResponse> getPage(PageRequest pageRequest) {
        String url = this.apiEndpointCommon + "/" + "search-page";
        PageResponse<RegionChange> regionChangePages = restTemplate.postForObject(url, pageRequest, PageResponse.class);
        PageResponse<RegionChangeResponse> result = new PageResponse<>();
        result.setPage(regionChangePages.getPage());
        ObjectMapper mapper = new ObjectMapper();
        List<RegionChange> regionChangeList = mapper.convertValue(regionChangePages.getList(), new TypeReference<List<RegionChange>>() {
        });

        List<RegionChangeResponse> list = regionChangeList.stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        result.setList(list);
        return result;

    }
}
