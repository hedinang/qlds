package vn.byt.qlds.model.search.response;

public class SPersonResponse {
    String personName; //
    String genderName;
    String addressName;
    String maritalName;
    String relationName;
    String ethnicName;
    String educationName;
    String residenceName;
    String technicalName;
    String contraceptiveName;
    String dateOfBirth;
    int age;

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getGenderName() {
        return genderName;
    }

    public void setGenderName(String genderName) {
        this.genderName = genderName;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getMaritalName() {
        return maritalName;
    }

    public void setMaritalName(String maritalName) {
        this.maritalName = maritalName;
    }

    public String getRelationName() {
        return relationName;
    }

    public void setRelationName(String relationName) {
        this.relationName = relationName;
    }

    public String getEthnicName() {
        return ethnicName;
    }

    public void setEthnicName(String ethnicName) {
        this.ethnicName = ethnicName;
    }

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getResidenceName() {
        return residenceName;
    }

    public void setResidenceName(String residenceName) {
        this.residenceName = residenceName;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getContraceptiveName() {
        return contraceptiveName;
    }

    public void setContraceptiveName(String contraceptiveName) {
        this.contraceptiveName = contraceptiveName;
    }
}
