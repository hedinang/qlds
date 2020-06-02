package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.AreaCategory;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.AreaCategoryService;
import com.google.gson.Gson;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("area-category")
@Api("AreaCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class AreaCategoryEndpoint {

    @Autowired
    AreaCategoryService areaCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one AreaCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = AreaCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = AreaCategory.class)
    })
    public AreaCategory findOneAreaCategory(@PathParam("id") int id) {
        return areaCategoryService.read(session, id);
    }

    @POST
    @Path("/all")
    public List getAll(@RequestBody Map<String, Object> query) throws IOException {
        return areaCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return areaCategoryService.findPage(query);
    }

    @POST
    public AreaCategory createAreaCategory(@RequestBody AreaCategory AreaCategoryRequest) {
        return areaCategoryService.create(session, AreaCategoryRequest);
    }

    @PUT
    @Path("/{id}")
    public AreaCategory updateAreaCategory(@PathParam("id") int id, @RequestBody AreaCategory areaCategoryRequest) {
        areaCategoryRequest.setId(id);
        return areaCategoryService.update(session, areaCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteAreaCategory(@PathParam("id") int id) {
        return areaCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("AreaCategory", "common")));
    }
}
