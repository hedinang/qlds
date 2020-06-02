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
import vn.byt.qlds.model.request.PersonRequest;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.PersonService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("person")
@Api("Person")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class PersonEndpoint {
    @Autowired
    PersonService personService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Person")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Person.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Person.class)
    })
    public Person findOnePerson(@HeaderParam("session") String session, @PathParam("id") int id) {
        return personService.read(session, id);
    }

    @POST
    @Path("/all")
    public List<Person> getAllPersons(@RequestBody Map<String, Object> request) throws IOException {
        return personService.findAll(request);
    }

    @POST
    @Path("/search-page")
    public PageResponse<Person> getPage(@RequestBody Map<String, Object> esRequest) throws IOException {
        return personService.findPage(esRequest);
    }

    @POST
    public Person createPerson(@HeaderParam("session") String session, @RequestBody Person personRequest) {
        return personService.create(session, personRequest);

    }

    @PUT
    @Path("/{id}")
    public Person updatePerson(@HeaderParam("session") String session, @PathParam("id") Integer id, @RequestBody Person personRequest) {
        return personService.update(session, personRequest);
    }

    @PUT
    @Path("/region")
    public boolean updatePerson(
            @HeaderParam("session") String session,
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("regionId", params.get("old_region"));
        value.put("regionId", params.get("new_region"));

        return personService.update(session, where, value);
    }

    @DELETE
    @Path("/{id}")
    public boolean deletePerson(@HeaderParam("session") String session, @PathParam("id") int id) {
        return personService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase(@HeaderParam("session") String session) {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Person", session)));

    }

}