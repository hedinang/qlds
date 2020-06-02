package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.ReasonChangeClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.reason_change.ReasonChange;
import vn.byt.qlds.model.reason_change.ReasonChangeRequest;
import vn.byt.qlds.model.reason_change.ReasonChangeResponse;
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
@RequestMapping("/reason-change")
public class ReasonChangeEndpoint {
    @Autowired
    ReasonChangeClient reasonChangeClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_REASON')")
    public ReasonChangeResponse createReasonChange(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody ReasonChangeRequest reasonChangeRequest) {
        long userId = userDetails.getAccount().getId();
        return reasonChangeClient
                .createReasonChange(userId, reasonChangeRequest)
                .orElseThrow(() -> new RuntimeException("Tạo lý do thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REASON')")
    public ReasonChangeResponse findReasonById(@PathVariable("id") Integer id) {
        return reasonChangeClient
                .getReasonChange(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lý do id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_REASON')")
    public ReasonChangeResponse updateReasonChange(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody ReasonChangeRequest reasonChangeRequest) {
        long userId = userDetails.getAccount().getId();
        ReasonChange reasonChange = reasonChangeClient
                .getReasonChange(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lý do id = " + id))
                .reasonChange;
        return reasonChangeClient
                .updateReasonChange(userId, reasonChange, reasonChangeRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật lý do thất bại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_REASON')")
    public boolean deleteReasonChange(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        ReasonChange reasonChange = reasonChangeClient
                .getReasonChange(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy lý do id = " + id))
                .reasonChange;
        return reasonChangeClient.deleteReasonChange(userId, reasonChange);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REASON')")
    public PageResponse<ReasonChangeResponse> getPageReasonChange(@RequestBody(required = false) Map<String, Object> query) {
        return reasonChangeClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_REASON')")
    public List<ReasonChangeResponse> getAllReasonChange(@RequestBody(required = false) Map<String, Object> query) {
        return reasonChangeClient.getAllReasonChange(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_REASON')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<ReasonChange> reasonChanges = reasonChangeClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportReasonChange(reasonChanges);
    }

}
