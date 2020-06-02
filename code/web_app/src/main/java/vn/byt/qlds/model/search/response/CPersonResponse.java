package vn.byt.qlds.model.search.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CPersonResponse {
    String regionName;
    Integer countPerson;
    Integer countMale;
    Integer countFemale;
    // tre em sinh ra trong thang
    Integer countChild;
}
