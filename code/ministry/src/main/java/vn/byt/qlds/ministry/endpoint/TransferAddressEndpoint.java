package vn.byt.qlds.ministry.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.model.TransferAddress;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.TransferAddressService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("transfer-address")
@Api("TransferAddress")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class TransferAddressEndpoint {
    private final String session = "common";
    @Autowired
    TransferAddressService transferAddressService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @POST
    public TransferAddress createTransferPerson(@RequestBody TransferAddress transferAddress) {
        return transferAddressService.create(this.session, transferAddress);
    }

    @PUT
    @Path("/{id}")
    public TransferAddress updateTransferRegion(@PathParam("id") int id, @RequestBody TransferAddress transferAddress) {
        return transferAddressService.update(this.session, transferAddress);
    }

    @GET
    @Path("/{id}")
    public TransferAddress findOneById(@PathParam("id") int id) throws IOException {
        return transferAddressService.findOne(id);
    }

    @POST
    @Path("/all")
    public List getAll(@RequestParam Map<String, Object> query) throws IOException {
        return transferAddressService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse searchPage(@RequestParam Map<String, Object> query) throws IOException {
        return transferAddressService.findPage(query);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("TransferAddress", "common")));
    }
}
