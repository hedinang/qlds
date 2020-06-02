package vn.byt.qlds.ministry.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.DeadCategory;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.DeadCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("dead-category")
@Api("DeadCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class DeadCategoryEndpoint {
    @Autowired
    DeadCategoryService deadCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one DeadCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = DeadCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = DeadCategory.class)
    })
    public DeadCategory findOneDead(@PathParam("id") Integer id) throws IOException {
        return deadCategoryService.findOne(id);
    }

    @POST
    @Path("/all")
    public List getAllDeadCategories(@RequestBody Map<String, Object> query) throws IOException {
        return deadCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return deadCategoryService.findPage(query);
    }

    @POST
    public DeadCategory createDead(@RequestBody DeadCategory deadCategoryRequest) {
        return deadCategoryService.create(session, deadCategoryRequest);
    }

    @PUT
    @Path("/{id}")
    public DeadCategory updateDead(@PathParam("id") Integer
                                           id, @RequestBody DeadCategory deadCategoryRequest) {
        deadCategoryRequest.setId(id);
        return deadCategoryService.update(session, deadCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteDead(@PathParam("id") Integer id) {
        return deadCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("DeadCategory", "common")));

    }
}
