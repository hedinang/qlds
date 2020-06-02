package vn.byt.qlds.core.base;

import vn.byt.qlds.model._province.address.Address;
import vn.byt.qlds.model._province.house.HouseHold;
import vn.byt.qlds.model._province.person.Person;
import vn.byt.qlds.model._province.person.PersonHistory;
import vn.byt.qlds.model.area.AreaCategory;
import vn.byt.qlds.model.contraceptive.Contraceptive;
import vn.byt.qlds.model.dead.DeadCategory;
import vn.byt.qlds.model.disability.DisabilityCategory;
import vn.byt.qlds.model.education.EducationCategory;
import vn.byt.qlds.model.ethnic.NationCategory;
import vn.byt.qlds.model.group.UserGroupCategory;
import vn.byt.qlds.model.marial.MaritalStatus;
import vn.byt.qlds.model.nationlity.Nationality;
import vn.byt.qlds.model.reason_change.ReasonChange;
import vn.byt.qlds.model.relationship.Relationship;
import vn.byt.qlds.model.residence.ResidenceStatus;
import vn.byt.qlds.model.technical.TechnicalCategory;
import vn.byt.qlds.model.transfer.TransferAddress;
import vn.byt.qlds.model.transfer.TransferHouseHold;
import vn.byt.qlds.model.unit.UnitLevelCategory;
import vn.byt.qlds.model.unit.UnitResponse;

import java.util.ArrayList;

public class ConvertList {
    public static class UnitList extends ArrayList<UnitResponse> { }
    public static class TransferAddressList extends  ArrayList<TransferAddress>{ }
    public static class  HouseHoldList extends  ArrayList<HouseHold>{}
    public static class  UnitLevelList extends  ArrayList<UnitLevelCategory>{}
    public static class  AreaList extends  ArrayList<AreaCategory>{}
    public static class  ContraceptiveList extends  ArrayList<Contraceptive>{}
    public static class  DeadList extends  ArrayList<DeadCategory>{}
    public static class  InvalidList extends  ArrayList<DisabilityCategory>{}
    public static class  EducationList extends  ArrayList<EducationCategory>{}
    public static class  MaritalStatusList extends  ArrayList<MaritalStatus>{}
    public static class  NationalityList extends  ArrayList<Nationality>{}
    public static class  EthnicList extends  ArrayList<NationCategory>{}
    public static class  ReasonChangeList extends  ArrayList<ReasonChange>{}
    public static class  RelationshipList extends  ArrayList<Relationship>{}
    public static class  ResidenceList extends  ArrayList<ResidenceStatus>{}
    public static class  TechnicalList extends  ArrayList<TechnicalCategory>{}
    public static class  UserGroupCategoryList extends  ArrayList<UserGroupCategory>{}
    public static class  TransferHouseHoldList extends  ArrayList<TransferHouseHold>{}



    public static class PagePersonList extends PageResponse<Person>{}
    public static class PersonList extends ArrayList<Person>{}
    public static class PersonHistoryList extends ArrayList<PersonHistory>{}
    public static class PagePersonHistoryList extends PageResponse<PersonHistory>{}

    public static class AddressList extends ArrayList<Address>{}

}
