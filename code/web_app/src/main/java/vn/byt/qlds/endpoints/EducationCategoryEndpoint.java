package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.EducationCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.education.EducationCategory;
import vn.byt.qlds.model.education.EducationCategoryRequest;
import vn.byt.qlds.model.education.EducationCategoryResponse;
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
@RequestMapping("/education-category")
public class EducationCategoryEndpoint {
    @Autowired
    EducationCategoryClient educationCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_EDUCATION')")
    public EducationCategoryResponse createLevelCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody EducationCategoryRequest educationCategoryRequest) {
        long userId = userDetails.getAccount().getId();
        return educationCategoryClient
                .createLevelCategory(userId, educationCategoryRequest)
                .orElseThrow(() -> new RuntimeException("Tạo trình độ học vấn thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_EDUCATION')")
    public EducationCategoryResponse findLevelById(@PathVariable("id") Integer id) {
        return educationCategoryClient
                .getLevelCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trình độ học vấn id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_EDUCATION')")
    public EducationCategoryResponse updateLevelCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody EducationCategoryRequest request) {
        long userId = userDetails.getAccount().getId();
        EducationCategory educationCategory = educationCategoryClient
                .getLevelCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trình độ học vấn id = " + id))
                .educationCategory;
        return educationCategoryClient
                .updateLevelCategory(userId, educationCategory, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật trình độ học vấn thất bại! Try again ~|~"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_EDUCATION')")
    public boolean deleteLevelCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        EducationCategory educationCategory = educationCategoryClient
                .getLevelCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trình độ học vấn id = " + id))
                .educationCategory;
        return educationCategoryClient.deleteLevelCategory(userId, educationCategory);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_EDUCATION')")
    public PageResponse<EducationCategoryResponse> getPage(@RequestBody(required = false) Map<String, Object> query) {
        return educationCategoryClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_EDUCATION')")
    public List<EducationCategoryResponse> getAll(@RequestBody(required = false) Map<String, Object> query) {
        return educationCategoryClient.getAllLevelCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_EDUCATION')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<EducationCategory> educationCategories = educationCategoryClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportEducation(educationCategories);
    }
}
