package vn.byt.qlds.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.model.LogAction;
import vn.byt.qlds.model.Person;
import vn.byt.qlds.model.request.PageRequest;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.LogActionService;

import javax.ws.rs.*;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("log-action")
@Api("LogAction")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class LogActionEndpoint {
    @Autowired
    LogActionService logActionService;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Person")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Person.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Person.class)
    })
    public LogAction findOneLogAction(@HeaderParam("session") String session, @PathParam("id") int id) {
        return logActionService.read(session, id);
    }

    @GET
    @Path("/all")
    public List<LogAction> getAllLogActions(@HeaderParam("session") String session) {
        return logActionService.getAll(session);
    }

    @POST
    @Path("/search-page")
    public PageResponse<LogAction> getPage(@HeaderParam("session") String session, @RequestBody PageRequest pageRequest) {

        PageResponse<LogAction> result = logActionService.getPage(session, pageRequest.page, pageRequest.limit, pageRequest.direction, pageRequest.property);
        return result;
    }

    @POST
    public LogAction createLogAction(@HeaderParam("session") String session, @RequestBody LogAction logAction) {
        return logActionService.create(session, logAction);
    }

    @PUT
    @Path("/{id}")
    public LogAction updateLogAction(@HeaderParam("session") String session, @PathParam("id") int id, @RequestBody LogAction logAction) {
        logAction.setId(id);
        return logActionService.update(session, logAction);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteLogAction(@HeaderParam("session") String session, @PathParam("id") int id) {
        return logActionService.delete(session, id);
    }
}
