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
import vn.byt.qlds.ministry.model.TransferHouseHold;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.TransferHouseHoldService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("transfer-house-hold")
@Api("TransferHouseHold")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class TransferHouseHoldEndpoint {
    @Autowired
    TransferHouseHoldService transferHouseHoldService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one PersonTranfer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = TransferHouseHold.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = TransferHouseHold.class)
    })
    public TransferHouseHold findOnePersonTranfer(@PathParam("id") int id) throws IOException {
        return transferHouseHoldService.findOne(id);
    }

    @POST
    @Path("/all")
    public List getAllPersonTranfers(@RequestBody Map<String, Object> query) throws IOException {
        return transferHouseHoldService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return transferHouseHoldService.findPage( query);
    }

    @POST
    public TransferHouseHold createTransferHouseHold(@RequestBody TransferHouseHold personTranferRequest) {
        return transferHouseHoldService.create(session, personTranferRequest);
    }

    @PUT
    @Path("/{id}")
    public TransferHouseHold updateTransferHouseHold(@PathParam("id") int id, @RequestBody TransferHouseHold personTranferRequest) {
        personTranferRequest.setId(id);
        return transferHouseHoldService.update(this.session, personTranferRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteTransferHouseHold(@PathParam("id") int id) {
        return transferHouseHoldService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("TransferHouseHold", "common")));
    }
}
