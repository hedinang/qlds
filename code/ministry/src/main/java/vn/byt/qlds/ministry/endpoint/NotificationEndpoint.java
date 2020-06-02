package vn.byt.qlds.ministry.endpoint;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.ministry.model.Notification;
import vn.byt.qlds.ministry.model.request.PageRequest;
import vn.byt.qlds.ministry.model.response.PageResponse;
import vn.byt.qlds.ministry.service.NotificationService;

import javax.ws.rs.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Path("notification")
@Api("Notification")
@Produces(APPLICATION_JSON)
@Consumes({APPLICATION_JSON, TEXT_PLAIN})
public class NotificationEndpoint {
    @Autowired
    NotificationService notificationService;

    private static String session = "common";

    @GET
    @Path("/{id}")
    @ApiOperation(value = "Find one Nationality")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Tasks have been successfully executed", response = Notification.class)
            , @ApiResponse(code = 202, message = "Tasks have been queued to be executed at the next inform", response = Notification.class)
    })
    public Notification findOneNotification(@PathParam("id") String id) {
        return notificationService.read(session, id);
    }

    @POST
    public Notification createNotification(@RequestBody Notification nationCategoryRequest) {
        return notificationService.create(session, nationCategoryRequest);
    }

    @GET
    @Path("/all")
    public List<Notification> getAllNotifications(@RequestBody Map<String, Object> query) throws IOException {
        return notificationService.getAll(query);
    }

    @POST
    @Path("/search-page")
    public PageResponse<Notification> getPage(@RequestBody Map<String, Object> query) {

        PageResponse<Notification> result = notificationService.getPage(session, query);
        return result;
    }

    @PUT
    @Path("/{id}")
    public Notification updateNotification(@PathParam("id") String id, @RequestBody Notification nationCategoryRequest) {
        nationCategoryRequest.setMessageId(id);
        return notificationService.update(session, nationCategoryRequest);
    }

    @DELETE
    @Path("/{id}")
    public boolean deleteNotification(@PathParam("id") String id) {
        return notificationService.delete(session, id);
    }
}
