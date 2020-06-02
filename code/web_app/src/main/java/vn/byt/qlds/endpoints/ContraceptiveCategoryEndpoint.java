package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.ContraceptiveCategoryClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.contraceptive.Contraceptive;
import vn.byt.qlds.model.contraceptive.ContraceptiveRequest;
import vn.byt.qlds.model.contraceptive.ContraceptiveResponse;
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
@RequestMapping("/contraceptive-category")
public class ContraceptiveCategoryEndpoint {
    @Autowired
    ContraceptiveCategoryClient contraceptiveCategoryClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_CONTRACEPTIVE')")
    public ContraceptiveResponse createContraceptiveCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody ContraceptiveRequest request) {
        long userId = userDetails.getAccount().getId();
        return contraceptiveCategoryClient
                .createContraceptive(userId, request)
                .orElseThrow(() -> new RuntimeException("Tạo biện pháp tránh thai thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_CONTRACEPTIVE')")
    public ContraceptiveResponse findOneContraceptiveCategory(@PathVariable("id") Integer id) {
        return contraceptiveCategoryClient
                .getContraceptiveCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy biện pháp tránh thai có id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_CONTRACEPTIVE')")
    public ContraceptiveResponse updateContraceptiveCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody ContraceptiveRequest request) {
        long userId = userDetails.getAccount().getId();
        Contraceptive contraceptive = contraceptiveCategoryClient
                .getContraceptiveCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy biện pháp tránh thai có id = " + id))
                .contraceptive;
        return contraceptiveCategoryClient
                .updateContraceptiveCategory(userId, contraceptive, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật thất bại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_CONTRACEPTIVE')")
    public boolean deleteContraceptiveCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        Contraceptive contraceptive = contraceptiveCategoryClient
                .getContraceptiveCategory(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy biện pháp tránh thai có id = " + id))
                .contraceptive;
        return contraceptiveCategoryClient.deleteContraceptiveCategory(userId, contraceptive);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_CONTRACEPTIVE')")
    public List<ContraceptiveResponse> getAllContraceptiveCategory(@RequestBody(required = false) Map<String, Object> query) {
        return contraceptiveCategoryClient.getAllContraceptiveCategory(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_CONTRACEPTIVE')")
    public PageResponse<ContraceptiveResponse> getPageContraceptiveCategory(@RequestBody(required = false) Map<String, Object> query) {
        return contraceptiveCategoryClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_CONTRACEPTIVE')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<Contraceptive> contraceptives = contraceptiveCategoryClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportContraceptive(contraceptives);
    }

}
