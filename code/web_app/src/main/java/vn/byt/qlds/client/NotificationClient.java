//package com.example.demo.client;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import vn.byt.qlds.configuration.TenantRestTemplate;
//import vn.byt.qlds.configuration.ThreadLocalStorage;
//import vn.byt.qlds.model.Notification;
//import vn.byt.qlds.model.request.PageRequest;
//import vn.byt.qlds.model.response.NotificationResponse;
//import vn.byt.qlds.model.response.PageResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class NotificationClient {
//    @Autowired
//    TenantRestTemplate restTemplate;
//
//    private String apiEndpointCommon;
//
//    public NotificationClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
//        this.apiEndpointCommon = apiEndpointCommon + "/notification";
//    }
//
//    //@Cacheable("notification")
//    public NotificationResponse getNotification(String id) {
//        String url = this.apiEndpointCommon + "/" + id;
//        Notification result = restTemplate.getForObject(url, Notification.class);
//        if (result != null) {
//            return new NotificationResponse(result.getMessageId(), result.getDescription());
//        }
//        return new NotificationResponse();
//    }
//
//    public NotificationResponse createNotification(Notification notificationRequest) {
//        notificationRequest.setIsDeleted(false);
//        notificationRequest.setUserCreated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//        notificationRequest.setUserLastUpdated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//        Notification result = restTemplate.postForObject(this.apiEndpointCommon, notificationRequest, Notification.class);
//        return new NotificationResponse(result.getMessageId(), result.getDescription());
//    }
//
//    public boolean deleteNotification(String id) {
//        String url = this.apiEndpointCommon + "/" + id;
//        Notification notification = restTemplate.getForObject(url, Notification.class);
//        if (notification != null) {
//            notification.setUserLastUpdated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//            notification.setIsDeleted(true);
//            Notification unit = restTemplate.putForObject(url, notification, Notification.class);
//            return true;
//        }
//        return false;
//    }
//
//    public NotificationResponse updateNotification(String id, Notification notificationResquest) {
//        String url = this.apiEndpointCommon + "/" + id;
//        Notification notification = restTemplate.getForObject(url, Notification.class);
//        if (notification != null) {
//            notificationResquest.setIsDeleted(notification.getIsDeleted());
//            notificationResquest.setUserCreated(notification.getUserCreated());
//            notificationResquest.setTimeCreated(notification.getTimeCreated());
//            notificationResquest.setUserLastUpdated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//            Notification result = restTemplate.putForObject(url, notificationResquest, Notification.class);
//            return new NotificationResponse(result.getMessageId(), result.getDescription());
//        }
//        return new NotificationResponse();
//    }
//
//    public List<NotificationResponse> getAllNotification() {
//        ObjectMapper mapper = new ObjectMapper();
//        String url = this.apiEndpointCommon + "/all";
//        List<Notification> list = mapper.convertValue(restTemplate.getForObject(url, List.class), new TypeReference<List<Notification>>() {
//        });
//        List<NotificationResponse> results = new ArrayList<>();
//        for (Notification notification : list) {
//            results.add(new NotificationResponse(notification.getMessageId(), notification.getDescription()));
//        }
//        return results;
//    }
//
//    public PageResponse<NotificationResponse> getPage(PageRequest pageRequest) {
//        String url = this.apiEndpointCommon + "/" + "search-page";
//        PageResponse<Notification> notificationPages = restTemplate.postForObject(url, pageRequest, PageResponse.class);
//        PageResponse<NotificationResponse> result = new PageResponse<>();
//        result.setPage(notificationPages.getPage());
//        //result.setTotal(notificationPages.getTotal());
//        ObjectMapper mapper = new ObjectMapper();
//        List<NotificationResponse> list = new ArrayList<>();
//        List<Notification> notificationList = mapper.convertValue(notificationPages.getList(), new TypeReference<List<Notification>>() {
//        });
//        for (Notification notification : notificationList) {
//            list.add(new NotificationResponse(notification.getMessageId(), notification.getDescription()));
//        }
//        result.setList(list);
//        return result;
//
//    }
//}
