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
import vn.byt.qlds.ministry.model.Relationship;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.RelationshipService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("relationship")
@Api("Relationship")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class RelationshipEndpoint {
    @Autowired
    RelationshipService relationshipService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Relationship")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Relationship.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Relationship.class)
    })
    public Relationship findOneRelationship(@PathParam("id") Integer id) throws IOException {
        return relationshipService.read(id);
    }

    @POST
    @Path("/all")
    public List getAllRelationships(@RequestBody Map<String, Object> query) throws IOException {
        return relationshipService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return relationshipService.findPage(query);
    }

    @POST
    public Relationship createRelationship(@RequestBody Relationship relationshipRequest) {
        return relationshipService.create(session, relationshipRequest);
    }

    @PUT
    @Path("/{id}")
    public Relationship updateRelationship(@PathParam("id") Integer id, @RequestBody Relationship relationshipRequest) {
        relationshipRequest.setId(id);
        return relationshipService.update(session, relationshipRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteRelationship(@PathParam("id") Integer id) {
        return relationshipService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Relation", "common")));

    }

}
