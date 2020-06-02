package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.ReportDesign;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.ReportDesignService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("report-design")
@Api("ReportDesign")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class ReportDesignEndpoint {
    @Autowired
    ReportDesignService reportDesignService;

    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one ReportDesign")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = ReportDesign.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = ReportDesign.class)
    })
    public ReportDesign findOneReportDesign(@PathParam("id") String id) {
        return reportDesignService.read(session, id);
    }

    @GET
    @Path("/all")
    public List<ReportDesign> getAllReportDesigns(@RequestBody Map<String, Object> query) throws IOException {
        return reportDesignService.getAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<ReportDesign> getPage(@RequestBody Map<String, Object> query) {

        PageResponse<ReportDesign> result = reportDesignService.getPage(session, query);
        return result;
    }

    @POST
    public ReportDesign createReportDesign(@RequestBody ReportDesign reportDesignRequest) {
        return reportDesignService.create(session, reportDesignRequest);
    }

    @PUT
    @Path("/{id}")
    public ReportDesign updateReportDesign(@PathParam("id") String id, @RequestBody ReportDesign reportDesignRequest) {
        reportDesignRequest.setRcId(id);
        return reportDesignService.update(session, reportDesignRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteReportDesign(@PathParam("id") String id) {
        return reportDesignService.delete(session, id);
    }
}
