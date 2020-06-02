package vn.byt.qlds.services;


import org.springframework.stereotype.Service;
import vn.byt.qlds.config.MappingCategory;
import vn.byt.qlds.entity_from.*;
import vn.byt.qlds.entity_to.*;

import java.util.Map;

@Service
public class TransferServices {


    public CollaboratorTo transfer(FieldworkerFrom fieldworker) {
        CollaboratorTo collaboratorTo = new CollaboratorTo();
        collaboratorTo.setCollaboratorId(fieldworker.getFieldWorkerId());
        collaboratorTo.setFirstName(fieldworker.getFirstName());
        collaboratorTo.setLastName(fieldworker.getLastName());
        collaboratorTo.setHireDate(fieldworker.getHireDate());
        collaboratorTo.setRegionId(fieldworker.getRegionId());
        collaboratorTo.setSexId(fieldworker.getSexId());
        collaboratorTo.setIsCadre(fieldworker.getIsCadre());
        collaboratorTo.setExportStatus(fieldworker.getExportStatus());
        collaboratorTo.setUserId(fieldworker.getUserId());
        collaboratorTo.setDateUpdate(fieldworker.getDateUpdate());
        collaboratorTo.setIsDeleted(false);
        collaboratorTo.setTimeCreated(fieldworker.getDateUpdate());
        collaboratorTo.setTimeLastUpdate(fieldworker.getDateUpdate());
        collaboratorTo.setUserCreated(fieldworker.getUserId() != null ? new Long(fieldworker.getUserId()) : 0L);
        collaboratorTo.setUserLastUpdated(fieldworker.getUserId() != null ? new Long(fieldworker.getUserId()) : 0L);
        return collaboratorTo;
    }

    public AddressTo transfer(AddressFrom addressFrom, Map<String, Integer> collaboratorToId) {
        AddressTo addressTo = new AddressTo();
        String collaboratorIdRegionId = addressFrom.getRegionId() + addressFrom.getFieldWorkerId();
        addressTo.setAddressId(addressFrom.getAddressId());
        addressTo.setCollaboratorId(collaboratorToId.get(collaboratorIdRegionId));
        addressTo.setName(addressFrom.getAddressName());
        addressTo.setLevels(addressFrom.getLevels());
        addressTo.setParent(addressFrom.getParent());
        addressTo.setRegionId(addressFrom.getRegionId());
        addressTo.setNotes(addressFrom.getNotes());
        addressTo.setFullName(addressFrom.getFullAddress());
        addressTo.setExportStatus(addressFrom.getExportStatus());
        addressTo.setUserId(addressFrom.getUserId());
        addressTo.setAddressIdOld(addressFrom.getAddressIdOld());
        addressTo.setDateUpdate(addressFrom.getDateUpdate());
        addressTo.setIsDeleted(false);
        addressTo.setTimeCreated(addressFrom.getDateUpdate());
        addressTo.setTimeLastUpdated(addressFrom.getDateUpdate());
        addressTo.setUserCreated(addressFrom.getUserId() != null ? new Long(addressFrom.getUserId()) : 0L);
        addressTo.setUserLastUpdated(addressFrom.getUserId() != null ? new Long(addressFrom.getUserId()) : 0L);
        return addressTo;
    }

