package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.UnitLevelCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.unit.UnitLevelCategory;
import vn.byt.qlds.model.unit.UnitLevelCategoryRequest;
import vn.byt.qlds.model.unit.UnitLevelCategoryResponse;
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
@RequestMapping("/unit-level-category")
public class UnitLevelCategoryEndpoint {
    @Autowired
    UnitLevelCategoryClient unitLevelCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_UNIT_LEVEL')")
    public UnitLevelCategoryResponse createUnitLevelCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @RequestBody UnitLevelCategoryRequest request) {
        long userId = userDetails.getAccount().getId();
        return unitLevelCategoryClient
                .createUnitLevelCategory(userId, request)
                .orElseThrow(() -> new RuntimeException("Tạo cấp hành chính thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT_LEVEL')")
    public UnitLevelCategoryResponse findUnitLevelCategoryById(@PathVariable("id") Integer id) {
        return unitLevelCategoryClient
                .getUnitLevelCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cấp hành chính id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_UNIT_LEVEL')")
    public UnitLevelCategoryResponse updateUnitLevelCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody UnitLevelCategoryRequest request) {
        long userId = userDetails.getAccount().getId();
        UnitLevelCategory unitLevelCategory = unitLevelCategoryClient
                .getUnitLevelCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cấp hành chính id = " + id))
                .unitLevelCategory;
        return unitLevelCategoryClient
                .updateUnitLevelCategory(userId, unitLevelCategory, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật cấp hành chính thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_UNIT_LEVEL')")
    public boolean deleteUnitLevelCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        UnitLevelCategory unitLevelCategory = unitLevelCategoryClient
                .getUnitLevelCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy cấp hành chính id = " + id))
                .unitLevelCategory;
        return unitLevelCategoryClient.deleteUnitLevelCategory(userId, unitLevelCategory);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT_LEVEL')")
    public PageResponse<UnitLevelCategoryResponse> getPageUnitLevelCategory(@RequestBody(required = false) Map<String, Object> query) {
        return unitLevelCategoryClient.getPage(query != null ? query : new HashMap<>());

    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT_LEVEL')")
    public List<UnitLevelCategoryResponse> getAllUnitLevelCategory(@RequestBody(required = false) Map<String, Object> query) {
        return unitLevelCategoryClient.getAllUnitLevelCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_UNIT_LEVEL')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<UnitLevelCategory> unitLevelCategories = unitLevelCategoryClient.findAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportUnitLevel(unitLevelCategories);
    }
}
