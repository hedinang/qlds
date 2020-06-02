package vn.byt.qlds.ministry.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.model.ReasonChange;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.ReasonChangeService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("reason-change")
@Api("ReasonChange")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class ReasonChangeEndpoint {
    @Autowired
    ReasonChangeService reasonChangeService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Nationality")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = ReasonChange.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = ReasonChange.class)
    })
    public ReasonChange findOneReasonChange(@PathParam("id") Integer id) throws IOException {
        return reasonChangeService.read(id);
    }

    @POST
    public ReasonChange createReasonChange(@RequestBody ReasonChange reasonChangeRequest) {
        return reasonChangeService.create(session, reasonChangeRequest);
    }

    @POST
    @Path("/all")
    public List getAllReasonChanges(@RequestBody Map<String, Object> query) throws IOException {
        return reasonChangeService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        PageResponse result = reasonChangeService.findPage(query);
        return result;
    }

    @PUT
    @Path("/{id}")
    public ReasonChange updateReasonChange(@PathParam("id") Integer id, @RequestBody ReasonChange reasonChangeRequest) {
        reasonChangeRequest.setId(id);
        return reasonChangeService.update(session, reasonChangeRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteReasonChange(@PathParam("id") String id) {
        return reasonChangeService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("ReasonChange", "common")));

    }
}
