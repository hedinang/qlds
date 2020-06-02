package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.AccountClient;
import vn.byt.qlds.core.ExportExcelService;
import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.exception.NotFoundException;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.account.Account;
import vn.byt.qlds.model.account.AccountRequest;
import vn.byt.qlds.model.account.AccountResponse;
import vn.byt.qlds.service.UnitCategoryService;
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
@RequestMapping("/account")
public class AccountEndpoint {
    @Autowired
    AccountClient accountClient;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    ExportExcelService exportExcelService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','CREATE_ACCOUNT')")
    public AccountResponse createAccount(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @NotNull @Valid @RequestBody AccountRequest accountRequest) {
        long userId = userDetails.getAccount().getId();
        accountRequest.dbName = unitCategoryService.getProvinceByRegionCode(accountRequest.regionId).getName();
        return accountClient
                .createAccount(userId, accountRequest)
                .orElseThrow(() -> new RuntimeException("Tạo tài khoản thất bại!"));
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_ACCOUNT')")
    public AccountResponse getAccountByUserName(@RequestParam("username") String username) {
        return accountClient
                .getAccountByUserName(username)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy username id = " + username));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_ACCOUNT')")
    public AccountResponse findAccountById(@PathVariable("id") Integer id) {
        return accountClient
                .getAccountByID(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy account " + id));
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','UPDATE_ACCOUNT')")
    public AccountResponse updateAccount(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id,
            @NotNull @Valid @RequestBody AccountRequest accountRequest) {
        long userId = userDetails.getAccount().getId();
        Account account = accountClient
                .getAccount(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy account " + id));

        return accountClient
                .updateAccount(userId, account, accountRequest)
                .orElseThrow(() -> new RuntimeException("Cập nhật tài khoản thất bại!"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN', 'DELETE_ACCOUNT')")
    public boolean deletedAccount(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        Account account = accountClient
                .getAccount(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy account " + id));
        return accountClient.deleteAccount(userId, account);
    }

    @PutMapping("/{id}/lock")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','WRITE_ACCOUNT')")
    public boolean lockAccount(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        Account account = accountClient
                .getAccount(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy account " + id));
        return accountClient.lock(userId, account);
    }

    @PutMapping("/{id}/unlock")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','WRITE_ACCOUNT')")
    public boolean unlockAccount(
            @AuthenticationPrincipal JwtUserDetails userDetails,
            @PathVariable("id") Integer id) {
        long userId = userDetails.getAccount().getId();
        Account account = accountClient
                .getAccount(id)
                .orElseThrow(() -> new NotFoundException("Không tìm thấy account " + id));
        return accountClient.unlock(userId, account);
    }

    @PostMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_ACCOUNT')")
    public List<AccountResponse> getAllAccount(@RequestBody(required = false) Map<String, Object> query) {
        return accountClient.getAllAccount(query != null ? query : new HashMap<>());
    }

    @PostMapping("/search-page")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','READ_ACCOUNT')")
    public PageResponse<AccountResponse> getPage(@RequestBody(required = false) Map<String, Object> query) {
        return accountClient.getPage(query);
    }

    @PostMapping("/export")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ADMIN','EXPORT_ACCOUNT')")
    public ExportResponse export(@RequestBody(required = false) Map<String, Object> query) throws IOException {
        List<AccountResponse> accountResponses = accountClient.getAllAccount(query != null ? query : new HashMap<>());
        return exportExcelService.exportAccount(accountResponses);
    }


}
