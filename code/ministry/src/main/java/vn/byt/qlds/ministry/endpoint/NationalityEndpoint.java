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
import vn.byt.qlds.ministry.model.Nationality;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.NationalityService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("nationality")
@Api("Nationality")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class NationalityEndpoint {
    @Autowired
    NationalityService nationalityService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Nationality")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Nationality.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Nationality.class)
    })
    public Nationality findOneNationality(@PathParam("id") Integer id) throws IOException {
        return nationalityService.findOne(id);
    }

    @POST
    public Nationality createNationality(@RequestBody Nationality nationalityRequest) {
        return nationalityService.create(session, nationalityRequest);
    }

    @POST
    @Path("/all")
    public List getAllNationality(@RequestBody Map<String, Object> query) throws IOException {
        return nationalityService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(Map<String, Object> query) throws IOException {
        return nationalityService.findPage(query);
    }

    @PUT
    @Path("/{id}")
    public Nationality updateNationality(@PathParam("id") Integer id, @RequestBody Nationality nationalityRequest) {
        nationalityRequest.setNationalityCode(id);
        return nationalityService.update(session, nationalityRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteNationality(@PathParam("id") Integer id) {
        return nationalityService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Nationality", "common")));

    }
}
