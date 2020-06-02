package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.RegionChangeClient;
import vn.byt.qlds.core.base.PageRequest;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.region.RegionChange;
import vn.byt.qlds.model.region.RegionChangeRequest;
import vn.byt.qlds.model.region.RegionChangeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/region-change")
public class RegionChangeEndpoint {
    @Autowired
    RegionChangeClient regionChangeClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_REGION_CHANGE')")
    public RegionChangeResponse createRegionChange(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody RegionChangeRequest request) {
        long userId = userDetails.getAccount().getId();
        return regionChangeClient
                .createRegionChange(userId, request)
                .orElseThrow(() -> new RuntimeException("Tạo Region change thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REGION_CHANGE')")
    public RegionChangeResponse findRegionChangeById(@PathVariable("id") Integer id) {
        return regionChangeClient
                .getRegionChange(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy region change id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_REGION_CHANGE')")
    public RegionChangeResponse updateRegionChange(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody RegionChangeRequest request) {
        long userId = userDetails.getAccount().getId();
        RegionChange regionChange = regionChangeClient
                .getRegionChange(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy region change id = " + id))
                .regionChange;
        return regionChangeClient
                .updateRegionChange(userId, regionChange, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật region change thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_REGION_CHANGE')")
    public boolean deleteRegionChange(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        RegionChange regionChange = regionChangeClient
                .getRegionChange(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy region change id = " + id))
                .regionChange;
        return regionChangeClient.deleteRegionChange(userId, regionChange);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REGION_CHANGE')")
    public List<RegionChangeResponse> getAllRegionChange() {
        return regionChangeClient.getAllRegionChange();
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REGION_CHANGE')")
    public PageResponse<RegionChangeResponse> getPageRegionChange(@RequestBody(required = false) PageRequest pageRequest) {
        return regionChangeClient.getPage(pageRequest);
    }
}