    public HouseholdTo transfer(HouseholdFrom householdFrom, Map<String, Integer> addressToId) {
        String regionIdAddressId = householdFrom.getRegionId() + householdFrom.getAddressId();
        HouseholdTo householdTo = new HouseholdTo();
        householdTo.setHouseholdId(householdFrom.getHouseHoldId());
        householdTo.setHouseholdCode(householdFrom.getHouseHoldCode());
        householdTo.setAddressId(addressToId.get(regionIdAddressId));
        householdTo.setRegionId(householdFrom.getRegionId());
        householdTo.setHouseholdNumber(householdFrom.getHouseHoldNumber());
        householdTo.setExportStatus(householdFrom.getExportStatus());
        householdTo.setUserId(householdFrom.getUserId());
        householdTo.setHouseholdOldId(householdFrom.getHouseHoldIdOld());
        householdTo.setStartDate(householdFrom.getStartDate());
        householdTo.setEndDate(householdFrom.getEndDate());
        householdTo.setIsBigHousehold(householdFrom.getIsBigHouseHold());
        householdTo.setNotes(householdFrom.getNotes());
        householdTo.setIsChecked(householdFrom.getIsChecked());
        householdTo.setIsDeleted(false);
        if (householdFrom.getUserId() != null) {
            householdTo.setUserCreated(new Long(householdFrom.getUserId()));
            householdTo.setUserLastUpdated(new Long(householdFrom.getUserId()));
        }
        return householdTo;
    }

    public PersonalTo transfer(PersonalFrom personalFrom, Map<String, Integer> houseHoldToId) {
        PersonalTo personalTo = new PersonalTo();
        String regionIdHouseHoldId = personalFrom.getRegionId() + "_" + personalFrom.getHouseHoldId();
        personalTo.setPersonalId(personalFrom.getPersonalId());
        personalTo.setFirstName(personalFrom.getFirstName());
        personalTo.setLastName(personalFrom.getLastName());
        personalTo.setDateOfBirth(personalFrom.getDateOfBirth());
        personalTo.setPlaceOfBirth(personalFrom.getPlaceOfBirth());
        personalTo.setNationalityCode(personalFrom.getNationalityCode());
        personalTo.setRelationId(MappingCategory.RELATIONSHIP_MAPPING.get(personalFrom.getRelationCode()));
        personalTo.setEthnicId(MappingCategory.ETHNIC_MAPPING.get(personalFrom.getEthnicCode()));
        personalTo.setResidenceId(MappingCategory.RESIDENCE_STATUS_MAPPING.get(personalFrom.getResidenceCode()));
        personalTo.setEducationId(MappingCategory.EDU_MAPPING.get(personalFrom.getEducationCode()));
        personalTo.setTechnicalId(MappingCategory.TECHNICAL_MAPPING.get(personalFrom.getTechnicalCode()));
        personalTo.setMaritalId(MappingCategory.MARITAL_MAPPING.get(personalFrom.getMaritalCode()));
        personalTo.setSexId(MappingCategory.GENDER_MAPPING.get(personalFrom.getSexId()));
        personalTo.setRegionId(personalFrom.getRegionId());
        personalTo.setHouseHoldId(houseHoldToId.get(regionIdHouseHoldId));
        personalTo.setPersonStatus(personalFrom.getPersonStatus());
        personalTo.setEducationLevel(personalFrom.getEducationLevel());
        personalTo.setInvalidId(MappingCategory.INVALID_MAPPING.get(personalFrom.getInvalidCode()));
        personalTo.setExportStatus(personalFrom.getExportStatus());
        personalTo.setChangeDate(personalFrom.getChangeDate());
        personalTo.setStartDate(personalFrom.getStartDate());
        personalTo.setEndDate(personalFrom.getEndDate());
        personalTo.setNotes(personalFrom.getNotes());
        personalTo.setMotherId(personalFrom.getMotherId());
        personalTo.setBirthNumber(personalFrom.getBirthNumber());
        personalTo.setGenerateId(personalFrom.getGenerateId());
        personalTo.setIsDeleted(false);
        personalTo.setTimeCreated(personalFrom.getStartDate());
        personalTo.setTimeLastUpdated(personalFrom.getStartDate());
        personalTo.setUserCreated(1L);
        personalTo.setUserLastUpdated(1L);
        return personalTo;
    }

