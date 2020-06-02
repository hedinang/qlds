//package com.example.demo.endpoints;
//
//import io.swagger.annotations.ApiResponse;
//import io.swagger.annotations.ApiResponses;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.RequestBody;
//import vn.byt.qlds.client.NotificationClient;
//import vn.byt.qlds.model.Notification;
//import vn.byt.qlds.model.request.PageRequest;
//import vn.byt.qlds.model.response.NotificationResponse;
//import vn.byt.qlds.model.response.PageResponse;
//
//import javax.ws.rs.*;
//import java.util.List;
//
//import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
//import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
//
//@Path("notification")
//@Produces(APPLICATION_JSON)
//@Consumes({APPLICATION_JSON, TEXT_PLAIN})
//public class NotificationEndpoint {
//    @Autowired
//    NotificationClient notificationClient;
//
//    @GET
//    @Path("/{id}")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    @ApiResponses(value = {
//            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Notification.class)
//            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Notification.class)
//    })
//    public NotificationResponse findOneNotification(@PathParam("id") String id) {
//        return notificationClient.getNotification(id);
//    }
//
//    @POST
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public NotificationResponse createNotification(@RequestBody Notification notificationRequest) {
//        return notificationClient.createNotification(notificationRequest);
//    }
//
//    @PUT
//    @Path("/{id}")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public NotificationResponse UpdateNotification(@PathParam("id") String id, @RequestBody Notification notificationRequest) {
//        return notificationClient.updateNotification(id, notificationRequest);
//    }
//
//    @DELETE
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    @Path("/{id}")
//    public boolean deleteNotification(@PathParam("id") String id) {
//        return notificationClient.deleteNotification(id);
//    }
//
//    @GET
//    @Path("/all")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public List<NotificationResponse> getAllNotification() {
//        return notificationClient.getAllNotification();
//    }
//
//    @POST
//    @Path("/search-page")
//    @PreAuthorize("hasAnyAuthority('Can Bo Dia Chinh','CRUD')")
//    public PageResponse<NotificationResponse> getPageNotification(@RequestBody PageRequest pageRequest) {
//        return notificationClient.getPage(pageRequest);
//
//    }
//
//}
