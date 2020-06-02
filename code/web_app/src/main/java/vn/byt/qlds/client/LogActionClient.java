//package com.example.demo.client;
//
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import vn.byt.qlds.configuration.TenantRestTemplate;
//import vn.byt.qlds.configuration.ThreadLocalStorage;
//import vn.byt.qlds.model.LogAction;
//import vn.byt.qlds.model.request.PageRequest;
//import vn.byt.qlds.model.response.LogActionResponse;
//import vn.byt.qlds.model.response.PageResponse;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@Component
//public class LogActionClient {
//    @Autowired
//    TenantRestTemplate restTemplate;
//
//    private String apiEndpointProvince;
//
//    public LogActionClient(@Value("${apiEndpointProvince}") String apiEndpointProvince) {
//        this.apiEndpointProvince = apiEndpointProvince + "/log-action";
//    }
//
//    public LogActionResponse getLogAction(int id) {
//        String url = this.apiEndpointProvince + "/" + id;
//        LogAction result = restTemplate.getForObject(url, LogAction.class);
//        if (result != null) {
//            return new LogActionResponse(result.getId(), result.getTimeCreated(), result.getUserCreated(), result.getUserName(), result.getDescription(), result.getAction());
//        }
//        return new LogActionResponse();
//    }
//
//    public LogActionResponse createLogAction(LogAction logAction) {
//        logAction.setIsDeleted(false);
//        logAction.setUserCreated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//        LogAction result = restTemplate.postForObject(this.apiEndpointProvince, logAction, LogAction.class);
//        return new LogActionResponse(result.getId(), result.getTimeCreated(), result.getUserCreated(), result.getUserName(), result.getDescription(), result.getAction());
//    }
//
//    public boolean deleteLogAction(int id) {
//        String url = this.apiEndpointProvince + "/" + id;
//        LogAction logAction = restTemplate.getForObject(url, LogAction.class);
//        if (logAction != null) {
//            logAction.setIsDeleted(true);
//            LogAction unit = restTemplate.putForObject(url, logAction, LogAction.class);
//            return true;
//        }
//        return false;
//    }
//
//    public LogActionResponse updateLogAction(int id, LogAction requestLogAction) {
//        String url = this.apiEndpointProvince + "/" + id;
//        LogAction logAction = restTemplate.getForObject(url, LogAction.class);
//        if (logAction != null) {
//            requestLogAction.setIsDeleted(logAction.getIsDeleted());
//            requestLogAction.setUserCreated(logAction.getUserCreated());
//            requestLogAction.setTimeCreated(logAction.getTimeCreated());
//            LogAction result = restTemplate.putForObject(url, requestLogAction, LogAction.class);
//            return new LogActionResponse(result.getId(), result.getTimeCreated(), result.getUserCreated(), result.getUserName(), result.getDescription(), result.getAction());
//        }
//        return new LogActionResponse();
//    }
//
//    public List<LogActionResponse> getAllLogAction() {
//        ObjectMapper mapper = new ObjectMapper();
//        String url = this.apiEndpointProvince + "/all";
//        List<LogAction> list = mapper.convertValue(restTemplate.getForObject(url, List.class), new TypeReference<List<LogAction>>() {
//        });
//        List<LogActionResponse> results = new ArrayList<>();
//        for (LogAction logAction : list) {
//            results.add(new LogActionResponse(logAction.getId(), logAction.getTimeCreated(), logAction.getUserCreated(), logAction.getUserName(), logAction.getDescription(), logAction.getAction()));
//        }
//        return results;
//    }
//
//    public PageResponse<LogActionResponse> getPage(PageRequest pageRequest) {
//        String url = this.apiEndpointProvince + "/" + "search-page";
//        PageResponse<LogAction> logActionPages = restTemplate.postForObject(url, pageRequest, PageResponse.class);
//        PageResponse<LogActionResponse> result = new PageResponse<>();
//        result.setPage(logActionPages.getPage());
//        //result.setTotal(logActionPages.getTotal());
//        ObjectMapper mapper = new ObjectMapper();
//        List<LogActionResponse> list = new ArrayList<>();
//        List<LogAction> logActionList = mapper.convertValue(logActionPages.getList(), new TypeReference<List<LogAction>>() {
//        });
//        for (LogAction logAction : logActionList) {
//            list.add(new LogActionResponse(logAction.getId(), logAction.getTimeCreated(), logAction.getUserCreated(), logAction.getUserName(), logAction.getDescription(), logAction.getAction()));
//        }
//        result.setList(list);
//        return result;
//
//    }
//}
