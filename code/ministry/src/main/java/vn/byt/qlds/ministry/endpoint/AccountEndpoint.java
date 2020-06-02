package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.Account;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.AccountResponse;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.AccountService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("account")
@Api("Account")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class AccountEndpoint {
    private static String session = "common";
    @Autowired
    AccountService accountService;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Account")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Account.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Account.class)
    })
    public Account findOneAccount(@PathParam("id") int id) {
        return accountService.read(session, id);
    }

    @POST
    @Path("/search-page")
    public PageResponse<Account> getPage(@RequestBody Map<String, Object> query) {
        return accountService.getPage(session, query);
    }

    @GET
    public Account getAccountByUsername(@QueryParam("username") String username) {
        return accountService.getAccountByUsername(session, username);
    }

    @POST
    @Path("/all")
    public List<Account> getAllAccount(@RequestBody Map<String, Object> query) throws IOException {
        return accountService.getAll(query);
    }

    @POST
    public Account createAccount(@RequestBody Account accountRequest) {
        return accountService.create(session, accountRequest);
    }

    @PUT
    @Path("/{id}")
    public Account updateAccount(@PathParam("id") int id, @RequestBody Account accountRequest) {
        return accountService.update(session, accountRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteAccount(@PathParam("id") int id) {
        return accountService.delete(session, id);
    }
}
