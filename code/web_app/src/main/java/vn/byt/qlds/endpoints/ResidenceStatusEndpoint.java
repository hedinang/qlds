package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.ResidenceStatusClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.residence.ResidenceStatus;
import vn.byt.qlds.model.residence.ResidenceStatusRequest;
import vn.byt.qlds.model.residence.ResidenceStatusResponse;
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
@RequestMapping("/residence-status")
public class ResidenceStatusEndpoint {
    @Autowired
    ResidenceStatusClient residenceStatusClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_RESIDENCE')")
    public ResidenceStatusResponse createResidenceStatus(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody ResidenceStatusRequest residenceStatusRequest) {
        long userId = userDetails.getAccount().getId();
        return residenceStatusClient
                .createResidenceStatus(userId, residenceStatusRequest)
                .orElseThrow(() -> new RuntimeException("Tạo trạng thái cư dân thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_RESIDENCE')")
    public ResidenceStatusResponse findResidenceStatusById(
            @PathVariable("id") Integer id) {
        return residenceStatusClient
                .getResidenceStatus(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trạng thái cư dân id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_RESIDENCE')")
    public ResidenceStatusResponse updateResidenceStatus(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody ResidenceStatusRequest residenceStatusRequest) {
        long userId = userDetails.getAccount().getId();
        ResidenceStatus residenceStatus = residenceStatusClient
                .getResidenceStatus(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trạng thái cư dân id = " + id))
                .residenceStatus;
        return residenceStatusClient
                .updateResidenceStatus(userId, residenceStatus, residenceStatusRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật trạng thái cư dân thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_RESIDENCE')")
    public boolean deleteResidenceStatus(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        ResidenceStatus residenceStatus = residenceStatusClient
                .getResidenceStatus(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy trạng thái cư dân id = " + id))
                .residenceStatus;
        return residenceStatusClient.deleteResidenceStatus(userId, residenceStatus);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_RESIDENCE')")
    public PageResponse<ResidenceStatusResponse> getPageResidenceStatus(@RequestBody(required = false) Map<String, Object> query) {
        return residenceStatusClient.getPage(query != null ? query : new HashMap<>());

    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_RESIDENCE')")
    public List<ResidenceStatusResponse> getAllResidenceStatus(@RequestBody(required = false) Map<String, Object> query) {
        return residenceStatusClient.getAllResidenceStatus(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_RESIDENCE')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<ResidenceStatus> residenceStatuses =  residenceStatusClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportResidence(residenceStatuses);
    }

}
