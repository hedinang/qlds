package vn.byt.qlds.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import vn.byt.qlds.client.UnitCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.unit.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(
        value = "/unit-category"
)
public class UnitCategoryEndpoint {
    @Autowired
    UnitCategoryClient unitCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_UNIT')")
    public UnitCategoryResponse createUnitCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody UnitCategoryRequest unitCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        return unitCategoryClient
                .createUnitCategory(userId, unitCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Tạo đơn vị thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT')")
    public UnitCategoryResponse findOneUnitCategory(@PathVariable("id") Integer id) {
        return unitCategoryClient
                .getUnitCategory(id)
                .orElseThrow(() -> new NotFoundException("Not found unit category id = " + id));
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_UNIT')")
    public UnitCategoryResponse updateUnitCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @RequestBody UnitCategoryRequest request) {
        long userId = userDetails.getAccount().getId();
        UnitCategory unitCategory = unitCategoryClient
                .getUnitCategory(id)
                .orElseThrow(() -> new NotFoundException("Not found unit category id = " + id))
                .unitCategory;
        return unitCategoryClient
                .updateUnitCategory(userId, unitCategory, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật đơn vị thất bại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_UNIT')")
    public boolean deleteUnitCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        UnitCategory unitCategory = unitCategoryClient
                .getUnitCategory(id)
                .orElseThrow(() -> new NotFoundException("Not found unit category id = " + id))
                .unitCategory;
        return unitCategoryClient
                .deleteUnitCategory(userId, unitCategory);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT')")
    public List<UnitCategoryResponse> getAllUnitCategory(@RequestBody(required = false) Map<String, Object> query) {
        return unitCategoryClient.getAllUnitCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT')")
    public PageResponse<UnitCategoryResponse> getPageUnitCategory(@RequestBody(required = false) Map<String, Object> query) {
        return unitCategoryClient.getPage(query != null ? query : new HashMap<>());
    }

    @GetMapping("/parent")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT')")
    public List<UnitCategoryTreeResponse> getParentOfUnitCategory() {
        return unitCategoryClient.getParentOfUnitCategory();
    }

    @GetMapping("/parent-all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT')")
    public List<UnitTree> getParentAll() {
        return unitCategoryClient.getParentAll();
    }

    @GetMapping("province-district")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_UNIT')")
    public List<UnitResponse> getTreeUnit() {
        return unitCategoryClient.getProvinceAndDistrict();
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_UNIT')")
    public ExportResponse exportExcel(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<UnitCategory> unitCategories = unitCategoryClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportUnit(unitCategories);
    }
}
