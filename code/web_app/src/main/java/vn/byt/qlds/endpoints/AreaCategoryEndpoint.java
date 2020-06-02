package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.AreaCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.area.AreaCategory;
import vn.byt.qlds.model.area.AreaCategoryRequest;
import vn.byt.qlds.model.area.AreaCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/area-category")
public class AreaCategoryEndpoint {
    @Autowired
    AreaCategoryClient areaCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    //id = 123456
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_AREA')")
    public AreaCategoryResponse createAreaCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody AreaCategoryRequest request) {
        long userId = userDetails.getAccount().getId();
        return areaCategoryClient
                .createAreaCategory(userId, request)
                .orElseThrow(()->new RuntimeException("Tạo thất bại! Vui lòng thử lại."));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_AREA')")
    public AreaCategoryResponse findOneAddress(@PathVariable("id") Integer id) {
        return areaCategoryClient
                .getAreaCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy vùng có id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_AREA')")
    public AreaCategoryResponse updateAreaCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody AreaCategoryRequest request) {
        long userId = userDetails.getAccount().getId();
        return areaCategoryClient.updateAreaCategory(userId, id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_AREA')")
    public boolean deleteAreaCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        return areaCategoryClient.deleteAreaCategory(userId, id);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_AREA')")
    public List<AreaCategoryResponse> getAllAreaCategory(@RequestBody(required = false) Map<String, Object> query) {
        return areaCategoryClient.getAllAreaCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_AREA')")
    public PageResponse<AreaCategoryResponse> getPage(@RequestBody(required = false) Map<String, Object> query) {
        return areaCategoryClient.getPageAreaCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_AREA')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<AreaCategory> areaCategories = areaCategoryClient.findAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportArea(areaCategories);
    }

}
