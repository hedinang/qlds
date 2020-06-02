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
import vn.byt.qlds.model.Person;
import vn.byt.qlds.model.PersonHistory;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.PersonHistoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("person-history")
@Api("PersonHistory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class PersonHistoryEndpoint {
    @Autowired
    PersonHistoryService personHistoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Person")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Person.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Person.class)
    })
    public PersonHistory findOnePersonHistory(@HeaderParam("session") String session, @PathParam("id") int id) {
        return personHistoryService.read(session, id);
    }

    @POST
    @Path("/all")
    public List<PersonHistory> getAllPersonHistories(@RequestBody Map<String, Object> request) throws IOException {
        return personHistoryService.findAll(request);
    }

    @POST
    @Path("/search-page")
    public PageResponse<PersonHistory> getPage(@HeaderParam("session") String session, @RequestBody Map<String, Object> esRequest) throws IOException {

        return personHistoryService.findPage(esRequest);
    }

    @POST
    public PersonHistory createPersonHistory(@HeaderParam("session") String session, @RequestBody PersonHistory personHistory) {
        return personHistoryService.create(session, personHistory);
    }

    @PUT
    @Path("/{id}")
    public PersonHistory updatePersonHistory(@HeaderParam("session") String session, @PathParam("id") int id, @RequestBody PersonHistory personHistory) {
        personHistory.setPersonalId(id);
        return personHistoryService.update(session, personHistory);
    }

    @PUT
    @Path("/region")
    public boolean updatePersonHistory(
            @HeaderParam("session") String session,
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("regionId", params.get("old_region"));
        value.put("regionId", params.get("new_region"));

        return personHistoryService.update(session, where, value);
    }

    @DELETE
    @Path("/{id}")
    public boolean deletePersonHistory(@HeaderParam("session") String session, @PathParam("id") int id) {
        return personHistoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase(@HeaderParam("session") String session) {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("PersonHistory", session)));

    }
}
