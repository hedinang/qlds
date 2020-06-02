package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.RegionChange;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.RegionChangeService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("region-change")
@Api("RegionChange")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class RegionChangeEndpoint {
    @Autowired
    RegionChangeService regionChangeService;

    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one RegionChange")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = RegionChange.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = RegionChange.class)
    })
    public RegionChange findOneRegionChange(@PathParam("id") Integer id) {
        return regionChangeService.read(session, id);
    }

    @GET
    @Path("/all")
    public List<RegionChange> getAllRegionChanges(@RequestBody Map<String, Object> query) throws IOException {
        return regionChangeService.getAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<RegionChange> getPage(@RequestBody Map<String, Object> query) {

        PageResponse<RegionChange> result = regionChangeService.getPage(session, query);
        return result;
    }

    @POST
    public RegionChange createNationRegionChange(@RequestBody RegionChange regionChangeRequest) {
        return regionChangeService.create(session, regionChangeRequest);
    }

    @PUT
    @Path("/{id}")
    public RegionChange updateNationRegionChange(@PathParam("id") Integer id, @RequestBody RegionChange regionChangeRequest) {
        regionChangeRequest.setId(id);
        return regionChangeService.update(session, regionChangeRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteRegionChange(@PathParam("id") Integer id) {
        return regionChangeService.delete(session, id);
    }
}
