package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.GenderClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.gender.GenderRequest;
import vn.byt.qlds.model.gender.GenderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/gender")
public class GenderEndpoint {
    @Autowired
    GenderClient genderClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_GENDER')")
    public GenderResponse createGender(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody(required = false) GenderRequest genderRequest) {
        long userId = userDetails.getAccount().getId();
        return genderClient.createGender(userId, genderRequest);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_GENDER')")
    public GenderResponse findGenderById(@PathVariable("id") Integer id) {
        return genderClient
                .getGender(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy giới tính có idd = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_GENDER')")
    public GenderResponse updateGender(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody GenderRequest genderRequest) {
        long userId = userDetails.getAccount().getId();
        return genderClient
                .updateGender(userId, id, genderRequest)
                .orElseThrow(() -> new NotFoundException("Không tạo thành công"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_GENDER')")
    public boolean deleteGender(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") int id) {
        long userId = userDetails.getAccount().getId();
        return genderClient.deleteGender(userId, id);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_GENDER')")
    public PageResponse<GenderResponse> getPage(@RequestBody(required = false) Map<String, Object> query) {
        return genderClient.getPage(query != null ? query : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_GENDER')")
    public List<GenderResponse> getAll(@RequestBody(required = false) Map<String, Object> query) {
        return genderClient.getAll(query != null ? query : new HashMap<>());
    }

    @PostMapping("/count")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_GENDER')")
    public Integer count(@RequestBody Map<String, Object> query) {
        return genderClient.count(query != null ? query : new HashMap<>());
    }
}
