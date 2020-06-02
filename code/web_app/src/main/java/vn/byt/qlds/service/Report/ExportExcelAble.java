package vn.byt.qlds.service.Report;


import vn.byt.qlds.model.report.ReportTemplate;

public interface ExportExcelAble {
    String generateExcel(ReportTemplate reportTemplate);
}
