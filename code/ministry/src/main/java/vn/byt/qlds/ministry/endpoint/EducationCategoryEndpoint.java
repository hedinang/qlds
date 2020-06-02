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
import vn.byt.qlds.ministry.model.EducationCategory;

import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.EducationCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("education-category")
@Api("LevelCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class EducationCategoryEndpoint {
    @Autowired
    EducationCategoryService educationCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one LevelCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = EducationCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = EducationCategory.class)
    })
    public EducationCategory findOneLevelCategory(@PathParam("id") Integer id) throws IOException {
        return educationCategoryService.findOne(id);
    }

    @POST
    @Path("/all")
    public List getAllMaritalStatus(@RequestBody Map<String, Object> query) throws IOException {
        return educationCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {

        return educationCategoryService.findPage(query);
    }

    @POST
    public EducationCategory createLevelCategory(@RequestBody EducationCategory levelRequest) {
        return educationCategoryService.create(session, levelRequest);
    }

    @PUT
    @Path("/{id}")
    public EducationCategory updateLevelCategory(@PathParam("id") Integer id, @RequestBody EducationCategory levelRequest) {
        levelRequest.setId(id);
        return educationCategoryService.update(session, levelRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteLevelCategory(@PathParam("id") Integer id) {
        return educationCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("EducationCategory", "common")));

    }
}
