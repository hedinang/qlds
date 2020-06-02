package vn.byt.qlds.service;

import vn.byt.qlds.client.AccountClient;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.account.Account;
import vn.byt.qlds.model.account.AccountResponse;
import vn.byt.qlds.model.group.UserGroupCategoryResponse;
import vn.byt.qlds.model.permission.PermissionCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class JwtUserDetailsService implements UserDetailsService {
    @Autowired
    RestTemplate restTemplate;
    @Value("${apiEndpointCommon}")
    String apiEndpointCommon;
    @Autowired
    AccountClient accountClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String url = apiEndpointCommon + "/account?username=" + username;
        Account account = restTemplate.getForObject(url, Account.class);
        if (account == null)
            throw new UsernameNotFoundException("Username: " + username + " not found");
        else {
            return new JwtUserDetails(account, getAuthority(account));
        }
    }

    private Set<GrantedAuthority> getAuthority(Account account) {
        AccountResponse accountResponse = accountClient.getAccountByID(account.getId())
                .orElseThrow(() -> new UsernameNotFoundException("Account: " + account.getId() + " not found"));
        Set<GrantedAuthority> grantedAuthority = new HashSet<>();
        if (account.getIsActive()) {
            List<UserGroupCategoryResponse> userGroupCategory = accountResponse.userGroupResponse;
            for (UserGroupCategoryResponse userGroupRes : userGroupCategory) {
                for (PermissionCategory permissionCategory : userGroupRes.permissionCategories) {
                    if (permissionCategory != null)
                        grantedAuthority.add(new SimpleGrantedAuthority(permissionCategory.getCode()));// add permission vao xac thuc api
                }
            }
        }
        return grantedAuthority;
    }
}