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
import vn.byt.qlds.ministry.model.MaritalStatus;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.MaritalStatusService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("marital-status")
@Api("MaritalStatus")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class MaritalStatusEndpoint {
    @Autowired
    MaritalStatusService maritalStatusService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Contraceptive")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = MaritalStatus.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = MaritalStatus.class)
    })
    public MaritalStatus findOneMaritalStatus(@PathParam("id") int id) throws IOException {
        return maritalStatusService.findOne(id);
    }

    @POST
    @Path("/all")
    public List getAllMaritalStatus(@RequestBody Map<String, Object> query) throws IOException {
        return maritalStatusService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(Map<String, Object> query) throws IOException {
        return maritalStatusService.findPage(query);
    }

    @POST
    public MaritalStatus createMaritalStatus(@RequestBody MaritalStatus maritalStatusRequest) {
        return maritalStatusService.create(session, maritalStatusRequest);
    }

    @PUT
    @Path("/{id}")
    public MaritalStatus updateMaritalStatus(@PathParam("id") int id,
                                             @RequestBody MaritalStatus maritalStatusRequest) {
        maritalStatusRequest.setMaritalCode(id);
        return maritalStatusService.update(session, maritalStatusRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteMaritalStatus(@PathParam("id") int id) {
        return maritalStatusService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Marital", "common")));

    }
}
