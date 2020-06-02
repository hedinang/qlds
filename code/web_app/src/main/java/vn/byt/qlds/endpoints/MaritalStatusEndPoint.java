package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.MaritalStatusClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.marial.MaritalStatus;
import vn.byt.qlds.model.marial.MaritalStatusRequest;
import vn.byt.qlds.model.marial.MaritalStatusResponse;
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
@RequestMapping("/marital-status")
public class MaritalStatusEndPoint {
    @Autowired
    MaritalStatusClient maritalStatusClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_MARITAL')")
    public MaritalStatusResponse createMaritalStatus(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody MaritalStatusRequest maritalStatusRequest) {
        long userId = userDetails.getAccount().getId();
        return maritalStatusClient
                .createMaritalStatus(userId, maritalStatusRequest)
                .orElseThrow(() -> new RuntimeException("Tạo tình trạng hôn nhân thất bại vui lòng thử lại"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_MARITAL')")
    public MaritalStatusResponse findOneMaritalStatus(@PathVariable("id") Integer id) {
        return maritalStatusClient
                .getMaritalStatus(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tình trạng hôn nhân có id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_MARITAL')")
    public MaritalStatusResponse updateMaritalStatus(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody MaritalStatusRequest maritalStatusRequest) {
        long useId = userDetails.getAccount().getId();
        MaritalStatus maritalStatus = maritalStatusClient
                .getMaritalStatus(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tình trạng hôn nhân có id = " + id))
                .maritalStatus;

        return maritalStatusClient
                .updateMaritalStatus(useId, maritalStatus, maritalStatusRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật tình trạng hôn nhân thất bại. Vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_MARITAL')")
    public boolean deleteMaritalStatus(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long useId = userDetails.getAccount().getId();
        MaritalStatus maritalStatus = maritalStatusClient
                .getMaritalStatus(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy tình trạng hôn nhân có id = " + id))
                .maritalStatus;
        return maritalStatusClient.deleteMaritalStatus(useId, maritalStatus);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_MARITAL')")
    public PageResponse<MaritalStatusResponse> getPage(@RequestBody(required = false) Map<String, Object> query) {
        return maritalStatusClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_MARITAL')")
    public List<MaritalStatusResponse> getAll(@RequestBody(required = false) Map<String, Object> query) {
        return maritalStatusClient.getAllMaritalStatus(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_MARITAL')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<MaritalStatus> maritalStatuses = maritalStatusClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportMaritalStatus(maritalStatuses);
    }

}
