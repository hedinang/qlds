package vn.byt.qlds.core;


import vn.byt.qlds.core.base.ExportResponse;
import vn.byt.qlds.model.account.Account;
import vn.byt.qlds.model.account.AccountResponse;
import vn.byt.qlds.model.area.AreaCategory;
import vn.byt.qlds.model.contraceptive.Contraceptive;
import vn.byt.qlds.model.dead.DeadCategory;
import vn.byt.qlds.model.education.EducationCategory;
import vn.byt.qlds.model.ethnic.NationCategory;
import vn.byt.qlds.model.group.UserGroupCategory;
import vn.byt.qlds.model.marial.MaritalStatus;
import vn.byt.qlds.model.nationlity.Nationality;
import vn.byt.qlds.model.reason_change.ReasonChange;
import vn.byt.qlds.model.relationship.Relationship;
import vn.byt.qlds.model.residence.ResidenceStatus;
import vn.byt.qlds.model.technical.TechnicalCategory;
import vn.byt.qlds.model.unit.UnitCategory;
import vn.byt.qlds.model.unit.UnitLevelCategory;
import vn.byt.qlds.service.UnitCategoryService;
import com.gembox.spreadsheet.ExcelFile;
import com.gembox.spreadsheet.ExcelRow;
import com.gembox.spreadsheet.ExcelWorksheet;
import com.gembox.spreadsheet.SpreadsheetInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExportExcelService {


    @Value("${path_export_tmp}")
    String PATH_EXPORT_TMP;
    @Value("${path_export}")
    String PATH_EXPORT;
    @Value("${store_export}")
    String STORE_EXPORT;
    @Autowired
    UnitCategoryService unitCategoryService;

    public ExportExcelService() {
        SpreadsheetInfo.setLicense("FREE-LIMITED-KEY");
    }

    /*Danh mục */


    public ExportResponse exportAccount(List<AccountResponse> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_account.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            AccountResponse accountResponse = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            Account account = accountResponse.account;

            String status = account.getIsActive() ? "Sử dụng" : "Không sử dụng";

            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(account.getUserName());
            currentRow.getCell(2).setValue(account.getNameDisplay());
            currentRow.getCell(3).setValue(account.getEmail());
            currentRow.getCell(4).setValue(accountResponse.unitName);
            currentRow.getCell(5).setValue(accountResponse.getLevel());
            currentRow.getCell(6).setValue(accountResponse.userGroupResponse.get(0).userGroupCategory.getGroupName());
            currentRow.getCell(7).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_account.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportArea(List<AreaCategory> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_area.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            AreaCategory areaCategory = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = areaCategory.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(areaCategory.getAreaName());
            currentRow.getCell(2).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_area.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportContraceptive(List<Contraceptive> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_contraceptive.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            Contraceptive contraceptive = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = contraceptive.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(contraceptive.getCode());
            currentRow.getCell(2).setValue(contraceptive.getName());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_contraceptive.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportDead(List<DeadCategory> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_dead.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            DeadCategory deadCategory = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = deadCategory.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(deadCategory.getCode());
            currentRow.getCell(2).setValue(deadCategory.getName());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_dead.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportEducation(List<EducationCategory> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_education_category.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            EducationCategory educationCategory = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = educationCategory.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(educationCategory.getCode());
            currentRow.getCell(2).setValue(educationCategory.getName());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_education_category.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportEthnic(List<NationCategory> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_ethnic.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            NationCategory nationCategory = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = nationCategory.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(nationCategory.getCode());
            currentRow.getCell(2).setValue(nationCategory.getName());
            currentRow.getCell(3).setValue(nationCategory.getIsMinority());
            currentRow.getCell(4).setValue(nationCategory.getNotes());
            currentRow.getCell(5).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_ethnic.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportMaritalStatus(List<MaritalStatus> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_marital_status.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            MaritalStatus maritalStatus = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = maritalStatus.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(maritalStatus.getMaritalCode());
            currentRow.getCell(2).setValue(maritalStatus.getMaritalName());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_marital_status.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportNationality(List<Nationality> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_nationality.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            Nationality nationality = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = nationality.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(nationality.getNationalityCode());
            currentRow.getCell(2).setValue(nationality.getCountryName());
            currentRow.getCell(3).setValue(nationality.getNationalityName());
            currentRow.getCell(4).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_nationality.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportReasonChange(List<ReasonChange> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_reason_change.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            ReasonChange reasonChange = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = reasonChange.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(reasonChange.getChangeTypeCode());
            currentRow.getCell(2).setValue(reasonChange.getChangeTypeName());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_reason_change.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportRelationship(List<Relationship> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_relationship.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            Relationship relationship = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = relationship.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(relationship.getRelationCode());
            currentRow.getCell(2).setValue(relationship.getRelationName());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_relationship.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportResidence(List<ResidenceStatus> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_residence.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            ResidenceStatus residenceStatus = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = residenceStatus.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(residenceStatus.getResidenceName());
            currentRow.getCell(2).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_residence.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportTechnical(List<TechnicalCategory> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_technical_category.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            TechnicalCategory technicalCategory = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = technicalCategory.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(technicalCategory.getTechnicalCode());
            currentRow.getCell(2).setValue(technicalCategory.getTechnicalName());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_technical_category.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportUnit(List<UnitCategory> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_unit_category.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            UnitCategory unitCategory = data.get(i);
            UnitCategory parent = unitCategoryService.getProvinceByRegionCode(unitCategory.getCode());
            ExcelRow currentRow = worksheet.getRow(2 + i);
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(unitCategory.getCode());
            currentRow.getCell(2).setValue(unitCategory.getName());
            currentRow.getCell(3).setValue(unitCategory.getLevels());
            currentRow.getCell(4).setValue(unitCategory.getZone());
            currentRow.getCell(5).setValue(unitCategory.getArea());
            currentRow.getCell(6).setValue(unitCategory.getNote());
            currentRow.getCell(7).setValue(parent.getName());
        }
        String nameExport = System.currentTimeMillis() + "-export_unit_category.xlsx";
        workbook.save(PATH_EXPORT + nameExport);

        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportUnitLevel(List<UnitLevelCategory> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_unit_level_category.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            UnitLevelCategory unitLevel = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);

            String status = unitLevel.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(unitLevel.getLevelName());
            currentRow.getCell(2).setValue(unitLevel.getNote());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-export_unit_level_category.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }

    public ExportResponse exportUserGroup(List<UserGroupCategory> data) throws IOException {
        ExcelFile workbook = ExcelFile.load(PATH_EXPORT_TMP + "tmp_export_user_group.xlsx");
        ExcelWorksheet worksheet = workbook.getWorksheet(0);
        int counter = data.size();
        for (int i = 0; i < counter; i++) {
            UserGroupCategory groupCategory = data.get(i);
            ExcelRow currentRow = worksheet.getRow(2 + i);
            String status = groupCategory.getIsActive() ? "Sử dụng" : "Không sử dụng";
            currentRow.getCell(0).setValue(i + 1);
            currentRow.getCell(1).setValue(groupCategory.getGroupName());
            currentRow.getCell(2).setValue(groupCategory.getNote());
            currentRow.getCell(3).setValue(status);
        }
        String nameExport = System.currentTimeMillis() + "-_export_user_group.xlsx";
        workbook.save(PATH_EXPORT + nameExport);
        ExportResponse response = new ExportResponse();
        response.fileName = nameExport;
        response.urlFile = STORE_EXPORT + nameExport;
        return response;
    }
}
