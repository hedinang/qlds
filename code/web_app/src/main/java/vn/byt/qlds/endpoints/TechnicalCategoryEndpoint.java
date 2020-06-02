package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.TechnicalCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.technical.TechnicalCategory;
import vn.byt.qlds.model.technical.TechnicalCategoryRequest;
import vn.byt.qlds.model.technical.TechnicalCategoryResponse;
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
@RequestMapping("/technical-category")
public class TechnicalCategoryEndpoint {
    @Autowired
    TechnicalCategoryClient technicalCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_TECHNICAL')")
    public TechnicalCategoryResponse createTechnicalCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody TechnicalCategoryRequest technicalCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        return technicalCategoryClient
                .createTechnicalCategory(userId, technicalCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Tạo trình độ chuyên môn thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TECHNICAL')")
    public TechnicalCategoryResponse findTechnicalCategoryById(@PathVariable("id") Integer id) {
        return technicalCategoryClient
                .getTechnicalCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trình độ chuyên môn id = " + id));

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TECHNICAL')")
    public TechnicalCategoryResponse updateTechnicalCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody TechnicalCategoryRequest technicalCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        TechnicalCategory technicalCategory = technicalCategoryClient
                .getTechnicalCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trình độ chuyên môn id = " + id))
                .technicalCategory;
        return technicalCategoryClient
                .updateTechnicalCategory(userId, technicalCategory, technicalCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật trình độ chuyên môn thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_TECHNICAL')")
    public boolean deleteTechnicalCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        TechnicalCategory technicalCategory = technicalCategoryClient
                .getTechnicalCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trình độ chuyên môn id = " + id))
                .technicalCategory;
        return technicalCategoryClient.deleteTechnicalCategory(userId, technicalCategory);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TECHNICAL')")
    public PageResponse<TechnicalCategoryResponse> getPage(@RequestBody(required = false) Map<String, Object> query) {
        return technicalCategoryClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TECHNICAL')")
    public List<TechnicalCategoryResponse> getAll(@RequestBody(required = false) Map<String, Object> query) {
        return technicalCategoryClient.getAllTechnicalCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_TECHNICAL')")
    public ExportResponse exportExcel(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<TechnicalCategory> technicalCategories = technicalCategoryClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportTechnical(technicalCategories);
    }

}
