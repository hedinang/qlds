package vn.byt.qlds.endpoints.province;

import vn.byt.qlds.client.HouseHoldClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model._province.house.HouseHold;
import vn.byt.qlds.model._province.house.HouseHoldRequest;
import vn.byt.qlds.model._province.house.HouseHoldResponse;
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
@RequestMapping("/house-hold")
public class HouseHoldEndpoint {
    @Autowired
    HouseHoldClient houseHoldClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_HOUSE_HOLD')")
    public HouseHoldResponse createHouseHold(
            @RequestHeader("session") String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody HouseHoldRequest houseHoldRequest) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return houseHoldClient
                .createHouseHold(db, userId, houseHoldRequest)
                .orElseThrow(() -> new RuntimeException("Tạo hộ gia đình thất bại! vui lòng thử lại"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_HOUSE_HOLD')")
    public HouseHoldResponse findHouseById(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return houseHoldClient
                .getHouseHold(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hộ gia đình id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_HOUSE_HOLD')")
    public HouseHoldResponse updateHouseHold(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody HouseHoldRequest request) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        HouseHold houseHold = houseHoldClient
                .getHouseHold(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hộ gia đình id = " + id))
                .houseHold;
        return houseHoldClient
                .updateHouseHold(db, userId, houseHold, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật hộ gia đình thất bại! vui lòng thử lại"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_HOUSE_HOLD')")
    public boolean deleteHouseHold(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        HouseHold houseHold = houseHoldClient
                .getHouseHold(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy hộ gia đình id = " + id))
                .houseHold;
        return houseHoldClient.deleteHouseHold(db, userId, houseHold);
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_HOUSE_HOLD')")
    public PageResponse<HouseHoldResponse> getPageHouseHold(@RequestBody(required = false) Map<String, Object> request) {
        return houseHoldClient.getPage(request != null ? request : new HashMap<>());
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_HOUSE_HOLD')")
    public List<HouseHoldResponse> getAllHouseHold(@RequestBody(required = false) Map<String, Object> request) {
        return houseHoldClient.getAllHouseHold(request != null ? request : new HashMap<>());
    }

}
