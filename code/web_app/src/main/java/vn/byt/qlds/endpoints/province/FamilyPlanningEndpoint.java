//package com.example.demo.endpoints.province;
//
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestBody;
//import vn.byt.qlds.client.FamilyPlanningClient;
//import vn.byt.qlds.model.FamilyPlanning;
//import vn.byt.qlds.model.request.PageRequest;
//import vn.byt.qlds.model.response.FamilyPlanningResponse;
//import vn.byt.qlds.model.response.PageResponse;
//
//import javax.ws.rs.*;
//import java.util.List;
//
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
//import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
//
//@Path("family-planning")
//@Produces(APPLICATION_JSON)
//@Consumes({APPLICATION_JSON, TEXT_PLAIN})
//public class FamilyPlanningEndpoint {
//    @Autowired
//    FamilyPlanningClient familyPlanningClient;
//
//    @GET
//    @Path("/{id}")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = FamilyPlanning.class)
//            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = FamilyPlanning.class)
//    })
//    public FamilyPlanningResponse findOneFamilyPlanning(@PathParam("id") int id) {
//        return familyPlanningClient.getFamilyPlanning(id);
//    }
//
//    @POST
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public FamilyPlanningResponse createFamilyPlanning(@RequestBody FamilyPlanning familyPlanningRequest) {
//        return familyPlanningClient.createFamilyPlanning(familyPlanningRequest);
//    }
//
//    @PUT
//    @Path("/{id}")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public FamilyPlanningResponse UpdateFamilyPlanning(@PathParam("id") Integer id, @RequestBody FamilyPlanning familyPlanningRequest) {
//        return familyPlanningClient.updateFamilyPlanning(id, familyPlanningRequest);
//    }
//
//    @DELETE
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    @Path("/{id}")
//    public boolean deleteFamilyPlanning(@PathParam("id") int id) {
//        return familyPlanningClient.deleteFamilyPlanning(id);
//    }
//
//    @GET
//    @Path("/all")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public List<FamilyPlanningResponse> getAllFamilyPlanning() {
//        return familyPlanningClient.getAllFamilyPlanning();
//    }
//
//    @POST
//    @Path("/search-page")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public PageResponse<FamilyPlanningResponse> getPageFamilyPlanning(@RequestBody PageRequest pageRequest) {
//        return familyPlanningClient.getPage(pageRequest);
//
//    }
//}
