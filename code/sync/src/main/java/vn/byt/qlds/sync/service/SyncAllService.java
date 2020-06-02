package vn.byt.qlds.sync.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.sync.model.ES.ESMessageSync;
import vn.byt.qlds.sync.service.common.*;
import vn.byt.qlds.sync.service.province.*;

import java.text.ParseException;

@Component
public class SyncAllService {

    @Autowired
    GenderService genderService;
    @Autowired
    PersonService personService;
    @Autowired
    AddressService addressService;
    @Autowired
    HouseHoldService houseHoldService;
    @Autowired
    RegionChangeTypeService regionChangeTypeService;
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    PersonHistoryService personHistoryService;
    @Autowired
    ReasonChangeService reasonChangeService;
    @Autowired
    FamilyPlanningHistoryService familyPlanningHistoryService;
    @Autowired
    ContraceptiveCategoryService contraceptiveCategoryService;
    @Autowired
    PersonHealthyService personHealthyService;
    @Autowired
    OperationstatisticinforService operationstatisticinforService;
    @Autowired
    CollaboratorService collaboratorService;
    @Autowired
    MaritalStatusService maritalStatusService;
    @Autowired
    NationCategoryService nationCategoryService;
    @Autowired
    RelationshipService relationshipService;
    @Autowired
    ResidenceService residenceService;
    @Autowired
    TechnicalService technicalService;
    @Autowired
    EducationService educationService;
    @Autowired
    AreaCategoryService areaCategoryService;
    @Autowired
    DeadCategoryService deadCategoryService;
    @Autowired
    NationalityService nationalityService;
    @Autowired
    UserGroupService userGroupService;
    @Autowired
    TransferHouseHoldService transferHouseHoldService;
    @Autowired
    UnitLevelCategoryService unitLevelCategoryService;
    @Autowired
    TransferAddressService transferAddressService;
    @Autowired
    SeparationHHService separationHHService;
    @Autowired
    PermissionService permissionService;
    @RabbitListener(queues = "All")
    public void receiveSyncAll(Message message) throws JsonProcessingException, ParseException {
//        String dbName = new String(message.getBody());
        ESMessageSync esMessageSync = new Gson().fromJson(new String(message.getBody()), ESMessageSync.class);
        if (esMessageSync.getTableName().equals("RegionChangeType")) regionChangeTypeService.syncAllRegionChangeType();
        if (esMessageSync.getTableName().equals("ReasonChange")) reasonChangeService.syncAllReasonChange();
        if (esMessageSync.getTableName().equals("Gender")) genderService.syncAllGender();
        if (esMessageSync.getTableName().equals("UnitCategory")) unitCategoryService.syncAllUnitCategory();
        if (esMessageSync.getTableName().equals("Address"))
            addressService.syncAllAddress(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("Person")) personService.syncAllPerson(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("HouseHold"))
            houseHoldService.syncAllHouseHold(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("PersonHistory"))
            personHistoryService.syncAllPersonHistory(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("ContraceptiveCategory"))
            contraceptiveCategoryService.syncAllContraceptiveCategory();
        if (esMessageSync.getTableName().equals("FamilyPlanningHistory"))
            familyPlanningHistoryService.syncAllFamilyPlanningHistory(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("PersonHealthy"))
            personHealthyService.syncAllPersonHealthy(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("Operationstatisticinfor"))
            operationstatisticinforService.syncAllStatistic(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("Collaborator"))
            collaboratorService.syncAllCollaborator(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("Marital"))
            maritalStatusService.syncAllMaritalStatus();
        if (esMessageSync.getTableName().equals("NationCategory"))
            nationCategoryService.syncAllNationCategory();
        if (esMessageSync.getTableName().equals("Relation"))
            relationshipService.syncAllRelationship();
        if (esMessageSync.getTableName().equals("Residence"))
            residenceService.syncAllResidence();
        if (esMessageSync.getTableName().equals("Technical"))
            technicalService.syncAllTechnicalCategory();
        if (esMessageSync.getTableName().equals("EducationCategory"))
            educationService.syncAllEducation();
        if (esMessageSync.getTableName().equals("AreaCategory"))
            areaCategoryService.syncAll(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("DeadCategory"))
            deadCategoryService.syncAll(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("Nationality"))
            nationalityService.syncAll(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("UserGroupCategory"))
            userGroupService.syncAll(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("TransferHouseHold"))
            transferHouseHoldService.syncAll(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("UnitLevelCategory"))
            unitLevelCategoryService.syncAll(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("TransferRegion"))
            transferAddressService.syncAll();
        if (esMessageSync.getTableName().equals("SeparationHouseHold"))
            separationHHService.syncAll(esMessageSync.getDbName());
        if (esMessageSync.getTableName().equals("PermissionCategory"))
            permissionService.syncAll();
    }
}