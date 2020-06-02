//package com.example.demo.client;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//import vn.byt.qlds.configuration.TenantRestTemplate;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Component
//public class OpStatisticInforClient {
//    @Autowired
//    TenantRestTemplate restTemplate;
//
//    private String apiEndpointProvince;
//
//    public OpStatisticInforClient(@Value("${apiEndpointProvince}") String apiEndpointProvince) {
//        this.apiEndpointProvince = apiEndpointProvince + "/operationstatisticinfor";
//    }
//
//    public boolean updateRegion(String oldAddress, String newAddress) {
//        String url = this.apiEndpointProvince + "/region";
//        Map<String, Object> body = new HashMap<>();
//        body.put("old_region", oldAddress);
//        body.put("new_region", newAddress);
//        Boolean result = restTemplate.putForObject(url, body, Boolean.class);
//        return result;
//    }
//}
