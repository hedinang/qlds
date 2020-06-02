package vn.byt.qlds.ministry.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.ContraceptiveCategory;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.ContraceptiveCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("contraceptive-category")
@Api("ContraceptiveCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class ContraceptiveCategoryEndpoint {
    private static String session = "common";
    @Autowired
    ContraceptiveCategoryService contraceptiveCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Contraceptive")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = ContraceptiveCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = ContraceptiveCategory.class)
    })
    public ContraceptiveCategory findOneContraceptive(@PathParam("id") Integer id) throws IOException {
        return contraceptiveCategoryService.findOne(id);
    }

    @POST
    @Path("/all")
    public List getAllContraceptiveCategories(@RequestBody Map<String, Object> query) throws IOException {
        return contraceptiveCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return contraceptiveCategoryService.findPage(query);
    }

    @POST
    public ContraceptiveCategory createContraceptive(@RequestBody ContraceptiveCategory ContraceptiveRequest) {
        return contraceptiveCategoryService.create(session, ContraceptiveRequest);
    }

    @PUT
    @Path("/{id}")
    public ContraceptiveCategory updateContraceptive(@PathParam("id") Integer id, @RequestBody ContraceptiveCategory ContraceptiveRequest) {
        ContraceptiveRequest.setId(id);
        return contraceptiveCategoryService.update(session, ContraceptiveRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteContraceptive(@PathParam("id") Integer id) {
        return contraceptiveCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("ContraceptiveCategory", "common")));
    }
}
