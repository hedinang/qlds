package vn.byt.qlds.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.byt.qlds.model.Address;
import vn.byt.qlds.model.ESMessageSync;
import vn.byt.qlds.model.request.AddressRequest;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.AddressService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("address")
@Api("Address")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class AddressEndpoint {
    @Autowired
    AddressService addressService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Address")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Address.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Address.class)
    })
    public Address findOneAddress(@HeaderParam("session") String session, @PathParam("id") int id) {
        return addressService.read(session, id);
    }

    @POST
    @Path("/all")
    public List<Address> getAllAddress(@RequestBody Map<String, Object> request) throws IOException {
        return addressService.findAllAddress(request);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> request) throws IOException {
        return addressService.findPageAddress(request);
    }

    @POST
    public Address createAddress(@HeaderParam("session") String session, @RequestBody Address addressRequest) {
        return addressService.create(session, addressRequest);
    }

    @PUT
    @Path("/{id}")
    public Address updateAddress(
            @HeaderParam("session") String session,
            @PathParam("id") int id,
            @RequestBody Address addressRequest) {
        addressRequest.setId(id);
        return addressService.update(session, addressRequest);
    }

    @PUT
    @Path("/region")
    public boolean updateAddress(
            @HeaderParam("session") String session,
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("khuVucId", params.get("old_region"));
        value.put("khuVucId", params.get("new_region"));

        return addressService.update(session, where, value);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteAddress(@HeaderParam("session") String session, @PathParam("id") int id) {
        return addressService.delete(session, id);
    }

    @POST
    @Path("/search")
    public List<Address> searchFrom(@HeaderParam("session") String session, @RequestBody AddressRequest addressRequest) {
        return addressService.searchListAddress(session, addressRequest);
    }

    @GET
    @Path("/sync")
    public void syncDatabase(@HeaderParam("session") String session) {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("Address", session)));

    }
}
