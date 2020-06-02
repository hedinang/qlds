package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.NationCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.ethnic.NationCategory;
import vn.byt.qlds.model.ethnic.NationCategoryRequest;
import vn.byt.qlds.model.ethnic.NationCategoryResponse;
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
@RequestMapping("/nation-category")
public class NationCategoryEndpoint {
    @Autowired
    NationCategoryClient nationCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_NATION')")
    public NationCategoryResponse createNationCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody NationCategoryRequest nationCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        return nationCategoryClient
                .createNationCategory(userId, nationCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Tạo danh mục dân tộc thất bại vui lòng thử lại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_NATION')")
    public NationCategoryResponse findNationById(@PathVariable("id") Integer id) {
        return nationCategoryClient
                .getNationCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục dân tộc có id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_NATION')")
    public NationCategoryResponse updateNationCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody NationCategoryRequest nationCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        NationCategory nationCategory = nationCategoryClient
                .getNationCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục dân tộc có id = " + id))
                .nationCategory;
        return nationCategoryClient
                .updateNationCategory(userId, nationCategory, nationCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật danh mục dân tộc thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_MARITAL')")
    public boolean deleteNationCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") int id) {
        long userId = userDetails.getAccount().getId();
        NationCategory nationCategory = nationCategoryClient
                .getNationCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy danh mục dân tộc có id = " + id))
                .nationCategory;
        return nationCategoryClient.deleteNationCategory(userId, nationCategory);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_NATION')")
    public PageResponse<NationCategoryResponse> getPage(@RequestBody(required = false) Map<String, Object> query) {
        return nationCategoryClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_NATION')")
    public List<NationCategoryResponse> getAll(@RequestBody(required = false) Map<String, Object> query) {
        return nationCategoryClient.getAllNationCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_NATION')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<NationCategory> nationCategories = nationCategoryClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportEthnic(nationCategories);
    }
}


