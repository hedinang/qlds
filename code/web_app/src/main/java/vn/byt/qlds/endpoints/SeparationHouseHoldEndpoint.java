package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.SeparationHouseHoldClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.separation_house.SeparationHouseHold;
import vn.byt.qlds.model.separation_house.SeparationHouseHoldRequest;
import vn.byt.qlds.model.separation_house.SeparationHouseHoldResponse;
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
@RequestMapping("/separation-house-hold")
public class SeparationHouseHoldEndpoint {
    @Autowired
    SeparationHouseHoldClient separationHouseHoldClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_SEPARATION_HH')")
    public SeparationHouseHoldResponse create(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody SeparationHouseHoldRequest request) {
        long userId = userDetails.getAccount().getId();
        return separationHouseHoldClient
                .create(userId, request)
                .orElseThrow(() -> new RuntimeException("Tạo yêu cầu tách hộ gia đình thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_SEPARATION_HH')")
    public SeparationHouseHoldResponse findById(@PathVariable("id") Integer id) {
        return separationHouseHoldClient
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu tách hộ id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_SEPARATION_HH')")
    public SeparationHouseHoldResponse update(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody SeparationHouseHoldRequest request) {
        long userId = userDetails.getAccount().getId();
        SeparationHouseHold separationHouseHold = separationHouseHoldClient
                .findById(id, false)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu tách hộ id = " + id));
        return separationHouseHoldClient
                .update(userId, separationHouseHold, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật yêu cầu tách hộ thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_SEPARATION_HH')")
    public boolean delete(@AuthenticationPrincipal JwtUserDetails userDetails,
                          @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        SeparationHouseHold separationHouseHold = separationHouseHoldClient
                .findById(id, false)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu tách hộ id = " + id));
        return separationHouseHoldClient.delete(userId, separationHouseHold);
    }

    @PutMapping("{id}/accepted")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_SEPARATION_HH')")
    public boolean accepted(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = userDetails.getAccount().getDbName();
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        long userId = userDetails.getAccount().getId();
        SeparationHouseHold separationHouseHold = separationHouseHoldClient
                .findById(id, false)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu tách hộ id = " + id));
        return separationHouseHoldClient.accepted(db, userId, separationHouseHold);
    }

    @PutMapping("{id}/rejected")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_SEPARATION_HH')")
    public boolean rejected(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = userDetails.getAccount().getDbName();
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        long userId = userDetails.getAccount().getId();
        SeparationHouseHold separationHouseHold = separationHouseHoldClient
                .findById(id, false)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu tách hộ id = " + id));
        return separationHouseHoldClient.rejected(userId, separationHouseHold);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_SEPARATION_HH')")
    public List<SeparationHouseHoldResponse> getAll(@RequestBody(required = false) Map<String, Object> query) {
        return separationHouseHoldClient.getAll(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_SEPARATION_HH')")
    public PageResponse<SeparationHouseHoldResponse> searchPage(@RequestBody(required = false) Map<String, Object> query) {
        return separationHouseHoldClient.getPage(query != null ? query : new HashMap<>());
    }

}
