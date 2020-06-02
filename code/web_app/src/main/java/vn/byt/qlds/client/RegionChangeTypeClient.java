package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.base.PageRequest;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.model.region.RegionChangeType;
import vn.byt.qlds.model.region.RegionChangeTypeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class RegionChangeTypeClient {
    @Autowired
    QldsRestTemplate restTemplate;
    private String apiEndpointCommon;

    public RegionChangeTypeClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/region-change-type";
    }

    public RegionChangeTypeResponse getRegionChangeType(String id) {
        String url = this.apiEndpointCommon + "/" + id;
        RegionChangeType result = restTemplate.getForObject(url, RegionChangeType.class);
        if (result != null) {
            return new RegionChangeTypeResponse(result.getChangeTypeCode(), result.getChangeTypeDesc());
        }
        return new RegionChangeTypeResponse();
    }

    public RegionChangeTypeResponse createRegionChangeType(long userId, RegionChangeType regionChangeTypeRequest) {
        regionChangeTypeRequest.setIsDeleted(false);
        regionChangeTypeRequest.setUserCreated(userId);
        regionChangeTypeRequest.setUserLastUpdated(userId);
        RegionChangeType result = restTemplate.postForObject(this.apiEndpointCommon, regionChangeTypeRequest, RegionChangeType.class);
        return new RegionChangeTypeResponse(result.getChangeTypeCode(), result.getChangeTypeDesc());
    }

    public boolean deleteRegionChangeType(long userId, String id) {
        String url = this.apiEndpointCommon + "/" + id;
        RegionChangeType regionChangeType = restTemplate.getForObject(url, RegionChangeType.class);
        if (regionChangeType != null) {
            regionChangeType.setUserLastUpdated(userId);
            regionChangeType.setIsDeleted(true);
            RegionChangeType unit = restTemplate.putForObject(url, regionChangeType, RegionChangeType.class);
            return true;
        }
        return false;
    }

    public RegionChangeTypeResponse updateRegionChangeType(long userId, String id, RegionChangeType regionChangeTypeResquest) {
        String url = this.apiEndpointCommon + "/" + id;
        RegionChangeType regionChangeType = restTemplate.getForObject(url, RegionChangeType.class);
        if (regionChangeType != null) {
            regionChangeTypeResquest.setIsDeleted(regionChangeType.getIsDeleted());
            regionChangeTypeResquest.setUserCreated(regionChangeType.getUserCreated());
            regionChangeTypeResquest.setTimeCreated(regionChangeType.getTimeCreated());
            regionChangeTypeResquest.setUserLastUpdated(userId);
            RegionChangeType result = restTemplate.putForObject(url, regionChangeTypeResquest, RegionChangeType.class);
            return new RegionChangeTypeResponse(result.getChangeTypeCode(), result.getChangeTypeDesc());
        }
        return new RegionChangeTypeResponse();
    }

    public List<RegionChangeTypeResponse> getAllRegionChangeType() {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        List<RegionChangeType> list = mapper.convertValue(restTemplate.getForObject(url, List.class), new TypeReference<List<RegionChangeType>>() {
        });
        List<RegionChangeTypeResponse> results = new ArrayList<>();
        for (RegionChangeType regionChangeType : list) {
            results.add(new RegionChangeTypeResponse(regionChangeType.getChangeTypeCode(), regionChangeType.getChangeTypeDesc()));
        }
        return results;
    }

    public PageResponse<RegionChangeTypeResponse> getPage(PageRequest pageRequest) {
        String url = this.apiEndpointCommon + "/search-page";
        PageResponse<RegionChangeType> regionChangeTypePages = restTemplate.postForObject(url, pageRequest, PageResponse.class);
        PageResponse<RegionChangeTypeResponse> result = new PageResponse<>();
        result.setPage(regionChangeTypePages.getPage());
        //result.setTotal(regionChangeTypePages.getTotal());
        ObjectMapper mapper = new ObjectMapper();
        List<RegionChangeTypeResponse> list = new ArrayList<>();
        List<RegionChangeType> regionChangeTypeList = mapper.convertValue(regionChangeTypePages.getList(), new TypeReference<List<RegionChangeType>>() {
        });
        for (RegionChangeType regionChangeType : regionChangeTypeList) {
            list.add(new RegionChangeTypeResponse(regionChangeType.getChangeTypeCode(), regionChangeType.getChangeTypeDesc()));
        }
        result.setList(list);
        return result;

    }
}
