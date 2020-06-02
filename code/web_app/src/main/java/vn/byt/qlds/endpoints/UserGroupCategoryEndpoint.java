package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.UserGroupCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.group.UserGroupCategory;
import vn.byt.qlds.model.group.UserGroupCategoryRequest;
import vn.byt.qlds.model.group.UserGroupCategoryResponse;
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
@RequestMapping("/user-group-category")
public class UserGroupCategoryEndpoint {
    @Autowired
    UserGroupCategoryClient userGroupCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_GROUP')")
    public UserGroupCategoryResponse createUserGroupCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @RequestBody UserGroupCategoryRequest userGroupCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        return userGroupCategoryClient
                .createUserGroupCategory(userId, userGroupCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Tạo nhóm người dùng thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_GROUP')")
    public UserGroupCategoryResponse findUserGroupCategoryById(@PathVariable("id") Integer id) {
        return userGroupCategoryClient
                .getUserGroupCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhóm người dùng id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_GROUP')")
    public UserGroupCategoryResponse updateUserGroupCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody UserGroupCategoryRequest userGroupCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        UserGroupCategory userGroupCategory = userGroupCategoryClient
                .getUserGroupCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhóm người dùng id = " + id))
                .userGroupCategory;
        return userGroupCategoryClient
                .updateUserGroupCategory(userId, userGroupCategory, userGroupCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật nhóm người dùng thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_GROUP')")
    public boolean deleteUserGroupCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        UserGroupCategory userGroupCategory = userGroupCategoryClient
                .getUserGroupCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy nhóm người dùng id = " + id))
                .userGroupCategory;
        return userGroupCategoryClient.deleteUserGroupCategory(userId, userGroupCategory);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_GROUP')")
    public PageResponse<UserGroupCategoryResponse> getPageUserGroupCategory(@RequestBody(required = false) Map<String, Object> query) {
        return userGroupCategoryClient.getPage(query != null ? query : new HashMap<>());

    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_GROUP')")
    public List<UserGroupCategoryResponse> getAllUserGroupCategory(@RequestBody(required = false) Map<String, Object> query) {
        return userGroupCategoryClient.getAllUserGroupCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_GROUP')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<UserGroupCategory> userGroupCategories = userGroupCategoryClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportUserGroup(userGroupCategories);
    }
}
