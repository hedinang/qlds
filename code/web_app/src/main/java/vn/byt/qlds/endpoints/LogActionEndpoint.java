//package com.example.demo.endpoints;
//
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestBody;
//import vn.byt.qlds.client.LogActionClient;
//import vn.byt.qlds.model.LevelCategory;
//import vn.byt.qlds.model.LogAction;
//import vn.byt.qlds.model.request.PageRequest;
//import vn.byt.qlds.model.response.LogActionResponse;
//import vn.byt.qlds.model.response.PageResponse;
//
//import javax.ws.rs.*;
//import java.util.List;
//
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
//import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
//
//@Path("log-action")
//@Produces(APPLICATION_JSON)
//@Consumes({APPLICATION_JSON, TEXT_PLAIN})
//public class LogActionEndpoint {
//    @Autowired
//    LogActionClient logActionClient;
//
//    @GET
//    @Path("/{id}")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = LevelCategory.class)
//            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = LevelCategory.class)
//    })
//    public LogActionResponse findOneLogAction(@PathParam("id") int id) {
//        return logActionClient.getLogAction(id);
//    }
//
//    @POST
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public LogActionResponse createLogAction(@RequestBody LogAction levelCategoryRequest) {
//        return logActionClient.createLogAction(levelCategoryRequest);
//    }
//
//    @PUT
//    @Path("/{id}")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public LogActionResponse UpdateLogAction(@PathParam("id") int id, @RequestBody LogAction logAction) {
//        return logActionClient.updateLogAction(id, logAction);
//    }
//
//    @DELETE
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    @Path("/{id}")
//    public boolean deleteLogAction(@PathParam("id") int id) {
//        return logActionClient.deleteLogAction(id);
//    }
//
//    @GET
//    @Path("/all")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public List<LogActionResponse> getAllLogAction() {
//        return logActionClient.getAllLogAction();
//    }
//
//    @POST
//    @Path("/search-page")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public PageResponse<LogActionResponse> getPageLogAction(@RequestBody PageRequest pageRequest) {
//        return logActionClient.getPage(pageRequest);
//
//    }
//}
