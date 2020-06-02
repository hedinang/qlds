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
import vn.byt.qlds.ministry.model.RegionChangeType;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.RegionChangeTypeService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("region-change-type")
@Api("RegionChangeType")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class RegionChangeTypeEndpoint {
    @Autowired
    RegionChangeTypeService regionChangeTypeService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one RegionChangeType")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = RegionChangeType.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = RegionChangeType.class)
    })
    public RegionChangeType findOneRegionChangeType(@PathParam("id") String id) {
        return regionChangeTypeService.read(session, id);
    }

    @GET
    @Path("/all")
    public List<RegionChangeType> getAllRegionChangeTypes(@RequestBody Map<String, Object> query) throws IOException {
        return regionChangeTypeService.getAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<RegionChangeType> getPage(@RequestBody Map<String, Object> query) {

        PageResponse<RegionChangeType> result = regionChangeTypeService.getPage(session, query);
        return result;
    }

    @POST
    public RegionChangeType createNationRegionChangeType(@RequestBody RegionChangeType regionChangeTypeRequest) {
        return regionChangeTypeService.create(session, regionChangeTypeRequest);
    }

    @PUT
    @Path("/{id}")
    public RegionChangeType updateNationRegionChangeType(@PathParam("id") String id, @RequestBody RegionChangeType regionChangeTypeRequest) {
        regionChangeTypeRequest.setChangeTypeCode(id);
        return regionChangeTypeService.update(session, regionChangeTypeRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteRegionChangeType(@PathParam("id") String id) {
        return regionChangeTypeService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("RegionChangeType", "common")));

    }
}
