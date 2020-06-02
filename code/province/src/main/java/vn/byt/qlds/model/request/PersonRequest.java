package vn.byt.qlds.model.request;

import vn.byt.qlds.model.Person;

import java.io.Serializable;

public class PersonRequest implements Serializable {
    public Person person;
    public String changeTypeCode;
}
