package vn.byt.qlds.ministry.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.model.UnitCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.UnitCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("unit-category")
@Api("UnitCategory")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class UnitCategoryEndpoint {
    private static String session = "common";
    @Autowired
    UnitCategoryService unitCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one UnitCategory")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = UnitCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = UnitCategory.class)
    })
    public UnitCategory findOneUnitCategory(@PathParam("id") Integer id) throws IOException {
        return unitCategoryService.findOneById(id);
    }

    @GET
    public UnitCategory findOneByCode(@QueryParam("code") String ma) throws IOException {
        return unitCategoryService.findOneByCode(ma);
    }

    @POST
    public UnitCategory createUnitCategory(@RequestBody UnitCategory unitCategoryRequest) {
        return unitCategoryService.create(session, unitCategoryRequest);
    }

    @POST
    @Path("/all")
    public List getAll(@RequestBody Map<String, Object> query) throws IOException {
        return unitCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return unitCategoryService.findPage(query);
    }

    @PUT
    @Path("/{id}")
    public UnitCategory updateUnitCategory(@PathParam("id") Integer id, @RequestBody UnitCategory unitCategoryRequest) {
        unitCategoryRequest.setId(id);
        return unitCategoryService.update(session, unitCategoryRequest);
    }

    @PUT
    @Path("/delete")
    public boolean deleteUnitCategory(@RequestParam Map<String, String> params) {
        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("code", params.get("code"));
        value.put("isDeleted", true);
        return unitCategoryService.update(session, where, value);
    }

    @PUT
    @Path("/parent")
    public boolean updateParent(
            @RequestParam Map<String, String> params) {

        Map<String, Object> where = new HashMap<>();
        Map<String, Object> value = new HashMap<>();
        where.put("parent", params.get("old_region"));
        value.put("parent", params.get("new_region"));

        return unitCategoryService.update(session, where, value);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteUnitCategory(@PathParam("id") Integer id) {
        return unitCategoryService.delete(session, id);
    }

    @GET
    @Path("/search/list-provinces")
    public List<UnitCategory> getListProvincesCategory(@QueryParam("code") String code) throws IOException {
        return unitCategoryService.getListProvincesCategory(1, code);
    }

    @GET
    @Path("/search/list-districts")
    public List<UnitCategory> getListDistrictsCategory(@QueryParam("code") String code) throws IOException {
        return unitCategoryService.getListDistrictsCategory(code);
    }

    @GET
    @Path("/search/parent")
    public List<UnitCategory> getListForParent(@QueryParam("regionId") String regionId) {
        return unitCategoryService.getListUnitCategoryForParent(session, regionId);
    }

    @GET
    @Path("/search/list-communes")
    public List<UnitCategory> getListCommunesCategory(@QueryParam("district") String district, @QueryParam("code") String code) throws IOException {
        return unitCategoryService.getListCommunesCategory(district, code);
    }

    @GET
    @Path("/list-for-level")
    public List<UnitCategory> getListProvincesCategory(@QueryParam("regionId") String regionId, @QueryParam("level") int level) {
        return unitCategoryService.getListUnitCategoryForLevel(session, level, regionId);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("UnitCategory", "common")));
    }
}
