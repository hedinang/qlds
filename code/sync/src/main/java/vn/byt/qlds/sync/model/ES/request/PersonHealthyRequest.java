package vn.byt.qlds.sync.model.ES.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersonHealthyRequest {
    private Integer generateId;
    private String regionId;
    private Integer personalId;
    private Integer generateCode;
    private Integer birthNumber;
    private Integer weight1;
    private Integer weight2;
    private Integer weight3;
    private Integer weight4;
    private String placeOfBirth;
    private Integer pregnantCheckNumber;
    private Boolean pregnantAbortNoUse;
    private Integer userId;
    private Boolean exportStatus;
    private String deliver;
    private String resultSlss;
    private String resultSlts1;
    private String resultSlts2;
    private Boolean isDeleted;
    private Long dateUpdate;
    private Long dateSlts2;
    private Long dateSlts1;
    private Long dateSlss;
    private Long createdDate;
    private Long genDate;
    private Long timeCreated;
    private Long timeLastUpdated;
    private Long userCreated;
    private Long userLastUpdated;
    private int genInAge; /*tuổi của mẹ lúc đẻ, tính theo sinh nhật của mẹ (dateOfBirth) so với ngày genDate*/
}
