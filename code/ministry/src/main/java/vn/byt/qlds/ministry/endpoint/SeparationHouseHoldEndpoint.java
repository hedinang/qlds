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
import vn.byt.qlds.ministry.model.SeparationHouseHold;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.SeparationHouseHoldService;

import javax.ws.rs.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("separation-house-hold")
@Api("SeparationHouseHold")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class SeparationHouseHoldEndpoint {
    @Autowired
    SeparationHouseHoldService separationHouseHoldService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one SeparationHouseHold")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = SeparationHouseHold.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = SeparationHouseHold.class)
    })
    public SeparationHouseHold findOneSeparationHouseHold(@PathParam("id") int id) {
        return separationHouseHoldService.read(session, id);
    }

    @POST
    @Path("/all")
    public List getAllSeparationHouseHolds(Map<String, Object> query) throws IOException {
        return separationHouseHoldService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return separationHouseHoldService.findPage(query);
    }

    @POST
    public SeparationHouseHold createSeparationHouseHold(@RequestBody SeparationHouseHold separationHouseHoldRequest) {
        return separationHouseHoldService.create(session, separationHouseHoldRequest);
    }

    @PUT
    @Path("/{id}")
    public SeparationHouseHold updateSeparationHouseHold(@PathParam("id") int id, @RequestBody SeparationHouseHold separationHouseHoldRequest) {
        separationHouseHoldRequest.setId(id);
        return separationHouseHoldService.update(session, separationHouseHoldRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteSeparationHouseHold(@PathParam("id") int id) {
        return separationHouseHoldService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("SeparationHouseHold", "common")));
    }

}
