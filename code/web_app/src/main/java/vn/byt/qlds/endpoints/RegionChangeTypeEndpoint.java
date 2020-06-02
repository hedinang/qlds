package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.RegionChangeTypeClient;
import vn.byt.qlds.core.base.PageRequest;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.region.RegionChangeType;
import vn.byt.qlds.model.region.RegionChangeTypeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/region-change-type")
public class RegionChangeTypeEndpoint {
    @Autowired
    RegionChangeTypeClient regionChangeTypeClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_REGION_CHANGE_TYPE')")
    public RegionChangeTypeResponse createRegionChangeType(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @RequestBody RegionChangeType regionChangeTypeRequest) {
        long userId = userDetails.getAccount().getId();
        return regionChangeTypeClient.createRegionChangeType(userId, regionChangeTypeRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REGION_CHANGE_TYPE')")
    public RegionChangeTypeResponse findRegionChangeTypeById(@PathVariable("id") String id) {
        return regionChangeTypeClient.getRegionChangeType(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_REGION_CHANGE_TYPE')")
    public RegionChangeTypeResponse updateRegionChangeType(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") String id,
            @RequestBody RegionChangeType regionChangeTypeRequest) {
        long userId = userDetails.getAccount().getId();
        return regionChangeTypeClient.updateRegionChangeType(userId, id, regionChangeTypeRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_REGION_CHANGE_TYPE')")
    public boolean deleteRegionChangeType(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") String id) {
        long userId = userDetails.getAccount().getId();
        return regionChangeTypeClient.deleteRegionChangeType(userId, id);
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REGION_CHANGE_TYPE')")
    public List<RegionChangeTypeResponse> getAllRegionChangeType() {
        return regionChangeTypeClient.getAllRegionChangeType();
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REGION_CHANGE_TYPE')")
    public PageResponse<RegionChangeTypeResponse> getPageRegionChangeType(@RequestBody PageRequest pageRequest) {
        return regionChangeTypeClient.getPage(pageRequest);

    }

}
