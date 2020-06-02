package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.DisabilityCategory;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.DisabilityCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("disability-category")
@Api("DisabilityCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class DisabilityCategoryEndpoint {
    @Autowired
    DisabilityCategoryService disabilityCategoryService;

    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one DisabilityCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = DisabilityCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = DisabilityCategory.class)
    })
    public DisabilityCategory findOneDisability(@PathParam("id") Integer id) {
        return disabilityCategoryService.read(session, id);
    }

    @POST
    @Path("/all")
    public List<DisabilityCategory> getAllDisabilityCategories(@RequestBody Map<String, Object> query) throws IOException {
        return disabilityCategoryService.getAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<DisabilityCategory> getPage(@RequestBody Map<String, Object> query) {

        PageResponse<DisabilityCategory> result = disabilityCategoryService.getPage(session, query);
        return result;
    }

    @POST
    public DisabilityCategory createDisability(@RequestBody DisabilityCategory deadCategoryRequest) {
        return disabilityCategoryService.create(session, deadCategoryRequest);
    }

    @PUT
    @Path("/{id}")
    public DisabilityCategory updateDisability(@PathParam("id") Integer id, @RequestBody DisabilityCategory deadCategoryRequest) {
        deadCategoryRequest.setId(id);
        return disabilityCategoryService.update(session, deadCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteDisability(@PathParam("id") Integer id) {
        return disabilityCategoryService.delete(session, id);
    }
}