    public PersonalHistoryTo transfer(ChangeFrom changeFrom, Map<String, Integer> personalToIds) {
        PersonalHistoryTo personalHistoryTo = new PersonalHistoryTo();
        String regionIdPersonalId = changeFrom.getRegionId() + "_" + changeFrom.getPersonalId();
        if (personalToIds.get(regionIdPersonalId) == null) {
            System.out.println(regionIdPersonalId);
        }

        if (changeFrom.getPersonalId() == null || changeFrom.getRegionId() == null) {
            personalHistoryTo.setPersonalId(null);

        } else personalHistoryTo.setPersonalId(personalToIds.get(regionIdPersonalId));

        personalHistoryTo.setChangeId(changeFrom.getChangeId());
        personalHistoryTo.setRegionId(changeFrom.getRegionId());
        personalHistoryTo.setDateUpdate(changeFrom.getDateUpdate());
        personalHistoryTo.setChangeDate(changeFrom.getChangeDate());
        personalHistoryTo.setChangeTypeCode(changeFrom.getChangeTypeCode());
        personalHistoryTo.setSource(changeFrom.getSource());
        personalHistoryTo.setDestination(changeFrom.getDestination());
        personalHistoryTo.setStatus(changeFrom.getStatus());
        personalHistoryTo.setNotes(changeFrom.getNotes());
        personalHistoryTo.setUserId(changeFrom.getUserId());
        personalHistoryTo.setExportStatus(changeFrom.getExportStatus());
        personalHistoryTo.setDieDate(changeFrom.getDieDate());
        personalHistoryTo.setGoDate(changeFrom.getGoDate());
        personalHistoryTo.setComeDate(changeFrom.getComeDate());
        personalHistoryTo.setIsDeleted(false);
        personalHistoryTo.setTimeCreated(changeFrom.getDateUpdate());
        personalHistoryTo.setTimeLastUpdated(changeFrom.getDateUpdate());
        personalHistoryTo.setUserCreated(1L);
        personalHistoryTo.setUserLastUpdated(1L);
        return personalHistoryTo;
    }

    public GenerateHealthyTo transfer(GeneratehealthFrom generatehealthFrom, Map<String, Integer> personalToIds) {
        GenerateHealthyTo generateHealthyTo = new GenerateHealthyTo();
        String regionIdPersonId = generatehealthFrom.getRegionId() + "_" + generatehealthFrom.getPersonalId();
        if (personalToIds.get(regionIdPersonId) != null) {
            generateHealthyTo.setPersonalId(personalToIds.get(regionIdPersonId));
        }
        generateHealthyTo.setRegionId(generatehealthFrom.getRegionId());
        generateHealthyTo.setDateUpdate(generatehealthFrom.getDateUpdate());
        generateHealthyTo.setGenerateCode(generatehealthFrom.getGenerateCode());
        generateHealthyTo.setGenDate(generatehealthFrom.getGenDate());
        generateHealthyTo.setBirthNumber(generatehealthFrom.getBirthNumber());
        generateHealthyTo.setWeight1(generatehealthFrom.getWeight1());
        generateHealthyTo.setWeight2(generatehealthFrom.getWeight2());
        generateHealthyTo.setWeight3(generatehealthFrom.getWeight3());
        generateHealthyTo.setWeight4(generatehealthFrom.getWeight4());
        generateHealthyTo.setPlaceOfBirth(generatehealthFrom.getPlaceOfBirth());
        generateHealthyTo.setPregnantCheckNumber(generatehealthFrom.getPregnantCheckNumber());
        generateHealthyTo.setPregnantAbortNoUse(generatehealthFrom.getPregnantAbortNoUse());
        generateHealthyTo.setUserId(generatehealthFrom.getUserId());
        generateHealthyTo.setExportStatus(generatehealthFrom.getExportStatus());
        generateHealthyTo.setDeliver(generatehealthFrom.getDeliver());
        generateHealthyTo.setDateSlss(generatehealthFrom.getDateSlss());
        generateHealthyTo.setResultSlss(generatehealthFrom.getResultSlss());
        generateHealthyTo.setDateSlts1(generatehealthFrom.getDateSlts1());
        generateHealthyTo.setDateSlts2(generatehealthFrom.getDateSlts2());
        generateHealthyTo.setResultSlts2(generatehealthFrom.getResultSlts2());
        generateHealthyTo.setIsDeleted(false);
        generateHealthyTo.setTimeCreated(generatehealthFrom.getDateUpdate());
        generateHealthyTo.setTimeLastUpdated(generatehealthFrom.getDateUpdate());
        generateHealthyTo.setUserCreated(1L);
        generateHealthyTo.setUserLastUpdated(1L);
        return generateHealthyTo;
    }

