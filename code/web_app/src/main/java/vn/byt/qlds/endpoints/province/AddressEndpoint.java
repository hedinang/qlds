package vn.byt.qlds.endpoints.province;


import vn.byt.qlds.client.AddressClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model._province.address.Address;
import vn.byt.qlds.model._province.address.AddressRequest;
import vn.byt.qlds.model._province.address.AddressResponse;
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
@RequestMapping("/address")
public class AddressEndpoint {
    @Autowired
    AddressClient addressClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_ADDRESS')")
    public AddressResponse createAddress(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody AddressRequest addressRequest) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        long userId = userDetails.getAccount().getId();
        return addressClient
                .createAddress(db, userId, addressRequest)
                .orElseThrow(() -> new RuntimeException("Tạo địa chỉ thất bại! vui lòng thử lại"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_ADDRESS')")
    public AddressResponse findOneAddress(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return addressClient
                .getAddress(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy địa chỉ id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_ADDRESS')")
    public AddressResponse updateAddress(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody AddressRequest addressRequest) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        Address address = addressClient
                .getAddress(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy địa chỉ id = " + id))
                .address;
        return addressClient
                .updateAddress(db, userId, address, addressRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật địa chỉ thất bại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_ADDRESS')")
    public boolean deleteAddress(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        Address address = addressClient
                .getAddress(db, id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy địa chỉ id = " + id))
                .address;
        return addressClient.deleteAddress(db, userId, address);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_ADDRESS')")
    public List<AddressResponse> getAllAddress(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @RequestBody(required = false) Map<String, Object> query) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return addressClient.getAll(db, query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_ADDRESS')")
    public PageResponse<AddressResponse> getPage(
            @RequestHeader(value = "session", required = false) String session,
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @RequestBody(required = false) Map<String, Object> query) {
        String db = session == null ? userDetails.getAccount().getDbName() : session;
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        return addressClient.getPage(db, query != null ? query : new HashMap<>());
    }
}
