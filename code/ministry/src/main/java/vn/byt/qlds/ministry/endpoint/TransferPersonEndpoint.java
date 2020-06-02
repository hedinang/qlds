package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.TransferPerson;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.TransferPersonService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("transfer-person")
@Api("TransferPerson")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class TransferPersonEndpoint {
    @Autowired
    TransferPersonService transferPersonService;

    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one PersonTranfer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = TransferPerson.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = TransferPerson.class)
    })
    public TransferPerson findOnePersonTranfer(@PathParam("id") int id) {
        return transferPersonService.read(session, id);
    }

    @GET
    @Path("/all")
    public List<TransferPerson> getAllPersonTranfers(@RequestBody Map<String, Object> query) throws IOException {
        return transferPersonService.getAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<TransferPerson> getPage(@RequestBody Map<String, Object> query) {

        PageResponse<TransferPerson> result = transferPersonService.getPage(session, query);
        return result;
    }

    @POST
    public TransferPerson createTransferPerson(@RequestBody TransferPerson personTranferRequest) {
        return transferPersonService.create(session, personTranferRequest);
    }

    @PUT
    @Path("/{id}")
    public TransferPerson updateTransferPerson(@PathParam("id") int id, @RequestBody TransferPerson personTranferRequest) {
        personTranferRequest.setId(id);
        return transferPersonService.update(this.session, personTranferRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteTransferPerson(@PathParam("id") int id) {
        return transferPersonService.delete(session, id);
    }
}
