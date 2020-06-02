package vn.byt.qlds.endpoints;

import vn.byt.qlds.client.ReportClient;
import vn.byt.qlds.core.ReportServiceFactory;
import vn.byt.qlds.exception.BadRequestException;
import vn.byt.qlds.model.report.ReportRequest;
import vn.byt.qlds.model.report.ReportTemplate;
import vn.byt.qlds.service.Report.ReportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;


@RestController
@RequestMapping(value = "/report")
public class ReportEndpoint {
    @Autowired
    ReportClient reportClient;
    @Autowired
    ReportServiceFactory reportServiceFactory;

    @PostMapping("/export")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','REPORT')")
    public ReportTemplate exportReport(@RequestBody ReportRequest request) throws ParseException, JsonProcessingException {
        ReportService reportService = reportServiceFactory.createReportService(request.rcId);
        return reportService.generateReport(request);
    }

    @PostMapping("/dcx")
    @PreAuthorize("hasAnyAuthority('ADMIN','REPORT_DCX')")
    public ReportTemplate exportReportDCX(@RequestBody ReportRequest request) throws ParseException, JsonProcessingException {
        if (!request.rcId.startsWith("DCX")) throw new BadRequestException("Bad request!");
        ReportService reportService = reportServiceFactory.createReportService(request.rcId);
        return reportService.generateReport(request);
    }

    @PostMapping("/dsx")
    @PreAuthorize("hasAnyAuthority('ADMIN','REPORT_DSX')")
    public ReportTemplate exportReportDSX(@RequestBody ReportRequest request) throws ParseException, JsonProcessingException {
        if (!request.rcId.startsWith("DSX")) throw new BadRequestException("Bad request!");
        ReportService reportService = reportServiceFactory.createReportService(request.rcId);
        return reportService.generateReport(request);
    }

    @PostMapping("/dch")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','REPORT_DCH')")
    public ReportTemplate exportReportDCH(@RequestBody ReportRequest request) throws ParseException, JsonProcessingException {
        if (!request.rcId.startsWith("DCH")) throw new BadRequestException("Bad request!");
        ReportService reportService = reportServiceFactory.createReportService(request.rcId);
        return reportService.generateReport(request);
    }

    @PostMapping("/dsh")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','REPORT_DSH')")
    public ReportTemplate exportReportDSH(@RequestBody ReportRequest request) throws ParseException, JsonProcessingException {
        if (!request.rcId.startsWith("DSH")) throw new BadRequestException("Bad request!");
        ReportService reportService = reportServiceFactory.createReportService(request.rcId);
        return reportService.generateReport(request);
    }

    @PostMapping("/dst")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ADMIN','REPORT_DST')")
    public ReportTemplate exportReportDST(@RequestBody ReportRequest request) throws ParseException, JsonProcessingException {
        if (!request.rcId.startsWith("DST")) throw new BadRequestException("Bad request!");
        ReportService reportService = reportServiceFactory.createReportService(request.rcId);
        return reportService.generateReport(request);
    }
}
