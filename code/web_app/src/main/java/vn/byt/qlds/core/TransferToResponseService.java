package vn.byt.qlds.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.byt.qlds.client.*;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model._province.address.Address;
import vn.byt.qlds.model._province.address.AddressResponse;
import vn.byt.qlds.model._province.family_plan.FamilyPlanningHistory;
import vn.byt.qlds.model._province.family_plan.FamilyPlanningHistoryResponse;
import vn.byt.qlds.model._province.house.HouseHold;
import vn.byt.qlds.model._province.house.HouseHoldResponse;
import vn.byt.qlds.model._province.person.*;
import vn.byt.qlds.model.account.Account;
import vn.byt.qlds.model.account.AccountResponse;
import vn.byt.qlds.model.account.AccountRole;
import vn.byt.qlds.model.area.AreaCategory;
import vn.byt.qlds.model.area.AreaCategoryResponse;
import vn.byt.qlds.model.collaborator.Collaborator;
import vn.byt.qlds.model.collaborator.CollaboratorResponse;
import vn.byt.qlds.model.contraceptive.Contraceptive;
import vn.byt.qlds.model.contraceptive.ContraceptiveResponse;
import vn.byt.qlds.model.dead.DeadCategory;
import vn.byt.qlds.model.dead.DeadResponse;
import vn.byt.qlds.model.disability.DisabilityCategory;
import vn.byt.qlds.model.disability.DisabilityResponse;
import vn.byt.qlds.model.education.EducationCategory;
import vn.byt.qlds.model.education.EducationCategoryResponse;
import vn.byt.qlds.model.ethnic.NationCategory;
import vn.byt.qlds.model.ethnic.NationCategoryResponse;
import vn.byt.qlds.model.gender.Gender;
import vn.byt.qlds.model.gender.GenderResponse;
import vn.byt.qlds.model.group.UserGroupCategory;
import vn.byt.qlds.model.group.UserGroupCategoryResponse;
import vn.byt.qlds.model.marial.MaritalStatus;
import vn.byt.qlds.model.marial.MaritalStatusResponse;
import vn.byt.qlds.model.nationlity.Nationality;
import vn.byt.qlds.model.nationlity.NationalityResponse;
import vn.byt.qlds.model.permission.PermissionCategory;
import vn.byt.qlds.model.permission.PermissionCategoryResponse;
import vn.byt.qlds.model.reason_change.ReasonChange;
import vn.byt.qlds.model.reason_change.ReasonChangeResponse;
import vn.byt.qlds.model.region.RegionChange;
import vn.byt.qlds.model.region.RegionChangeResponse;
import vn.byt.qlds.model.relationship.Relationship;
import vn.byt.qlds.model.relationship.RelationshipResponse;
import vn.byt.qlds.model.residence.ResidenceStatus;
import vn.byt.qlds.model.residence.ResidenceStatusResponse;
import vn.byt.qlds.model.separation_house.SeparationHouseHold;
import vn.byt.qlds.model.separation_house.SeparationHouseHoldResponse;
import vn.byt.qlds.model.technical.TechnicalCategory;
import vn.byt.qlds.model.technical.TechnicalCategoryResponse;
import vn.byt.qlds.model.transfer.TransferHouseHold;
import vn.byt.qlds.model.transfer.TransferHouseHoldResponse;
import vn.byt.qlds.model.transfer.TransferPerson;
import vn.byt.qlds.model.transfer.TransferPersonResponse;
import vn.byt.qlds.model.unit.*;
import vn.byt.qlds.service.UnitCategoryService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferToResponseService {
    @Autowired
    CollaboratorClient collaboratorClient;
    @Autowired
    PermissionCategoryClient permissionCategoryClient;
    @Autowired
    UnitLevelCategoryClient unitLevelCategoryClient;
    @Autowired
    RolePermissionClient rolePermissionClient;
    @Autowired
    AccountRoleClient accountRoleClient;
    @Autowired
    UserGroupCategoryClient userGroupCategoryClient;
    @Autowired
    UnitCategoryClient unitCategoryClient;
    @Autowired
    EducationCategoryClient educationCategoryClient;
    @Autowired
    TechnicalCategoryClient technicalCategoryClient;
    @Autowired
    MaritalStatusClient maritalStatusClient;
    @Autowired
    GenderClient genderClient;
    @Autowired
    RelationshipClient relationshipClient;
    @Autowired
    ResidenceStatusClient residenceStatusClient;
    @Autowired
    DisabilityCategoryClient disabilityCategoryClient;
    @Autowired
    PersonClient personClient;
    @Autowired
    HouseHoldClient houseHoldClient;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    AddressClient addressClient;
    @Autowired
    ReasonChangeClient reasonChangeClient;
    @Autowired
    ContraceptiveCategoryClient contraceptiveCategoryClient;

    public AddressResponse transfer(String db, Address address) {
        AddressResponse addressResponse = null;
        if (address != null) {
            addressResponse = new AddressResponse();
            CollaboratorResponse collaboratorResponse = collaboratorClient
                    .getCollaborator(db, address.getCollaboratorId())
                    .orElse(new CollaboratorResponse());
            Collaborator collaborator = collaboratorResponse.collaborator;
            addressResponse.setAddress(address);
            if (collaborator != null)
                addressResponse.setCollaboratorName(
                        collaborator.getLastName()
                                + " " + collaborator.getFirstName());
        }
        return addressResponse;
    }

    public UnitLevelCategoryResponse transfer(UnitLevelCategory unitLevelCategory) {
        UnitLevelCategoryResponse response = null;
        if (unitLevelCategory != null) {
            response = new UnitLevelCategoryResponse();
            response.unitLevelCategory = unitLevelCategory;
        }
        return response;
    }

    public AreaCategoryResponse transfer(AreaCategory areaCategory) {
        AreaCategoryResponse response = null;
        if (areaCategory != null) {
            response = new AreaCategoryResponse();
            response.areaCategory = areaCategory;
        }
        return response;
    }

    public CollaboratorResponse transfer(Collaborator collaborator) {
        CollaboratorResponse response = null;
        if (collaborator != null) {
            response = new CollaboratorResponse();
            response.collaborator = collaborator;
        }
        return response;
    }

    public ContraceptiveResponse transfer(Contraceptive contraceptive) {
        ContraceptiveResponse response = null;
        if (contraceptive != null) {
            response = new ContraceptiveResponse();
            response.contraceptive = contraceptive;
        }
        return response;
    }

    public DeadResponse transfer(DeadCategory deadCategory) {
        DeadResponse response = null;
        if (deadCategory != null) {
            response = new DeadResponse();
            response.deadCategory = deadCategory;
        }
        return response;
    }

    public DisabilityResponse transfer(DisabilityCategory disabilityCategory) {
        DisabilityResponse response = null;
        if (disabilityCategory != null) {
            response = new DisabilityResponse();
            response.disabilityCategory = disabilityCategory;
        }
        return response;
    }

    public EducationCategoryResponse transfer(EducationCategory educationCategory) {
        EducationCategoryResponse response = null;
        if (educationCategory != null) {
            response = new EducationCategoryResponse();
            response.educationCategory = educationCategory;
        }
        return response;
    }

    public MaritalStatusResponse transfer(MaritalStatus maritalStatus) {
        MaritalStatusResponse response = null;
        if (maritalStatus != null) {
            response = new MaritalStatusResponse();
            response.maritalStatus = maritalStatus;
        }
        return response;
    }

    public NationalityResponse transfer(Nationality nationality) {
        NationalityResponse response = null;
        if (nationality != null) {
            response = new NationalityResponse();
            response.nationality = nationality;
        }
        return response;
    }

    public NationCategoryResponse transfer(NationCategory nationCategory) {
        NationCategoryResponse response = null;
        if (nationCategory != null) {
            response = new NationCategoryResponse();
            response.nationCategory = nationCategory;
        }
        return response;
    }

    public PermissionCategoryResponse transfer(PermissionCategory permissionCategory) {
        PermissionCategoryResponse response = null;
        if (permissionCategory != null) {
            response = new PermissionCategoryResponse();
            response.permissionCategory = permissionCategory;
        }
        return response;
    }

    public ReasonChangeResponse transfer(ReasonChange reasonChange) {
        ReasonChangeResponse response = null;
        if (reasonChange != null) {
            response = new ReasonChangeResponse();
            response.reasonChange = reasonChange;
        }
        return response;
    }

    public RelationshipResponse transfer(Relationship relationship) {
        RelationshipResponse response = null;
        if (relationship != null) {
            response = new RelationshipResponse();
            response.relationship = relationship;
        }
        return response;
    }

    public ResidenceStatusResponse transfer(ResidenceStatus residenceStatus) {
        ResidenceStatusResponse response = null;
        if (residenceStatus != null) {
            response = new ResidenceStatusResponse();
            response.residenceStatus = residenceStatus;
        }
        return response;
    }

    public TechnicalCategoryResponse transfer(TechnicalCategory technicalCategory) {
        TechnicalCategoryResponse response = null;
        if (technicalCategory != null) {
            response = new TechnicalCategoryResponse();
            response.technicalCategory = technicalCategory;
        }
        return response;
    }

    public UnitCategoryResponse transfer(UnitCategory unitCategory) {
        UnitCategoryResponse response = null;
        if (unitCategory != null) {
            response = new UnitCategoryResponse();
            response.unitCategory = unitCategory;
        }
        return response;
    }

    public GenderResponse transfer(Gender gender) {
        GenderResponse response = null;
        if (gender.getName() != null) {
            response = new GenderResponse();
            response.setGender(gender);
        }
        return response;
    }

    public RegionChangeResponse transfer(RegionChange regionChange) {
        RegionChangeResponse response = null;
        if (regionChange != null) {
            response = new RegionChangeResponse();
            response.setRegionChange(regionChange);
        }
        return response;
    }

    public UserGroupCategoryResponse transfer(UserGroupCategory userGroupCategory) {
        UserGroupCategoryResponse response = null;
        if (userGroupCategory != null) {
            response = new UserGroupCategoryResponse();
            UnitLevelCategory unitLevelCategory = unitLevelCategoryClient
                    .getUnitLevelCategory(userGroupCategory.getLevelId())
                    .orElse(new UnitLevelCategoryResponse())
                    .unitLevelCategory;
            List<PermissionCategory> permissionCategories = rolePermissionClient
                    .getRolePermissionByRoleId(userGroupCategory.getId())
                    .stream()
                    .map(rolePermission -> permissionCategoryClient
                            .getPermissionCategory(rolePermission.getPermissionId())
                            .orElse(new PermissionCategoryResponse())
                            .permissionCategory)
                    .collect(Collectors.toList());
            response.userGroupCategory = userGroupCategory;
            response.permissionCategories = permissionCategories;
            response.unitLevelCategory = unitLevelCategory;
        }
        return response;
    }

    public AccountResponse transfer(Account account) {
        AccountResponse response = new AccountResponse();
        if (account != null) {
            UnitResponse unitCategory = unitCategoryClient
                    .getUnitByCode(account.getRegionId())
                    .orElse(new UnitResponse());

            List<AccountRole> accountRoles = accountRoleClient.getAccountRoleByAccountId(account.getId());
            for (AccountRole accountRole : accountRoles) {
                UserGroupCategoryResponse userGroupCategoryResponse =
                        userGroupCategoryClient.getUserGroupCategory(accountRole.getRoleId())
                                .orElse(new UserGroupCategoryResponse());
                response.userGroupResponse.add(userGroupCategoryResponse);
            }
            response.account = account;
            response.account.setPassword("");
            response.level = unitCategory.levels;
            response.unitName = unitCategory.name;
            response.parent = unitCategory.parent;
        }
        return response;
    }

    public TransferHouseHoldResponse transfer(TransferHouseHold transferHouseHold) {
        TransferHouseHoldResponse response = null;
        if (transferHouseHold != null) {
            response = new TransferHouseHoldResponse();
            String startDate = StringUtils.convertTimestampToDate(transferHouseHold.getStartDate(), "dd/MM/yyyy");
            response.id = transferHouseHold.getId();
            response.houseHoldId = transferHouseHold.getHouseHoldId();
            response.houseHoldName = transferHouseHold.getHouseHoldName();
            response.addressIdOld = transferHouseHold.getAddressIdOld();
            response.addressOld = transferHouseHold.getAddressOld();
            response.addressIdNew = transferHouseHold.getAddressIdNew();
            response.addressNew = transferHouseHold.getAddressNew();
            response.regionIdOld = transferHouseHold.getRegionIdOld();
            response.regionIdNew = transferHouseHold.getRegionIdNew();
            response.status = transferHouseHold.getStatus();
            response.startDate = startDate;
        }
        return response;
    }

    public SeparationHouseHoldResponse transfer(SeparationHouseHold separationHouseHold) {
        SeparationHouseHoldResponse response = null;
        UnitResponse unitOld = unitCategoryClient.getUnitByCode(separationHouseHold.getRegionIdOld()).orElse(new UnitResponse());
        UnitResponse unitNew = unitCategoryClient.getUnitByCode(separationHouseHold.getRegionIdNew()).orElse(new UnitResponse());
        Person person = personClient.findPersonById(separationHouseHold.getRegionIdOld(), separationHouseHold.getPersonId());
        HouseHold houseHoldOld = houseHoldClient.findHouseHoldById(
                separationHouseHold.getRegionIdOld(),
                separationHouseHold.getHouseHoldIdOld());
        HouseHold houseHoldNew = null;
        if (separationHouseHold.getHouseHoldIdNew() != null) {
            houseHoldNew = houseHoldClient.findHouseHoldById(separationHouseHold.getRegionIdNew(), separationHouseHold.getHouseHoldIdNew());
        }
        Address addressOld = addressClient.findAddressById(separationHouseHold.getRegionIdOld(), separationHouseHold.getAddressIdOld()).orElse(null);
        Address addressNew = addressClient.findAddressById(separationHouseHold.getRegionIdNew(), separationHouseHold.getAddressIdNew()).orElse(null);

        response = new SeparationHouseHoldResponse();
        response.separationHouseHold = separationHouseHold;
        if (person != null)
            response.personName = person.getLastName() + " " + person.getFirstName();

        response.houseHoldOldName = houseHoldOld != null ? houseHoldOld.getHouseHoldNumber() : "";
        response.addressOldName = addressOld != null ? addressOld.getName() : "";
        response.regionOldName = unitOld.name;

        response.houseHoldNewName = houseHoldNew != null ? houseHoldNew.getHouseHoldNumber() : "";
        response.addressNewName = addressNew != null ? addressNew.getName() : "";
        response.regionNewName = unitNew.name;
        return response;
    }

    public TransferPersonResponse transfer(TransferPerson transferPerson) {
        TransferPersonResponse response = null;
        if (transferPerson != null) {
            response = new TransferPersonResponse();
            response.transferPerson = transferPerson;
        }
        return response;
    }

    /*province*/

    public HouseHoldResponse transfer(HouseHold houseHold) {
        HouseHoldResponse response = null;
        if (houseHold != null) {
            response = new HouseHoldResponse();
            response.houseHold = houseHold;
        }
        return response;
    }

    public PersonResponse transfer(Person person) {
        PersonResponse personResponse = null;

        if (person != null) {
            personResponse = new PersonResponse();
            EducationCategory educationCategory = educationCategoryClient
                    .getLevelCategory(person.getEducationId())
                    .orElse(new EducationCategoryResponse())
                    .educationCategory;
            TechnicalCategory technicalCategory = technicalCategoryClient
                    .getTechnicalCategory(person.getTechnicalId())
                    .orElse(new TechnicalCategoryResponse())
                    .technicalCategory;
            MaritalStatus maritalStatus = maritalStatusClient
                    .getMaritalStatus(person.getMaritalId())
                    .orElse(new MaritalStatusResponse())
                    .maritalStatus;
            Gender gender = genderClient
                    .getGender(person.getSexId())
                    .orElse(new GenderResponse())
                    .getGender();
            Relationship relationship = relationshipClient
                    .getRelationship(person.getRelationId())
                    .orElse(new RelationshipResponse())
                    .getRelationship();
            ResidenceStatus residenceStatus = residenceStatusClient
                    .getResidenceStatus(person.getResidenceId())
                    .orElse(new ResidenceStatusResponse())
                    .getResidenceStatus();
            DisabilityCategory invalid = disabilityCategoryClient
                    .getDisabilityByCode(person.getInvalidId())
                    .orElse(new DisabilityCategory());
            UnitResponse unitCategory = unitCategoryClient
                    .getUnitByCode(person.getRegionId())
                    .orElse(new UnitResponse());
            String relation = relationship != null ? relationship.getRelationName() : "";
            String genderName = gender != null ? gender.getName() : "";
            String education = educationCategory != null ? educationCategory.getName() : "";
            String technical = technicalCategory != null ? technicalCategory.getTechnicalName() : "";
            String marital = maritalStatus != null ? maritalStatus.getMaritalName() : "";
            String residence = residenceStatus != null ? residenceStatus.getResidenceName() : "";
            personResponse.setPerson(person);
            personResponse.setRelationName(relation);
            personResponse.setResidenceName(residence);
            personResponse.setEducationName(education);
            personResponse.setTechnicalName(technical);
            personResponse.setMaritalName(marital);
            personResponse.setGenderName(genderName);
            personResponse.setInvalidName(invalid.getName());
            personResponse.setRegionName(unitCategory.name);
        }
        return personResponse;
    }

    public PersonHealthyResponse transfer(PersonHealthy personHealthy) {
        PersonHealthyResponse response = null;
        if (personHealthy != null) {
            response = new PersonHealthyResponse();
            response.personHealthy = personHealthy;
        }
        return response;
    }

    public PersonHistoryResponse transfer(PersonHistory personHistory) {
        PersonHistoryResponse response = null;
        if (personHistory != null) {
            ReasonChange reasonChange = reasonChangeClient.findReasonChangeByCode(personHistory.getChangeTypeCode());
            response = new PersonHistoryResponse();
            response.personHistory = personHistory;
            response.changeTypeName = reasonChange != null ? reasonChange.getChangeTypeName() : "";
        }
        return response;
    }

    public FamilyPlanningHistoryResponse transfer(FamilyPlanningHistory familyPlanningHistory) {
        FamilyPlanningHistoryResponse response = null;
        if (familyPlanningHistory != null) {
            Contraceptive contraceptive = contraceptiveCategoryClient.findByCode(familyPlanningHistory.getContraceptiveCode());
            response = new FamilyPlanningHistoryResponse();
            response.familyPlanningHistory = familyPlanningHistory;
            response.contraceptiveName = contraceptive != null ? contraceptive.getName() : "";
        }
        return response;
    }
}
