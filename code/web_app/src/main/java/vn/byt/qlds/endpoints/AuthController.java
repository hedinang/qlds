package vn.byt.qlds.endpoints;

import vn.byt.qlds.jwt.JwtTokenProvider;
import vn.byt.qlds.jwt.JwtUserDetails;
import vn.byt.qlds.model.auth.JwtRequest;
import vn.byt.qlds.model.auth.JwtResponse;
import vn.byt.qlds.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public JwtResponse login(@RequestBody JwtRequest data) {
        try {
            String username = data.getUsername();
            String password = data.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            return jwtTokenProvider.createToken(username);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    @PostMapping("/profile")
    public ResponseEntity<?> getProfile(@AuthenticationPrincipal JwtUserDetails userDetails) {
        Map<String, Object> response = new HashMap<>();
        Account account = userDetails.getAccount();
        response.put("id", account.getId());
        response.put("userName", account.getUserName());
        response.put("dbName", account.getDbName());
        response.put("regionId", account.getRegionId());
        return ok(response);
    }

}