package vn.byt.qlds.jwt;

import vn.byt.qlds.client.AccountClient;
import vn.byt.qlds.model.account.Account;
import vn.byt.qlds.model.account.AccountResponse;
import vn.byt.qlds.model.auth.JwtResponse;
import vn.byt.qlds.service.JwtUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private String secretKey = "secret";
    private final static long validityInMilliseconds = 86400 * 1000L; // 1day
    @Autowired
    private JwtUserDetailsService userDetailsService;
    @Autowired
    AccountClient accountClient;
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public JwtResponse createToken(String username) {
        Account account = ((JwtUserDetails) userDetailsService.loadUserByUsername(username)).account;
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("dbName", account.getDbName());
        claims.put("regionId", account.getRegionId());
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        String token = Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS512, secretKey)//
                .compact();

        AccountResponse accountResponse = accountClient.getAccountByID(account.getId())
                .orElseThrow(()-> new RuntimeException("Not found!"));
        JwtResponse response = new JwtResponse(token);
        response.setRegionId(account.getRegionId());
        response.setParent(accountResponse.parent);
        response.setLevel(accountResponse.level);
        return response;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String token = req.getHeader("access-token");
        String bearerToken = token != null ? token : req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        } else {
            return null;
        }
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}