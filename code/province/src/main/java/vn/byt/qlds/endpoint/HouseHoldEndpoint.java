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
import vn.byt.qlds.model.ESMessageSync;
import vn.byt.qlds.model.HouseHold;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.HouseHoldService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("house-hold")
@Api("HouseHold")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class HouseHoldEndpoint {
    @Autowired
    HouseHoldService houseHoldService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one HouseHold")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = HouseHold.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = HouseHold.class)
    })
    public HouseHold findOneHouseHold(@HeaderParam("session") String session, @PathParam("id") int id) {
        return houseHoldService.read(session, id);
    }

    @POST
    @Path("/all")
    public List<HouseHold> getAllHouseHolds(@RequestBody Map<String, Object> request) throws IOException {
        return houseHoldService.findAll(request);
    }

    @POST
    @Path("/search-page")
    public PageResponse<HouseHold> getPage(@HeaderParam("session") String session, @RequestBody Map<String, Object> esRequest) throws IOException {
        return houseHoldService.findPage(esRequest);
    }

    @POST
    @Path("/count")
    public int count(@RequestBody Map<String, Object> esRequest) throws IOException {
        return houseHoldService.count(esRequest);
    }

    @POST
    public HouseHold createHouseHold(@HeaderParam("session") String session, @RequestBody HouseHold houseHoldRequest) {
        return houseHoldService.create(session, houseHoldRequest);
    }

    @PUT
    @Path("/{id}")
    public HouseHold updateHouseHold(@HeaderParam("session") String session, @PathParam("id") int id, @RequestBody HouseHold houseHoldRequest) {
        houseHoldRequest.setHouseHoldId(id);
        return houseHoldService.update(session, houseHoldRequest);
    }

    @PUT
    @Path("/region")
    public boolean updateHouseHold(
            @HeaderParam("session") String session,
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("regionId", params.get("old_region"));
        value.put("regionId", params.get("new_region"));

        return houseHoldService.update(session, where, value);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteHouseHold(@HeaderParam("session") String session, @PathParam("id") int id) {
        return houseHoldService.delete(session, id);
    }

    @GET
    public List<HouseHold> getHouseHoldsByAddressId(@HeaderParam("session") String session, @QueryParam("addressId") int addressId) {
        return houseHoldService.getHouseHoldByAddressId(session, addressId);

    }

    @GET
    @Path("/sync")
    public void syncDatabase(@HeaderParam("session") String session) {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("HouseHold", session)));

    }
}
