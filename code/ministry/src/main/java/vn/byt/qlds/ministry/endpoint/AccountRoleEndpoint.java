package vn.byt.qlds.ministry.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.AccountRole;
import vn.byt.qlds.ministry.model.AreaCategory;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.service.AccountRoleService;

import javax.ws.rs.*;

import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("account-role")
@Api("AccountRole")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class AccountRoleEndpoint {
    private static String session = "common";
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    AccountRoleService accountRoleService;

    @GET
    @Path("/{id}")
    public AccountRole findOne(@PathParam("id") int id) {
        return accountRoleService.read(session, id);
    }

    @POST
    public AccountRole createAccountRole(@RequestBody AccountRole accountRoleRequest) {
        AccountRole result = accountRoleService.create(session, accountRoleRequest);
        return result;
    }

    @DELETE
    public void deleteAccountRole(@QueryParam("userId") Integer userId) {
        accountRoleService.deleteAccountRole(session, userId);
    }

    @PUT
    @Path("/{id}")
    public void updateAccountRole(@PathParam("id") Integer id, AccountRole accountRole) {
        accountRoleService.update(session, accountRole);
    }

    @GET
    public List<AccountRole> getAccountRoleByAccountId(@QueryParam("accountId") int accountId) {
        return accountRoleService.getRoleByAccountID(session, accountId);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("AccountRole", "common")));
    }

}
