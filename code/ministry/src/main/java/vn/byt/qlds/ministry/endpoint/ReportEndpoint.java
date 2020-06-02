package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.Report;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.ReportService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("report")
@Api("Report")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class ReportEndpoint {
    @Autowired
    ReportService reportService;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Report")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Report.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Report.class)
    })
    public Report findOneReport(@PathParam("id") String id) {
        return reportService.read(session, id);
    }

    @POST
    public Report createReport(@RequestBody Report reportRequest) {
        return reportService.create(session, reportRequest);
    }

    @GET
    @Path("/all")
    public List<Report> getAllReports(@RequestBody Map<String, Object> query) throws IOException {
        return reportService.getAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<Report> getPage(@RequestBody Map<String, Object> query) {

        PageResponse<Report> result = reportService.getPage(session, query);
        return result;
    }

    @PUT
    @Path("/{id}")
    public Report updateReport(@PathParam("id") String id, @RequestBody Report reportRequest) {
        reportRequest.setRcId(id);
        return reportService.update(session, reportRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteReport(@PathParam("id") String id) {
        return reportService.delete(session, id);
    }
}
