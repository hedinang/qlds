package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.TransferPersonClient;
import vn.byt.qlds.core.base.PageRequest;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.transfer.TransferPerson;
import vn.byt.qlds.model.transfer.TransferPersonRequest;
import vn.byt.qlds.model.transfer.TransferPersonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/transfer-person")
public class TransferPersonEndpoint {
    @Autowired
    TransferPersonClient transferPersonClient;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_TRANSFER_PERSON')")
    public TransferPersonResponse createTransferPerson(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody TransferPersonRequest request) {
        long userId = userDetails.getAccount().getId();
        return transferPersonClient
                .createTransferPerson(userId, request)
                .orElseThrow(() -> new RuntimeException("Tạo yêu cầu chuyển người dân thất bại!"));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TRANSFER_PERSON')")
    public TransferPersonResponse findById(@PathVariable("id") Integer id) {
        return transferPersonClient
                .getTransferPerson(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển người dân  id = " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TRANSFER_PERSON')")
    public TransferPersonResponse updateTransferPerson(
            @AuthenticationPrincipal JwtUserDetails userDetails,

            @PathVariable("id") Integer id,
            @RequestBody TransferPersonRequest request) {
        long userId = userDetails.getAccount().getId();

        TransferPerson transferPerson = transferPersonClient
                .getTransferPerson(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển người dân id = " + id))
                .transferPerson;

        return transferPersonClient
                .updateTransferPerson(userId, transferPerson, request)
                .orElseThrow(() -> new RuntimeException("Cập nhật yêu cầu chuyển người dân thất bại, vui lòng thử lại!"));

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','DELETE_TRANSFER_PERSON')")
    public boolean deleteUnitCategory(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        TransferPerson transferPerson = transferPersonClient
                .getTransferPerson(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển người dân id = " + id))
                .transferPerson;
        return transferPersonClient.deleteTransferPerson(userId, transferPerson);
    }

    @PostMapping("/all")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TRANSFER_PERSON')")
    public List<TransferPersonResponse> getAllTransferPerson() {
        return transferPersonClient.getAllTransferPerson();
    }

    @PostMapping("/search-page")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_TRANSFER_PERSON')")
    public PageResponse<TransferPersonResponse> getPageTransferPerson(@RequestBody PageRequest pageRequest) {
        return transferPersonClient.getPage(pageRequest);
    }

    @PutMapping("{id}/accepted")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TRANSFER_PERSON')")
    public boolean accepted(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        String db = userDetails.getAccount().getDbName();
        TransferPerson transferPerson = transferPersonClient
                .getTransferPerson(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển người dân id = " + id))
                .transferPerson;
        return transferPersonClient.accepted(db, userId, transferPerson);
    }

    @PutMapping("{id}/rejected")
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_TRANSFER_PERSON')")
    public boolean rejected(@AuthenticationPrincipal JwtUserDetails userDetails,
                            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        TransferPerson transferPerson = transferPersonClient
                .getTransferPerson(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy yêu cầu chuyển người dân id = " + id))
                .transferPerson;
        return transferPersonClient.rejected(userId, transferPerson);
    }

}