    public FamilyPlanningHistoryTo transfer(FamilyplanninghistoryFrom familyplanninghistoryFrom, Map<String, Integer> personalToIds) {
        FamilyPlanningHistoryTo familyPlanningHistoryTo = new FamilyPlanningHistoryTo();
        String regionIdPersonalId = familyplanninghistoryFrom.getRegionId() + "_" + familyplanninghistoryFrom.getPersonalId();
        String contraceptiveCode = familyplanninghistoryFrom.getContraceptiveCode();
        familyPlanningHistoryTo.setFphistoryId(familyplanninghistoryFrom.getFpHistoryId());
        familyPlanningHistoryTo.setRegionId(familyplanninghistoryFrom.getRegionId());
        if (personalToIds.get(regionIdPersonalId) != null)
            familyPlanningHistoryTo.setPersonalId(personalToIds.get(regionIdPersonalId));
        familyPlanningHistoryTo.setDateUpdate(familyplanninghistoryFrom.getDateUpdate());
        familyPlanningHistoryTo.setContraDate(familyplanninghistoryFrom.getContraDate());
        familyPlanningHistoryTo.setContraceptiveCode(contraceptiveCode);
        familyPlanningHistoryTo.setContraceptiveId(MappingCategory.CONTRACEPTIVE_MAPPING.get(contraceptiveCode));
        familyPlanningHistoryTo.setUserId(familyplanninghistoryFrom.getUserId());
        familyPlanningHistoryTo.setExportStatus(familyplanninghistoryFrom.getExportStatus());
        familyPlanningHistoryTo.setIsDeleted(false);
        familyPlanningHistoryTo.setTimeCreated(familyplanninghistoryFrom.getDateUpdate());
        familyPlanningHistoryTo.setTimeLastUpdated(familyplanninghistoryFrom.getDateUpdate());
        familyPlanningHistoryTo.setUserCreated(1L);
        familyPlanningHistoryTo.setUserLastUpdated(1L);
        return familyPlanningHistoryTo;
    }

    public FamilyPlanningTo transfer(FamilyplanningFrom familyplanningFrom, Map<String, Integer> personalToIds) {
        FamilyPlanningTo familyPlanningTo = new FamilyPlanningTo();
        String regionIdPersonalId = familyplanningFrom.getRegionId() + "_" + familyplanningFrom.getPersonalId();
        if (personalToIds.get(regionIdPersonalId) != null)
            familyPlanningTo.setPersonalId(personalToIds.get(regionIdPersonalId));
        familyPlanningTo.setContraDate(familyplanningFrom.getContraDate());
        familyPlanningTo.setContraceptiveCode(familyplanningFrom.getContraceptiveCode());
        familyPlanningTo.setExportStatus(familyplanningFrom.getExportStatus());
        familyPlanningTo.setRegionId(familyplanningFrom.getRegionId());
        familyPlanningTo.setDateUpdate(familyplanningFrom.getDateUpdate());
        familyPlanningTo.setUserId(familyplanningFrom.getUserId());
        familyPlanningTo.setIsDeleted(false);
        familyPlanningTo.setTimeCreated(familyplanningFrom.getDateUpdate());
        familyPlanningTo.setTimeLastUpdated(familyplanningFrom.getDateUpdate());
        familyPlanningTo.setUserCreated(1L);
        familyPlanningTo.setUserLastUpdated(1L);
        return familyPlanningTo;
    }

}
