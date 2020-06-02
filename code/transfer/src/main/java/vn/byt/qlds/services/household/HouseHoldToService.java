package vn.byt.qlds.services.household;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import vn.byt.qlds.config.db.CrudService;
import vn.byt.qlds.entity_to.HouseholdTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HouseHoldToService extends CrudService<HouseholdTo, Integer> {
    private final int LIMIT = 1000;

    public List<HouseholdTo> getHouseHoldByRegionId(String idSession, List<String> regionIds) {
        Session session = getSession(idSession);
        try {
            Criteria criteria = session.createCriteria(entityClass);
            criteria.add((Restrictions.in("regionId", regionIds)));
            List<HouseholdTo> list = (List<HouseholdTo>) criteria.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.close();
            return new ArrayList<>();
        }
    }

    public Map<String, Integer> getMappingToIDByRegionId(String idSession, List<String> regionIds) {
        Map<String, Integer> map = new HashMap<>();
        List<HouseholdTo> addressTos = getHouseHoldByRegionId(idSession, regionIds);
        addressTos.forEach(householdTo -> {
            String key = householdTo.getRegionId() + "_" + householdTo.getHouseholdId();
            map.put(key, householdTo.getId());
        });
        return map;
    }
}
