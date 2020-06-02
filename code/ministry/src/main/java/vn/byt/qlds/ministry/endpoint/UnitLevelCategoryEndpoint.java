package vn.byt.qlds.ministry.endpoint;

import com.google.gson.Gson;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.ESMessageSync;
import vn.byt.qlds.ministry.model.UnitLevelCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.UnitLevelCategoryService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("unit-level-category")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class UnitLevelCategoryEndpoint {
    @Autowired
    UnitLevelCategoryService unitLevelCategoryService;
    @Autowired
    RabbitTemplate rabbitTemplate;

    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = UnitLevelCategory.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = UnitLevelCategory.class)
    })
    public UnitLevelCategory findOneUnitLevelCategory(@PathParam("id") int id) throws IOException {
        return unitLevelCategoryService.findOne(id);
    }


    @POST
    @Path("/all")
    public List getAllUnitLevelCategories(@RequestBody Map<String, Object> query) throws IOException {
        return unitLevelCategoryService.findAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse getPage(@RequestBody Map<String, Object> query) throws IOException {
        return unitLevelCategoryService.findPage(query);
    }

    @POST
    public UnitLevelCategory createAdministrativeLevelCategory(@RequestBody UnitLevelCategory unitLevelCategoryRequest) {
        return unitLevelCategoryService.create(session, unitLevelCategoryRequest);
    }

    @PUT
    @Path("/{id}")
    public UnitLevelCategory updateAdministrativeLevelCategory(@PathParam("id") int id, @RequestBody UnitLevelCategory unitLevelCategoryRequest) {
        unitLevelCategoryRequest.setId(id);
        return unitLevelCategoryService.update(session, unitLevelCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteUnitLevelCategory(@PathParam("id") int id) {
        return unitLevelCategoryService.delete(session, id);
    }

    @GET
    @Path("/sync")
    public void syncDatabase() {
        // ban len tren queue
        rabbitTemplate.convertAndSend("QLDS", "All", new Gson().toJson(new ESMessageSync("UnitLevelCategory", "common")));
    }
}
