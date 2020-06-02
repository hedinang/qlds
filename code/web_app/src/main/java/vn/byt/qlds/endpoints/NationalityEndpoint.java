package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.NationalityClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.nationlity.Nationality;
import vn.byt.qlds.model.nationlity.NationalityRequest;
import vn.byt.qlds.model.nationlity.NationalityResponse;
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
@RequestMapping("/nationality")
public class NationalityEndpoint {
    @Autowired
    NationalityClient nationalityClient;
    @Autowired
    ExportExcelService exportExcelService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_NATIONLITY')")
    public NationalityResponse createNationality(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody NationalityRequest nationalityRequest) {
        long userId = userDetails.getAccount().getId();
        return nationalityClient
                .createNationality(userId, nationalityRequest)
                .orElseThrow(() -> new RuntimeException("Tạo quốc gia thất bại. Vui lòng thử lại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_NATIONLITY')")
    public NationalityResponse findNationalityById(@PathVariable("id") Integer id) {
        return nationalityClient
                .getNationality(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quốc gia nào có id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_NATIONLITY')")
    public NationalityResponse updateNationality(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody NationalityRequest nationalityRequest) {
        long userId = userDetails.getAccount().getId();
        Nationality nationality = nationalityClient
                .getNationality(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quốc gia nào có id = " + id))
                .nationality;

        return nationalityClient
                .updateNationality(userId, nationality, nationalityRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật quốc tịch thất bại. Vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_NATIONLITY')")
    public boolean deleteNationality(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        Nationality nationality = nationalityClient
                .getNationality(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy quốc gia nào có id = " + id))
                .nationality;
        return nationalityClient.deleteNationality(userId, nationality);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_NATIONLITY')")
    public PageResponse<NationalityResponse> getPage(@RequestBody Map<String, Object> query) {
        return nationalityClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_NATIONLITY')")
    public List<NationalityResponse> getAll(@RequestBody Map<String, Object> query) {
        return nationalityClient.getAllNationality(query != null ? query : new HashMap<>());
    }

    @PostMapping("/export")
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_NATIONLITY')")
    public ExportResponse export(@RequestBody Map<String, Object> query) throws IOException {
        List<Nationality> nationalities = nationalityClient.getAll(query != null ? query : new HashMap<>());
        return exportExcelService.exportNationality(nationalities);
    }
}
