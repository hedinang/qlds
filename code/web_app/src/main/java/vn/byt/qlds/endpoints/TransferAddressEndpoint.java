package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.TransferAddressClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.transfer.TransferAddress;
import vn.byt.qlds.model.transfer.TransferAddressRequest;
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
@RequestMapping("/transfer-address")
public class TransferAddressEndpoint {
    @Autowired
    TransferAddressClient transferAddressClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_TRANSFER_ADDRESS')")
    public TransferAddress createTransferRegion(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody TransferAddressRequest requestBody) {
        long userId = userDetails.getAccount().getId();
        return transferAddressClient
                .create(userId, requestBody)
                .orElseThrow(() -> new RuntimeException("Tạo yêu cầu chuyển địa chỉ thất bại thất bại!"));
    }

    @PutMapping("/{id}/accepted")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TRANSFER_ADDRESS')")
    public boolean accepted(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") String id) {
        String db = userDetails.getAccount().getDbName();
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        long userId = userDetails.getAccount().getId();
        TransferAddress transferAddress = transferAddressClient
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển địa chỉ id = " + id));
        return transferAddressClient.accepted(db, userId, transferAddress);
    }

    @PutMapping("/{id}/rejected")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TRANSFER_ADDRESS')")
    public boolean rejected(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") String id) {
        String db = userDetails.getAccount().getDbName();
        long userId = userDetails.getAccount().getId();
        TransferAddress transferAddress = transferAddressClient
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển địa chỉ id = " + id));
        return transferAddressClient.rejected(userId, transferAddress);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TRANSFER_ADDRESS')")
    public List<TransferAddress> getAll(@RequestBody(required = false) Map<String, Object> query) {
        return transferAddressClient.getAll(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TRANSFER_ADDRESS')")
    public PageResponse<TransferAddress> getPage(@RequestBody(required = false) Map<String, Object> query) {
        return transferAddressClient.getPage(query != null ? query : new HashMap<>());
    }
}
