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
import vn.byt.qlds.ministry.model.NationCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.NationCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("nation-category")
@Api("NationCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class NationCategoryEndpoint {
    @Autowired
    NationCategoryService nationCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Nationality")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = NationCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = NationCategory.class)
    })
    public NationCategory findOneNationCategory(@PathParam("id") Integer id) throws IOException {
        return nationCategoryService.findOne(id);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(Map<String, Object> query) throws IOException {
        return nationCategoryService.findPage(query);
    }

    @POST
    public NationCategory createNationCategory(@RequestBody NationCategory nationCategoryRequest) {
        return nationCategoryService.create(session, nationCategoryRequest);
    }

    @POST
    @Path("/all")
    public List getAllReasonChanges(@RequestBody Map<String, Object> query) throws IOException {
        return nationCategoryService.findAll(query);
    }

    @PUT
    @Path("/{id}")
    public NationCategory updateNationCategory(@PathParam("id") Integer id, @RequestBody NationCategory nationCategoryRequest) {
        nationCategoryRequest.setId(id);
        return nationCategoryService.update(session, nationCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteNationCategory(@PathParam("id") Integer id) {
        return nationCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("NationCategory", "common")));

    }


}
