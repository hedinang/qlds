package vn.byt.qlds.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.byt.qlds.model.ESMessageSync;
import vn.byt.qlds.model.PersonHealthy;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.PersonHealthyService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("person-healthy")
@Api("PersonHealthy")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class PersonHealthyEndpoint {
    @Autowired
    PersonHealthyService personHealthyService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one PersonHealthy")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = PersonHealthy.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = PersonHealthy.class)
    })
    public PersonHealthy findOnePersonHealthy(@HeaderParam("session") String session, @PathParam("id") int id) {
        return personHealthyService.read(session, id);
    }

    @POST
    @Path("/all")
    public List<PersonHealthy> getAllPersonHealthys(@RequestBody Map<String, Object> request) throws IOException {
        return personHealthyService.findAll(request);
    }

    @POST
    @Path("/search-page")
    public PageResponse<PersonHealthy> getPage(@HeaderParam("session") String session, @RequestBody Map<String, Object> esRequest) throws IOException {
        return personHealthyService.findPage(esRequest);

    }

    @POST
    public PersonHealthy createPersonHealthy(@HeaderParam("session") String session, @RequestBody PersonHealthy personHealthyRequest) {
        return personHealthyService.create(session, personHealthyRequest);
    }

    @PUT
    @Path("/{id}")
    public PersonHealthy updatePersonHealthy(@HeaderParam("session") String session, @PathParam("id") int id, @RequestBody PersonHealthy personHealthyRequest) {
        personHealthyRequest.setId(id);
        return personHealthyService.update(session, personHealthyRequest);
    }

    @PUT
    @Path("/region")
    public boolean updatePersonHealthy(
            @HeaderParam("session") String session,
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("regionId", params.get("old_region"));
        value.put("regionId", params.get("new_region"));

        return personHealthyService.update(session, where, value);
    }

    @DELETE
    @Path("/{id}")
    public boolean deletePersonHealthy(@HeaderParam("session") String session, @PathParam("id") int id) {
        return personHealthyService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase(@HeaderParam("session") String session) {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("PersonHealthy", session)));

    }

}
