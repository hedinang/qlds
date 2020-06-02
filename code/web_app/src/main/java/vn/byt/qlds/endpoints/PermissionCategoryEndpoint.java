package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.PermissionCategoryClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.permission.PermissionCategory;
import vn.byt.qlds.model.permission.PermissionCategoryRequest;
import vn.byt.qlds.model.permission.PermissionCategoryResponse;
import vn.byt.qlds.model.permission.PermissionTreeResponse;
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
@RequestMapping("/permission-category")
public class PermissionCategoryEndpoint {
    @Autowired
    PermissionCategoryClient permissionCategoryClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_PERMISSION')")
    public PermissionCategoryResponse createPermissionCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody PermissionCategoryRequest permissionCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        return permissionCategoryClient
                .createPermissionCategory(userId, permissionCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Tạo phân quyền thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERMISSION')")
    public PermissionCategoryResponse findPermissionById(@PathVariable("id") Integer id) {
        return permissionCategoryClient
                .getPermissionCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quyền hạn id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_PERMISSION')")
    public PermissionCategoryResponse updatePermissionCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody PermissionCategoryRequest permissionCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        PermissionCategory permissionCategory = permissionCategoryClient
                .getPermissionCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quyền hạn id = " + id))
                .permissionCategory;
        return permissionCategoryClient
                .updatePermissionCategory(userId, permissionCategory, permissionCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật quyền hạn thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_PERMISSION')")
    public boolean deletePermissionCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        PermissionCategory permissionCategory = permissionCategoryClient
                .getPermissionCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quyền hạn id = " + id))
                .permissionCategory;
        return permissionCategoryClient.deletePermissionCategory(userId, permissionCategory);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERMISSION')")
    public PageResponse<PermissionCategoryResponse> getPagePermissionCategory(@RequestBody(required = false) Map<String, Object> query) {
        return permissionCategoryClient.getPage(query != null ? query : new HashMap<>());

    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERMISSION')")
    public List<PermissionCategoryResponse> getAllPermissionCategory(@RequestBody(required = false) Map<String, Object> query) {
        return permissionCategoryClient.getAllPermissionCategory(query != null ? query : new HashMap<>());
    }

    @GetMapping("/parent")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_PERMISSION')")
    public List<PermissionTreeResponse> getParent() {
        return permissionCategoryClient.getParentAndPermissions();
    }
}
