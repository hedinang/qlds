package vn.byt.qlds.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.byt.qlds.model.FamilyPlanning;
import vn.byt.qlds.model.request.PageRequest;
import vn.byt.qlds.model.response.PageResponse;
import vn.byt.qlds.service.FamilyPlanningService;

import javax.ws.rs.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("family-planning")
@Api("FamilyPlanning")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class FamilyPlanningEndpoint {
    @Autowired
    FamilyPlanningService familyPlanningService;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one FamilyPlanning")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = FamilyPlanning.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = FamilyPlanning.class)
    })
    public FamilyPlanning findOneFamilyPlanning(@HeaderParam("session") String session, @PathParam("id") int id) {
        return familyPlanningService.read(session, id);
    }

    @GET
    @Path("/all")
    public List<FamilyPlanning> getAllFamilyPlannings(@HeaderParam("session") String session) {
        return familyPlanningService.getAll(session);
    }

    @POST
    @Path("/search-page")
    public PageResponse<FamilyPlanning> getPage(@HeaderParam("session") String session, @RequestBody PageRequest pageRequest) {

        PageResponse<FamilyPlanning> result = familyPlanningService.getPage(session, pageRequest.page, pageRequest.limit, pageRequest.direction, pageRequest.property);
        return result;
    }

    @POST
    public FamilyPlanning createFamilyPlanning(@HeaderParam("session") String session, @RequestBody FamilyPlanning familyPlanningRequest) {
        return familyPlanningService.create(session, familyPlanningRequest);
    }

    @PUT
    @Path("/{id}")
    public FamilyPlanning updateFamilyPlanning(@HeaderParam("session") String session, @PathParam("id") int id, @RequestBody FamilyPlanning familyPlanningRequest) {
        familyPlanningRequest.setPersonalId(id);
        return familyPlanningService.update(session, familyPlanningRequest);
    }

    @PUT
    @Path("/region")
    public boolean updateFamilyPlanning(
            @HeaderParam("session") String session,
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("regionId", params.get("old_region"));
        value.put("regionId", params.get("new_region"));

        return familyPlanningService.update(session, where, value);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteFamilyPlanning(@HeaderParam("session") String session, @PathParam("id") int id) {
        return familyPlanningService.delete(session, id);
    }
}
