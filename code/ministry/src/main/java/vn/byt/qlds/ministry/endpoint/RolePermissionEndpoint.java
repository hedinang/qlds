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
import vn.byt.qlds.ministry.model.RolePermission;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.RolePermissionService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("role-permission")
@Api("RolePermission")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class RolePermissionEndpoint {
    @Autowired
    RolePermissionService rolePermissionService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one RolePermission")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = RolePermission.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = RolePermission.class)
    })
    public RolePermission findOneRolePermission(@PathParam("id") int id) {
        return rolePermissionService.read(session, id);
    }

    @GET
    @Path("/all")
    public List<RolePermission> getAllRolePermissions(@RequestBody Map<String, Object> query) throws IOException {
        return rolePermissionService.getAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<RolePermission> getPage(@RequestBody Map<String, Object> query) {

        PageResponse<RolePermission> result = rolePermissionService.getPage(session, query);
        return result;
    }

    @GET
    public List<RolePermission> getRolePermissionByRoleId(@QueryParam("roleId") int roleId) {
        List<RolePermission> result = rolePermissionService.getPermisionByRoleId("common", roleId);
        return result;
    }

    @POST
    public RolePermission createRolePermission(@RequestBody RolePermission rolePermissionRequest) {
        return rolePermissionService.create(session, rolePermissionRequest);
    }

    @PUT
    @Path("/{id}")
    public RolePermission updateRolePermission(@PathParam("id") int id, @RequestBody RolePermission rolePermissionRequest) {
        rolePermissionRequest.setId(id);
        return rolePermissionService.update(session, rolePermissionRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteRolePermission(@PathParam("id") int id) {
        return rolePermissionService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("RolePermission", "common")));

    }
}
