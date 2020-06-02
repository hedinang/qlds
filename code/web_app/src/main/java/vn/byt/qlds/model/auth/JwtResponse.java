package vn.byt.qlds.model.auth;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class JwtResponse implements Serializable {
    private static final long serialVersionUID = -8091879091924046844L;
    private final String token;
    private String parent;
    private Integer level;
    private String regionId;
    public JwtResponse(String token) {
        this.token = token;
    }

}