package vn.byt.qlds.model._province.person;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonResponse implements Serializable {
    private Person person;
    private String regionName;
    private String relationName;
    private String residenceName;
    private String educationName;
    private String technicalName;
    private String maritalName;
    private String genderName;
    private String invalidName;


}