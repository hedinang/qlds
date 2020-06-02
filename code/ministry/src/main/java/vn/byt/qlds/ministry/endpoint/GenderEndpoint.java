package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.model.Gender;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.GenderService;
import com.google.gson.Gson;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("gender")
@Api("Gender")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class GenderEndpoint {
    @Autowired
    GenderService genderService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Gender")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Gender.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Gender.class)
    })
    public Gender findOneGender(@PathParam("id") int id) throws IOException {
        return genderService.read(id);
    }

    @POST
    @Path("/all")
    public List<Gender> getAllGender(@RequestBody Map<String, Object> query) throws IOException {
        return genderService.getAll(query);
    }

    @POST
    @Path("/count")
    public Integer count(@RequestBody Map<String, Object> query) throws IOException {
        return genderService.count(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<Gender> getPage(@RequestBody Map<String, Object> query) throws IOException {
        return genderService.getPage(query);
    }

    @POST
    public Gender createGender(@RequestBody Gender genderRequest) {
        return genderService.create(session, genderRequest);
    }

    @PUT
    @Path("/{id}")
    public Gender updateGender(@PathParam("id") int id, @RequestBody Gender genderRequest) {
        genderRequest.setId(id);
        return genderService.update(session, genderRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteGender(@PathParam("id") int id) {
        return genderService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Gender", "common")));

    }

}
