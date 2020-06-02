package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.DeadCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.dead.DeadCategory;
import vn.byt.qlds.model.dead.DeadRequest;
import vn.byt.qlds.model.dead.DeadResponse;
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
@RequestMapping("/dead-category")
public class DeadCategoryEndpoint {
    @Autowired
    DeadCategoryClient deadCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_DEAD')")
    public DeadResponse createDeadCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody DeadRequest deadRequest) {
        long userId = userDetails.getAccount().getId();
        return deadCategoryClient
                .createDeadCategory(userId, deadRequest)
                .orElseThrow(() -> new RuntimeException("Tạo danh mục chết thất bại"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_DEAD')")
    public DeadResponse findDeadById(@PathVariable("id") Integer id) {
        return deadCategoryClient
                .getDeadCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy Dead có id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_DEAD')")
    public DeadResponse updateDeadCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody DeadRequest deadRequest) {
        long userId = userDetails.getAccount().getId();
        DeadCategory deadCategory = deadCategoryClient
                .getDeadCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy Dead có id = " + id))
                .deadCategory;
        return deadCategoryClient
                .updateDeadCategory(userId, deadCategory, deadRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật danh mục Dead thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_DEAD')")
    public boolean deleteDeadCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") int id) {
        long userId = userDetails.getAccount().getId();
        DeadCategory deadCategory = deadCategoryClient
                .getDeadCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy Dead có id = " + id))
                .deadCategory;
        return deadCategoryClient.deleteDeadCategory(userId, deadCategory);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_DEAD')")
    public PageResponse<DeadResponse> getPageDeadCategory(@RequestBody(required = false) Map<String, Object> query) {
        return deadCategoryClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_DEAD')")
    public List<DeadResponse> getAllDeadCategory(@RequestBody(required = false) Map<String, Object> query) {
        return deadCategoryClient.getAllDeadCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_DEAD')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<DeadCategory> deadCategories = deadCategoryClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportDead(deadCategories);
    }
}
