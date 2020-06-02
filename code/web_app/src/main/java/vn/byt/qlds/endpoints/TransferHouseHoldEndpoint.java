package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.TransferHouseHoldClient;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.transfer.TransferHouseHold;
import vn.byt.qlds.model.transfer.TransferHouseHoldRequest;
import vn.byt.qlds.model.transfer.TransferHouseHoldResponse;
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
@RequestMapping("/transfer-house-hold")
public class TransferHouseHoldEndpoint {
    @Autowired
    TransferHouseHoldClient transferHouseHoldClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_TRANSFER_HOUSE_HOLD')")
    public TransferHouseHoldResponse createTransferHouseHold(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody TransferHouseHoldRequest transferHouseHoldRequest) {
        long userId = userDetails.getAccount().getId();
        return transferHouseHoldClient
                .createTransferHouseHold(userId, transferHouseHoldRequest)
                .orElseThrow(() -> new RuntimeException("Tạo yêu cầu chuyển hộ gia đình thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TRANSFER_HOUSE_HOLD')")
    public TransferHouseHoldResponse findOneTransferHouseHold(
            @PathVariable("id") Integer id) {
        return transferHouseHoldClient
                .getTransferHouseHold(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển hộ  id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TRANSFER_HOUSE_HOLD')")
    public TransferHouseHoldResponse updateTransferHouseHold(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody TransferHouseHoldRequest request) {
        long userId = userDetails.getAccount().getId();

        TransferHouseHold transferHouseHold = transferHouseHoldClient
                .findOneById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển hộ  id = " + id));
        return transferHouseHoldClient
                .updateTransferHouseHold(userId, transferHouseHold, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật yêu cầu chuyển hộ gia đình thất bại, vui lòng thử lại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_TRANSFER_HOUSE_HOLD')")
    public boolean deleteTransferHouseHold(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        TransferHouseHold transferHouseHold = transferHouseHoldClient
                .findOneById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển hộ  id = " + id));
        return transferHouseHoldClient.deleteTransferHouseHold(userId, transferHouseHold);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TRANSFER_HOUSE_HOLD')")
    public List<TransferHouseHoldResponse> getAllTransferHouseHold(@RequestBody(required = false) Map<String, Object> query) {
        return transferHouseHoldClient.getAllTransferHouseHold(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TRANSFER_HOUSE_HOLD')")
    public PageResponse<TransferHouseHoldResponse> getPageTransferHouseHold(@RequestBody(required = false) Map<String, Object> query) {
        return transferHouseHoldClient.getPage(query != null ? query : new HashMap<>());

    }

    @PutMapping("{id}/accepted")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TRANSFER_HOUSE_HOLD')")
    public boolean accepted(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        String db = userDetails.getAccount().getDbName();
        if (db == null || db.isEmpty() || db.equals("common")) throw new BadRequestException("Bad request !");
        long userId = userDetails.getAccount().getId();
        TransferHouseHold transferHouseHold = transferHouseHoldClient
                .findOneById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển hộ  id = " + id));
        return transferHouseHoldClient.accepted(db, userId, transferHouseHold);
    }

    @PutMapping("{id}/rejected")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TRANSFER_HOUSE_HOLD')")
    public boolean rejected(@AuthenticationPrincipal JwtUserDetails userDetails,
                            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        TransferHouseHold transferHouseHold = transferHouseHoldClient
                .findOneById(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển hộ  id = " + id));
        return transferHouseHoldClient.rejected(userId, transferHouseHold);
    }

}