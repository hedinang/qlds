package vn.byt.qlds.client;

import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.account.AccountRole;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AccountRoleClient {
    @Autowired
    QldsRestTemplate restTemplate;
    private String apiEndpointCommon;

    public AccountRoleClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/account-role";
    }

    private AccountRole createAccountRole(long userId, Integer accountId, Integer roleId) {
        long currentTime = System.currentTimeMillis();
        AccountRole accountRole = new AccountRole();
        accountRole.setUserId(accountId);
        accountRole.setRoleId(roleId);
        accountRole.setIsDeleted(false);
        accountRole.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        accountRole.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        accountRole.setUserCreated(userId);
        accountRole.setUserLastUpdated(userId);
        return restTemplate.postForObject(this.apiEndpointCommon, accountRole, AccountRole.class);
    }

    public List<AccountRole> createAccountRole(long userId, Integer accountId, List<Integer> roleIds) {
        Set<Integer> roleSetIds = new HashSet<>(roleIds);
        return roleSetIds
                .stream()
                .map(roleId -> createAccountRole(userId, accountId, roleId))
                .collect(Collectors.toList());
    }

    public List<AccountRole> updateAccountRoleByAccountId(long userId, Integer accountId, List<Integer> roleIds) {
        // delete all account_role điều kiện accountId
        deleteRolePermissionByRoleId(userId, accountId);
        return createAccountRole(userId, accountId, roleIds);
    }

    public boolean deleteAccountRoleById(long userId, Integer id) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + id;
        AccountRole accountRole = restTemplate.getForObject(url, AccountRole.class);
        if (accountRole != null) {
            accountRole.setIsDeleted(true);
            accountRole.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
            accountRole.setUserLastUpdated(userId);
            restTemplate.putForObject(url, accountRole, AccountRole.class);
            return true;
        } else {
            //todo throw not found
            return false;
        }
    }

    public void deleteRolePermissionByRoleId(long userId, Integer accountId) {
        List<AccountRole> accountRoles = getAccountRoleByAccountId(accountId);
        for (AccountRole accountRole : accountRoles) {
            deleteAccountRoleById(userId, accountRole.getId());
        }
    }

    public List<AccountRole> getAccountRoleByAccountId(Integer id) {
        String url = this.apiEndpointCommon + "?accountId=" + id;
        ObjectMapper mapper = new ObjectMapper();

        List<AccountRole> result = mapper.convertValue(restTemplate.getForObject(url, List.class), new TypeReference<List<AccountRole>>() {
        });
        return result;
    }
}
