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
import vn.byt.qlds.ministry.model.ResidenceStatus;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.ResidenceStatusService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("residence-status")
@Api("ResidenceStatus")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class ResidenceStatusEndpoint {
    @Autowired
    ResidenceStatusService residenceStatusService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one ResidenceStatus")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = ResidenceStatus.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = ResidenceStatus.class)
    })
    public ResidenceStatus findOneResidenceStatus(@PathParam("id") Integer id) throws IOException {
        return residenceStatusService.read(id);
    }

    @POST
    public ResidenceStatus createResidenceStatus(@RequestBody ResidenceStatus residenceStatusRequest) {
        return residenceStatusService.create(session, residenceStatusRequest);
    }

    @POST
    @Path("/all")
    public List getAllResidenceStatuses(@RequestBody Map<String, Object> query) throws IOException {
        return residenceStatusService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return residenceStatusService.findPage(query);
    }

    @PUT
    @Path("/{id}")
    public ResidenceStatus updateResidenceStatus(@PathParam("id") Integer id, @RequestBody ResidenceStatus residenceStatusRequest) {
        residenceStatusRequest.setResidenceCode(id);
        return residenceStatusService.update(session, residenceStatusRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteResidenceStatus(@PathParam("id") Integer id) {
        return residenceStatusService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Residence", "common")));

    }
}
