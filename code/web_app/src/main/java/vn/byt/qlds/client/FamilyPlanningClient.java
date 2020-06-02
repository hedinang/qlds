//package com.example.demo.client;
//
//import com.example.demo.core.TenantRestTemplate;
//import com.example.demo.core.base.PageResponse;
//import com.example.demo.model._province.family_plan.FamilyPlanning;
//import com.example.demo.model._province.family_plan.FamilyPlanningResponse;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Component
//public class FamilyPlanningClient {
//    @Autowired
//    TenantRestTemplate restTemplate;
//
//    private String apiEndpointProvince;
//
//    public FamilyPlanningClient(@Value("${apiEndpointProvince}") String apiEndpointProvince) {
//        this.apiEndpointProvince = apiEndpointProvince + "/family-planning";
//    }
//
//    //@Cacheable("familyPlanning")
//    public FamilyPlanningResponse getFamilyPlanning(int id) {
//        String url = this.apiEndpointProvince + "/" + id;
//        FamilyPlanning result = restTemplate.getForObject(url, FamilyPlanning.class);
//        if (result != null) {
//            return new FamilyPlanningResponse(result.getPersonalId(), result.getContraDate(), result.getContraceptiveCode(),
//                    result.getExportStatus(), result.getRegionId());
//        }
//        return new FamilyPlanningResponse();
//    }
//
//    public FamilyPlanningResponse createFamilyPlanning(FamilyPlanning familyPlanningRequest) {
//        familyPlanningRequest.setIsDeleted(false);
//        familyPlanningRequest.setUserCreated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//        familyPlanningRequest.setUserLastUpdated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//        FamilyPlanning result = restTemplate.postForObject(this.apiEndpointProvince, familyPlanningRequest, FamilyPlanning.class);
//        return new FamilyPlanningResponse(result.getPersonalId(), result.getContraDate(), result.getContraceptiveCode(),
//                result.getExportStatus(), result.getRegionId());
//    }
//
//    public boolean deleteFamilyPlanning(int id) {
//        String url = this.apiEndpointProvince + "/" + id;
//        FamilyPlanning familyPlanning = restTemplate.getForObject(url, FamilyPlanning.class);
//        if (familyPlanning != null) {
//            familyPlanning.setUserLastUpdated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//            familyPlanning.setIsDeleted(true);
//            FamilyPlanning unit = restTemplate.putForObject(url, familyPlanning, FamilyPlanning.class);
//            return true;
//        }
//        return false;
//    }
//
//    public FamilyPlanningResponse updateFamilyPlanning(int id, FamilyPlanning familyPlanningResquest) {
//        String url = this.apiEndpointProvince + "/" + id;
//        FamilyPlanning familyPlanning = restTemplate.getForObject(url, FamilyPlanning.class);
//        if (familyPlanning != null) {
//            familyPlanningResquest.setIsDeleted(familyPlanning.getIsDeleted());
//            familyPlanningResquest.setUserCreated(familyPlanning.getUserCreated());
//            familyPlanningResquest.setTimeCreated(familyPlanning.getTimeCreated());
//            familyPlanningResquest.setUserLastUpdated(Long.parseLong(ThreadLocalStorage.getTenantName().get("userId")));
//            FamilyPlanning result = restTemplate.putForObject(url, familyPlanningResquest, FamilyPlanning.class);
//            return new FamilyPlanningResponse(result.getPersonalId(), result.getContraDate(), result.getContraceptiveCode(),
//                    result.getExportStatus(), result.getRegionId());
//        }
//        return new FamilyPlanningResponse();
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
//
//    public List<FamilyPlanningResponse> getAllFamilyPlanning() {
//        ObjectMapper mapper = new ObjectMapper();
//        String url = this.apiEndpointProvince + "/all";
//        List<FamilyPlanning> list = mapper.convertValue(restTemplate.getForObject(url, List.class), new TypeReference<List<FamilyPlanning>>() {
//        });
//        List<FamilyPlanningResponse> results = new ArrayList<>();
//        for (FamilyPlanning familyPlanning : list) {
//            results.add(new FamilyPlanningResponse(familyPlanning.getPersonalId(), familyPlanning.getContraDate(), familyPlanning.getContraceptiveCode(),
//                    familyPlanning.getExportStatus(), familyPlanning.getRegionId()));
//        }
//        return results;
//    }
//
//    public PageResponse<FamilyPlanningResponse> getPage(PageRequest pageRequest) {
//        String url = this.apiEndpointProvince + "/" + "search-page";
//        PageResponse<FamilyPlanning> familyPlanningPages = restTemplate.postForObject(url, pageRequest, PageResponse.class);
//        PageResponse<FamilyPlanningResponse> result = new PageResponse<>();
//        result.setPage(familyPlanningPages.getPage());
//        //result.setTotal(familyPlanningPages.getTotal());
//        ObjectMapper mapper = new ObjectMapper();
//        List<FamilyPlanningResponse> list = new ArrayList<>();
//        List<FamilyPlanning> familyPlanningList = mapper.convertValue(familyPlanningPages.getList(), new TypeReference<List<FamilyPlanning>>() {
//        });
//        for (FamilyPlanning familyPlanning : familyPlanningList) {
//            list.add(new FamilyPlanningResponse(familyPlanning.getPersonalId(), familyPlanning.getContraDate(), familyPlanning.getContraceptiveCode(),
//                    familyPlanning.getExportStatus(), familyPlanning.getRegionId()));
//        }
//        result.setList(list);
//        return result;
//
//    }
//}
