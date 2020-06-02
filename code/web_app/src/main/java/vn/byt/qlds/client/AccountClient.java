package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.account.Account;
import vn.byt.qlds.model.account.AccountRequest;
import vn.byt.qlds.model.account.AccountResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class AccountClient {

    @Autowired
    QldsRestTemplate restTemplate;
    @Autowired
    TransferToResponseService toResponseService;
    @Autowired
    AccountRoleClient accountRoleClient;
    private String apiEndpointCommon;

    public AccountClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/account";
    }

    public Optional<AccountResponse> getAccountByUserName(String username) {
        String url = this.apiEndpointCommon + "?username=" + username;
        Account account = restTemplate.getForObject(url, Account.class);
        return Optional.ofNullable(toResponseService.transfer(account));
    }

    public Optional<AccountResponse> getAccountByID(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        Account account = restTemplate.getForObject(url, Account.class);
        return Optional.ofNullable(toResponseService.transfer(account));
    }

    public Optional<Account> getAccount(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        Account account = restTemplate.getForObject(url, Account.class);
        return Optional.ofNullable(account);
    }

    public Optional<AccountResponse> createAccount(long userId, final AccountRequest request) {
        long currentTime = System.currentTimeMillis();
        Account account = new Account(request);
        account.setIsDeleted(false);
        account.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        account.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        account.setUserCreated(userId);
        account.setUserLastUpdated(userId);
        Account result = restTemplate.postForObject(this.apiEndpointCommon, account, Account.class);

        if (result != null) {
            /*tạo thành công account => tạo accounts_roles*/
            accountRoleClient.createAccountRole(userId, result.getId(), request.roleIds);
            return Optional.ofNullable(toResponseService.transfer(result));
        } else {
            //todo throw exception
            return Optional.empty();
        }
    }

    public Optional<AccountResponse> updateAccount(long userId, Account account, AccountRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + account.getId();
        account.createFromRequest(request);
        account.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        account.setUserLastUpdated(userId);
        Account result = restTemplate.putForObject(url, account, Account.class);
        if (result != null) {
            /*update thành công => update accounts_roles nếu muốn update*/
            if (request.roleIds != null && !request.roleIds.isEmpty()) {
                accountRoleClient.updateAccountRoleByAccountId(userId, result.getId(), request.roleIds);
            }
            return Optional.ofNullable(toResponseService.transfer(result));
        } else {
            //todo throw create failed
            return Optional.empty();
        }
    }

    public boolean deleteAccount(long userId, Account account) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + account.getId();
        account.setIsDeleted(true);
        account.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        account.setUserLastUpdated(userId);
        Account result = restTemplate.putForObject(url, account, Account.class);
        return result != null;
    }

    public boolean lock(long userId, Account account) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + account.getId();
        account.setIsActive(false);
        account.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        account.setUserLastUpdated(userId);
        Account result = restTemplate.putForObject(url, account, Account.class);
        return result != null;
    }

    public boolean unlock(long userId, Account account) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + account.getId();
        account.setIsActive(true);
        account.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        account.setUserLastUpdated(userId);
        Account result = restTemplate.putForObject(url, account, Account.class);
        return result != null;
    }

    public List<AccountResponse> getAllAccount(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        ObjectMapper mapper = new ObjectMapper();
        List<Account> accounts = mapper.convertValue(restTemplate.postForObject(url, query, List.class), new TypeReference<List<Account>>() {
        });
        return accounts
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
    }

    public PageResponse<AccountResponse> getPage(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/" + "search-page";
        PageResponse<Account> accountPages = restTemplate.postForObject(url, query, PageResponse.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Account> accounts = mapper.convertValue(accountPages.getList(), new TypeReference<List<Account>>() {
        });
        List<AccountResponse> accountResponses = accounts
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        PageResponse<AccountResponse> result = new PageResponse<>();
        result.setPage(accountPages.getPage());
        result.setTotal(accountPages.getTotal());
        result.setList(accountResponses);
        return result;
    }
}

