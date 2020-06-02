package vn.byt.qlds.service;

import vn.byt.qlds.client.UnitCategoryClient;
import vn.byt.qlds.model.unit.UnitCategory;
import vn.byt.qlds.model.unit.UnitCategoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UnitCategoryService {
    private final static int LEVEL_PROVINCE = 1;
    private final static int LEVEL_DISTRICT = 2;
    private final static int LEVEL_COMMUNE = 3;
    @Autowired
    UnitCategoryClient unitCategoryClient;

    public UnitCategory getProvinceByRegionCode(String regionCode) {
        UnitCategory unitCategory = unitCategoryClient
                .getUnitCategoryByCode(regionCode)
                .orElse(new UnitCategoryResponse())
                .unitCategory;
        if (unitCategory != null && unitCategory.getLevels() != null) {
            switch (unitCategory.getLevels()) {
                case LEVEL_COMMUNE:
                case LEVEL_DISTRICT:
                    return getProvinceByRegionCode(unitCategory.getParent());
                case LEVEL_PROVINCE:
                    return unitCategory;
            }
        } else {
            return null;
        }
        return null;
    }

    public String generateFullName(String regionCode) {
        UnitCategory unitCategory = unitCategoryClient
                .getUnitCategoryByCode(regionCode)
                .orElse(new UnitCategoryResponse())
                .unitCategory;
        if (unitCategory != null) {
            if (unitCategory.getLevels() == LEVEL_PROVINCE) {
                return unitCategory.getName();
            }else if (unitCategory.getLevels() == LEVEL_DISTRICT
                    || unitCategory.getLevels() == LEVEL_COMMUNE)
                return unitCategory.getName() +", "+ generateFullName(unitCategory.getParent());
            else {
                return "";
            }
        }else {
            return "";
        }
    }

    public String getProvinceName(String regionId){
        if (regionId == null) return null;
        UnitCategory unitCategory = unitCategoryClient
                .getUnitCategoryByCode(regionId)
                .orElse(new UnitCategoryResponse())
                .unitCategory;
        return unitCategory.getName();
    }
}
