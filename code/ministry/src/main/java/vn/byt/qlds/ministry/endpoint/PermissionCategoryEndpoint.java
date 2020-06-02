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
import vn.byt.qlds.ministry.model.PermissionCategory;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.PermissionCategoryService;

import javax.ws.rs.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("permission-category")
@Api("PermissionCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class PermissionCategoryEndpoint {
    @Autowired
    PermissionCategoryService permissionCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one PermissionCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = PermissionCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = PermissionCategory.class)
    })
    public PermissionCategory findOnePermissionCategory(@PathParam("id") int id) {
        return permissionCategoryService.read(session, id);
    }

    @POST
    @Path("/all")
    public List getAllPermissionCategorys(@RequestBody Map<String, Object> query) throws IOException {
        return permissionCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return permissionCategoryService.findPage(query);
    }

    @POST
    public PermissionCategory createPermissionCategory(@RequestBody PermissionCategory permissionCategoryRequest) {
        return permissionCategoryService.create(session, permissionCategoryRequest);
    }

    @PUT
    @Path("/{id}")
    public PermissionCategory updatePermissionCategory(@PathParam("id") int id, @RequestBody PermissionCategory permissionCategoryRequest) {
        permissionCategoryRequest.setId(id);
        return permissionCategoryService.update(session, permissionCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deletePermissionCategory(@PathParam("id") int id) {
        return permissionCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("PermissionCategory", "common")));

    }

}
