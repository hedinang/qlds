package vn.byt.qlds.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import vn.byt.qlds.model.ESMessageSync;
import vn.byt.qlds.service.OpStatisticInfoService;

import javax.ws.rs.*;

import java.util.HashMap;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("operationstatisticinfor")
@Api("Operationstatisticinfor")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class OperationstatisticinforEndpoint {
    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    OpStatisticInfoService opStatisticInfoService;

    @PUT
    @Path("/region")
    public boolean updateHouseHold(
            @HeaderParam("session") String session,
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("regionId", params.get("old_region"));
        value.put("regionId", params.get("new_region"));

        return opStatisticInfoService.update(session, where, value);
    }

    @GET
    @Path("/sync")
    public void syncDatabase(@HeaderParam("session") String session) {
        // ban len tren queue
        ESMessageSync esMessageSync = new ESMessageSync("Operationstatisticinfor", session);
        rabbitTemplate.convertAndSend(
                "QLDS",
                "All",
                new Gson().toJson(esMessageSync));

    }

}
