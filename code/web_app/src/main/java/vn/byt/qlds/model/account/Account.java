package vn.byt.qlds.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import vn.byt.qlds.core.utils.StringUtils;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private Integer id;
    private String userName;
    private String password;
    private String regionId;
    private String nameDisplay;
    private String email;
    private String dbName;
    private Boolean isDeleted;
    private Boolean isActive;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;


    public Account(AccountRequest request) {
        createFromRequest(request);
    }

    public Account createFromRequest(AccountRequest request) {
        if (request.password != null && !request.password.isEmpty()) {
            this.password = new BCryptPasswordEncoder().encode(request.password);
        } else {
            //todo throw password bad reqest
        }
        String dbName = StringUtils.convertNameProvinceToDbName(request.dbName); //todo xử lý convert dbname
        this.userName = request.userName;
        this.email = request.email;
        this.nameDisplay = request.nameDisplay;
        this.regionId = request.regionId;
        this.dbName = dbName;
        this.isActive = request.isActive;
        return this;
    }

}
