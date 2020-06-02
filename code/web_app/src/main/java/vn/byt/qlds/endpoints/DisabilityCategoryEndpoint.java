package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.DisabilityCategoryClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.disability.DisabilityCategory;
import vn.byt.qlds.model.disability.DisabilityRequest;
import vn.byt.qlds.model.disability.DisabilityResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/disability-category")
public class DisabilityCategoryEndpoint {
    @Autowired
    DisabilityCategoryClient disabilityCategoryClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_DISABILITY')")
    public DisabilityResponse createDisabilityCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody DisabilityRequest request) {
        long userId = userDetails.getAccount().getId();
        return disabilityCategoryClient
                .createDisabilityCategory(userId, request)
                .orElseThrow(() -> new RuntimeException("Tạo thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_DISABILITY')")
    public DisabilityResponse findDisabilityById(@PathVariable("id") int id) {
        return disabilityCategoryClient
                .getDisabilityCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bản ghi có id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_DISABILITY')")
    public DisabilityResponse updateDisabilityCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody DisabilityRequest disabilityRequest) {
        long userId = userDetails.getAccount().getId();
        DisabilityCategory disabilityCategory = disabilityCategoryClient
                .getDisabilityCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bản ghi có id = " + id))
                .disabilityCategory;
        return disabilityCategoryClient
                .updateDisabilityCategory(userId, disabilityCategory, disabilityRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_DISABILITY')")
    public boolean deleteDisabilityCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        DisabilityCategory disabilityCategory = disabilityCategoryClient
                .getDisabilityCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy bản ghi có id = " + id))
                .disabilityCategory;
        return disabilityCategoryClient.deleteDisabilityCategory(userId, disabilityCategory);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_DISABILITY')")
    public PageResponse<DisabilityResponse> getPageDisabilityCategory(@RequestBody(required = false) Map<String, Object> query) {
        return disabilityCategoryClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_DISABILITY')")
    public List<DisabilityResponse> getAllDisabilityCategory(@RequestBody(required = false) Map<String, Object> query) {
        return disabilityCategoryClient.getAllDisabilityCategory(query != null ? query : new HashMap<>());
    }
}
