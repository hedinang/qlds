package vn.byt.qlds.model.search.request;

public class PersonSearch {
    public int limit;
    public int page;
    public String personName; //
    public String regionId; //
    public String addressId;
    public int genderId;//
    public String fromDateOfBirth;//
    public String toDateOfBirth;//
    public int fromAge;//
    public int toAge;//
    public int maritalId;//
    public int relationId;//
    public int ethnicId;//
    public int educationId;//
    public int residenceId;//
    public int technicalId;//
    public int contraceptiveId;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getRegionId() {
        return regionId;
    }

    public void setRegionId(String regionId) {
        this.regionId = regionId;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getFromDateOfBirth() {
        return fromDateOfBirth;
    }

    public void setFromDateOfBirth(String fromDateOfBirth) {
        this.fromDateOfBirth = fromDateOfBirth;
    }

    public String getToDateOfBirth() {
        return toDateOfBirth;
    }

    public void setToDateOfBirth(String toDateOfBirth) {
        this.toDateOfBirth = toDateOfBirth;
    }

    public int getFromAge() {
        return fromAge;
    }

    public void setFromAge(int fromAge) {
        this.fromAge = fromAge;
    }

    public int getToAge() {
        return toAge;
    }

    public void setToAge(int toAge) {
        this.toAge = toAge;
    }

    public int getMaritalId() {
        return maritalId;
    }

    public void setMaritalId(int maritalId) {
        this.maritalId = maritalId;
    }

    public int getRelationId() {
        return relationId;
    }

    public void setRelationId(int relationId) {
        this.relationId = relationId;
    }

    public int getEthnicId() {
        return ethnicId;
    }

    public void setEthnicId(int ethnicId) {
        this.ethnicId = ethnicId;
    }

    public int getEducationId() {
        return educationId;
    }

    public void setEducationId(int educationId) {
        this.educationId = educationId;
    }

    public int getResidenceId() {
        return residenceId;
    }

    public void setResidenceId(int residenceId) {
        this.residenceId = residenceId;
    }

    public int getTechnicalId() {
        return technicalId;
    }

    public void setTechnicalId(int technicalId) {
        this.technicalId = technicalId;
    }

    public int getContraceptiveId() {
        return contraceptiveId;
    }

    public void setContraceptiveId(int contraceptiveId) {
        this.contraceptiveId = contraceptiveId;
    }
}
