package vn.byt.qlds.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.model.Collaborator;
import vn.byt.qlds.model.ESMessageSync;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.CollaboratorService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("collaborator")
@Api("Collaborator")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class CollaboratorEndpoint {
    @Autowired
    CollaboratorService collaboratorService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Collaborator")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Collaborator.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Collaborator.class)
    })
    public Collaborator findOneCollaborator(@HeaderParam("session") String session, @PathParam("id") Integer id) throws IOException {
        return collaboratorService.read(session, id);
    }

    @POST
    @Path("/all")
    public List getAll(@RequestBody Map<String, Object> query) throws IOException {
        return collaboratorService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {

        return collaboratorService.findPage(query);
    }

    @POST
    public Collaborator createCollaborator(@HeaderParam("session") String session, @RequestBody Collaborator collaborator) {
        return collaboratorService.create(session, collaborator);
    }

    @PUT
    @Path("/{id}")
    public Collaborator updateCollaborator(@HeaderParam("session") String session, @PathParam("id") int id, @RequestBody Collaborator collaboratorRequest) {
        collaboratorRequest.setId(id);
        return collaboratorService.update(session, collaboratorRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteCollaborator(@HeaderParam("session") String session, @PathParam("id") int id) {
        return collaboratorService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase(@HeaderParam("session") String session) {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Collaborator", session)));

    }
}
