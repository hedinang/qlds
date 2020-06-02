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
import vn.byt.qlds.model.FamilyPlanningHistory;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.FamilyPlanningHistoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("family-planning-history")
@Api("FamilyPlanningHistory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class FamilyPlanningHistoryEndpoint {
    @Autowired
    FamilyPlanningHistoryService familyPlanningHistoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one FamilyPlanningHistory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = FamilyPlanningHistory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = FamilyPlanningHistory.class)
    })
    public FamilyPlanningHistory findOneFamilyPlanningHistory(@HeaderParam("session") String session, @PathParam("id") int id) {
        return familyPlanningHistoryService.read(session, id);
    }

    @POST
    @Path("/all")
    public List<FamilyPlanningHistory> getAllFamilyPlanningHistory(@RequestBody Map<String, Object> request) throws IOException {
        return familyPlanningHistoryService.findAll(request);
    }


    @POST
    @Path("/search-page")
    public PageResponse<FamilyPlanningHistory> getPage(@HeaderParam("session") String session, @RequestBody Map<String, Object> esRequest) throws IOException {

        return familyPlanningHistoryService.findPage(esRequest);

    }

    @POST
    public FamilyPlanningHistory createFamilyPlanningHistory(@HeaderParam("session") String session, @RequestBody FamilyPlanningHistory familyPlanningHistoryRequest) {
        return familyPlanningHistoryService.create(session, familyPlanningHistoryRequest);
    }

    @PUT
    @Path("/{id}")
    public FamilyPlanningHistory updateFamilyPlanningHistory(@HeaderParam("session") String session, @PathParam("id") int id, @RequestBody FamilyPlanningHistory familyPlanningHistoryRequest) {
        familyPlanningHistoryRequest.setFpHistoryId(id);
        return familyPlanningHistoryService.update(session, familyPlanningHistoryRequest);
    }

    @PUT
    @Path("/region")
    public boolean updateFamilyPlanningHistory(
            @HeaderParam("session") String session,
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("regionId", params.get("old_region"));
        value.put("regionId", params.get("new_region"));

        return familyPlanningHistoryService.update(session, where, value);
    }


    @DELETE
    @Path("/{id}")
    public boolean deleteFamilyPlanningHistory(@HeaderParam("session") String session, @PathParam("id") int id) {
        return familyPlanningHistoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase(@HeaderParam("session") String session) {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("FamilyPlanningHistory", session)));

    }

    @GET
    @Path("/search")
    public List<FamilyPlanningHistory> getAllFamilyPlanningHistorysByPerson(@HeaderParam("session") String session, @QueryParam("personId") int personId) {
        return familyPlanningHistoryService.getAllByPersonId(session, personId);
    }

}
