package vn.byt.qlds.client;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import vn.byt.qlds.core.QldsRestTemplate;
import vn.byt.qlds.core.TransferToResponseService;
import vn.byt.qlds.core.base.ConvertList;
import vn.byt.qlds.core.base.PageResponse;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.unit.*;

import java.util.*;
import java.util.stream.Collectors;


@Component
public class UnitCategoryClient {
    @Autowired
    QldsRestTemplate qldsRestTemplate;
    @Autowired
    TransferToResponseService toResponseService;

    private String apiEndpointCommon;

    public UnitCategoryClient(@Value("${apiEndpointCommon}") String apiEndpointCommon) {
        this.apiEndpointCommon = apiEndpointCommon + "/unit-category";
    }

    public Optional<UnitCategoryResponse> createUnitCategory(long userId, UnitCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        UnitCategory unitCategory = new UnitCategory(request);
        unitCategory.setIsDeleted(false);
        unitCategory.setTimeCreated(StringUtils.convertLongToTimestamp(currentTime));
        unitCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        unitCategory.setUserCreated(userId);
        unitCategory.setUserLastUpdated(userId);
        UnitCategory result = qldsRestTemplate.postForObject(this.apiEndpointCommon, unitCategory, UnitCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<UnitCategoryResponse> getUnitCategory(Integer id) {
        String url = this.apiEndpointCommon + "/" + id;
        UnitCategory result = qldsRestTemplate.getForObject(url, UnitCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<UnitCategoryResponse> getUnitCategoryByCode(String code) {
        String url = this.apiEndpointCommon + "?code=" + code;
        UnitCategory result = qldsRestTemplate.getForObject(url, UnitCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public Optional<UnitResponse> getUnitByCode(String code){
        if(code == null){
            return Optional.empty();
        }
        String url = this.apiEndpointCommon + "/all";
        Map<String, Object> request =  new HashMap<>();
        request.put("code", code);
        List<UnitResponse> categories = qldsRestTemplate.postForObject(url, request, ConvertList.UnitList.class);
        if (categories!=null && !categories.isEmpty()){
            return Optional.ofNullable(categories.get(0));
        }else{
            return Optional.empty();
        }
    }

    public Optional<UnitCategoryResponse> updateUnitCategory(Long userId, UnitCategory unitCategory, UnitCategoryRequest request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + unitCategory.getId();
        unitCategory.createFromRequest(request);
        unitCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        unitCategory.setUserLastUpdated(userId);
        UnitCategory result = qldsRestTemplate.putForObject(url, unitCategory, UnitCategory.class);
        return Optional.ofNullable(toResponseService.transfer(result));
    }

    public UnitCategoryResponse updateUnitCategory(Long userId, UnitCategory request) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + request.getId();
        request.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        request.setUserLastUpdated(userId);
        UnitCategory result = qldsRestTemplate.putForObject(url, request, UnitCategory.class);
        return toResponseService.transfer(result);
    }

    public boolean deleteUnitCategory(Long userId, UnitCategory unitCategory) {
        long currentTime = System.currentTimeMillis();
        String url = this.apiEndpointCommon + "/" + unitCategory.getId();
        unitCategory.setIsDeleted(true);
        unitCategory.setTimeLastUpdated(StringUtils.convertLongToTimestamp(currentTime));
        unitCategory.setUserLastUpdated(userId);
        qldsRestTemplate.putForObject(url, unitCategory, UnitCategory.class);
        return true;
    }

    public boolean delete(String ma) {
        String url = this.apiEndpointCommon + "/delete";
        Map<String, String> value = new HashMap<>();
        value.put("code", ma);
        Boolean result = qldsRestTemplate.putForObject(url, value, Boolean.class);
        return result;
    }

    public List<UnitCategoryResponse> getAllUnitCategory(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        ArrayList<UnitCategory> categories = qldsRestTemplate.postForObject(url, query, ArrayList.class);
        List<UnitCategory> unitCategories = mapper.convertValue(categories, new TypeReference<List<UnitCategory>>() {
        });
        return unitCategories.stream().map(toResponseService::transfer).collect(Collectors.toList());
    }

    public List<UnitCategory> getAll(Map<String, Object> query) {
        ObjectMapper mapper = new ObjectMapper();
        String url = this.apiEndpointCommon + "/all";
        ArrayList<UnitCategory> categories = qldsRestTemplate.postForObject(url, query, ArrayList.class);
        return mapper.convertValue(categories, new TypeReference<List<UnitCategory>>() {
        });
    }

    public PageResponse<UnitCategoryResponse> getPage(@RequestBody Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/search-page";
        ObjectMapper mapper = new ObjectMapper();
        PageResponse<UnitCategory> response = qldsRestTemplate.postForObject(url, query, PageResponse.class);
        List<UnitCategory> unitCategories = mapper.convertValue(response.getList(), new TypeReference<List<UnitCategory>>() {
        });
        List<UnitCategoryResponse> unitLevelCategoryResponses = unitCategories
                .stream()
                .map(toResponseService::transfer)
                .collect(Collectors.toList());
        PageResponse<UnitCategoryResponse> result = new PageResponse<>();
        result.setPage(response.getPage());
        result.setTotal(response.getTotal());
        result.setList(unitLevelCategoryResponses);
        result.setLimit(response.getLimit());
        return result;

    }

    public List<UnitCategoryTreeResponse> getParentOfUnitCategory() {
        /*get all province*/
        List<UnitCategoryTreeResponse> response = new ArrayList<>();
        Map<String, Object> queryProvince = new HashMap<>();
        queryProvince.put("levels", 1);
        List<UnitResponse> provinces = getAllUnit(queryProvince);
        provinces.forEach(province -> {
            Map<String, Object> queryDistrict = new HashMap<>();
            queryDistrict.put("levels", 2);
            queryDistrict.put("parent", province.code);
            UnitCategoryTreeResponse unitCategoryTreeResponse = new UnitCategoryTreeResponse();
            unitCategoryTreeResponse.id = province.id;
            unitCategoryTreeResponse.code = province.code;
            unitCategoryTreeResponse.name = province.name;
            unitCategoryTreeResponse.levels = province.levels;
            unitCategoryTreeResponse.parent = province.parent;
            unitCategoryTreeResponse.districts = getAllUnit(queryDistrict);
            response.add(unitCategoryTreeResponse);
        });
        return response;
    }

    /*lấy all province - district -  commune*/
    public List<UnitTree> getParentAll() {
        /*get all province*/
        List<UnitResponse> unitCategories = getAllUnit(new HashMap<>());
        Map<String, UnitResponse> codeToUnit = new HashMap<>();
        Map<String, LinkedHashMap<String, ArrayList<UnitResponse>>> rootTree = new LinkedHashMap<>();

        unitCategories.forEach(unitTreeModel -> {
            Integer level = unitTreeModel.levels;
            String code = unitTreeModel.code;
            if (level == 1) rootTree.put(code, new LinkedHashMap<>());
            if (level == 2) {
                LinkedHashMap<String, ArrayList<UnitResponse>> linkedHashMap = rootTree.get(unitTreeModel.parent);
                if (linkedHashMap != null)
                    rootTree.get(unitTreeModel.parent).put(unitTreeModel.code, new ArrayList<>());
                else {
                    linkedHashMap = new LinkedHashMap<>();
                    linkedHashMap.put(unitTreeModel.code, new ArrayList<>());
                    rootTree.put(unitTreeModel.parent, linkedHashMap);
                }
            }
            codeToUnit.put(unitTreeModel.code, unitTreeModel);
        });


        unitCategories.forEach(unitTreeModel -> {
            Integer level = unitTreeModel.levels;
            String code = unitTreeModel.code;
            if (level != null && code != null) {
                if (level == 3) {
                    UnitResponse districtUnit = codeToUnit.get(unitTreeModel.parent);
                    UnitResponse provinceUnit = codeToUnit.get(districtUnit != null ? districtUnit.parent : "");
                    if (provinceUnit != null) {
                        String provinceId = provinceUnit.code;
                        LinkedHashMap<String, ArrayList<UnitResponse>> provinceToDistricts = rootTree.get(provinceId);
                        if (provinceToDistricts == null) {
                            LinkedHashMap<String, ArrayList<UnitResponse>> districtToCommunes = new LinkedHashMap<>();
                            districtToCommunes.put(unitTreeModel.parent, new ArrayList<>());
                            rootTree.put(provinceId, districtToCommunes);
                        } else {
                            provinceToDistricts.computeIfAbsent(unitTreeModel.parent, k -> new ArrayList<>());
                            provinceToDistricts.get(unitTreeModel.parent).add(unitTreeModel);
                        }
                    }
                }
            }
        });
        List<UnitTree> response = new ArrayList<>();
        Set<String> keyProvinces = rootTree.keySet();
        for (String keyProvince : keyProvinces) {
            LinkedHashMap<String, ArrayList<UnitResponse>> linkProvince = rootTree
                    .get(keyProvince);
            UnitTree provinceTree = new UnitTree(codeToUnit.get(keyProvince));
            Set<String> keyDistricts = rootTree.get(keyProvince).keySet();

            keyDistricts.forEach(keyDistrict -> {
                UnitTree districtTree = new UnitTree(codeToUnit.get(keyDistrict));
                List<UnitTree> communesTree = linkProvince
                        .get(keyDistrict)
                        .stream()
                        .map(UnitTree::new)
                        .collect(Collectors.toList());
                districtTree.children.addAll(communesTree);
                provinceTree.children.add(districtTree);
            });
            response.add(provinceTree);
        }

        return response;
    }

    public List<UnitResponse> getProvinceAndDistrict() {
        /*get all province*/
        /*get all province*/
        Map<String, Object> queryProvince = new HashMap<>();
        queryProvince.put("levels", 1);

        Map<String, Object> queryDistrict = new HashMap<>();
        queryDistrict.put("levels", 2);

        List<UnitResponse> provinces = getAllUnit(queryProvince);
        List<UnitResponse> districts = getAllUnit(queryDistrict);
        List<UnitResponse> response = new ArrayList<>(provinces);
        response.addAll(districts);
        return response;
    }

    private List<UnitResponse> getAllUnit(Map<String, Object> query) {
        String url = this.apiEndpointCommon + "/all";
        return qldsRestTemplate.postForObject(url, query, ConvertList.UnitList.class);
    }


}
