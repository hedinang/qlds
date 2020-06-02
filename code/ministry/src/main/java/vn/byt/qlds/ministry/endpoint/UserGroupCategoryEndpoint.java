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
import vn.byt.qlds.ministry.model.UserGroupCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.UserGroupCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("user-group-category")
@Api("UserGroupCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class UserGroupCategoryEndpoint {
    @Autowired

    UserGroupCategoryService userGroupCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";


    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one UserGroupCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = UserGroupCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = UserGroupCategory.class)
    })
    public UserGroupCategory findOneUserGroupCategory(@PathParam("id") Integer id) throws IOException {
        return userGroupCategoryService.findOne(id);
    }

    @POST
    @Path("/all")
    public List getAllUserGroupCategories(@RequestBody Map<String, Object> query) throws IOException {
        return userGroupCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return userGroupCategoryService.findPage(query);
    }

    @POST
    public UserGroupCategory createUserGroupCategory(@RequestBody UserGroupCategory userGroupCategoryRequest) {
        return userGroupCategoryService.create(session, userGroupCategoryRequest);
    }

    @PUT
    @Path("/{id}")
    public UserGroupCategory updateUserGroupCategory(@PathParam("id") int id, @RequestBody UserGroupCategory userGroupCategoryRequest) {
        userGroupCategoryRequest.setId(id);
        return userGroupCategoryService.update(session, userGroupCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteUserGroupCategory(@PathParam("id") int id) {
        return userGroupCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("UserGroupCategory", "common")));

    }

}
