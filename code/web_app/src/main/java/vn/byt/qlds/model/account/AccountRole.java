package vn.byt.qlds.model.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountRole {
    private Integer id;
    private Integer userId;
    private Integer roleId;
    private Boolean isDeleted;
    private Long userCreated;
    private Long userLastUpdated;
    private Timestamp timeCreated;
    private Timestamp timeLastUpdated;
}
