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
import vn.byt.qlds.ministry.model.TechnicalCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.TechnicalCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("technical-category")
@Api("TechnicalCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class TechnicalCategoryEndpoint {
    @Autowired
    TechnicalCategoryService technicalCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one TechnicalCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = TechnicalCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = TechnicalCategory.class)
    })
    public TechnicalCategory findOneTechnicalCategory(@PathParam("id") Integer id) throws IOException {
        return technicalCategoryService.findOne(id);
    }

    @POST
    public TechnicalCategory createTechnicalCategory(@RequestBody TechnicalCategory technicalCategoryRequest) {
        return technicalCategoryService.create(session, technicalCategoryRequest);
    }

    @POST
    @Path("/all")
    public List getAllTechnicalCategory(@RequestBody Map<String, Object> query) throws IOException {
        return technicalCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        PageResponse<TechnicalCategory> result = technicalCategoryService.findPage(query);
        return result;
    }

    @PUT
    @Path("/{id}")
    public TechnicalCategory updateTechnicalCategory(@PathParam("id") Integer id, @RequestBody TechnicalCategory technicalCategoryRequest) {
        technicalCategoryRequest.setId(id);
        return technicalCategoryService.update(session, technicalCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteTechnicalCategory(@PathParam("id") Integer id) {
        return technicalCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Technical", "common")));

    }
}
