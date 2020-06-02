package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequest {
    private Integer id;
    private Integer congTacVienId;
    private String ten;
    private String levels;
    private String parent;
    private String khuVucId; // dia chi xa
    private String districtId;
    private String provinceId;
    private String ghiChu;
    private String tenDayDu;
    private Boolean exportStatus;
    private Integer userId;
    private Integer addressIdOld;
    private Long dateUpdate;
    private Boolean isDeleted;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
}
