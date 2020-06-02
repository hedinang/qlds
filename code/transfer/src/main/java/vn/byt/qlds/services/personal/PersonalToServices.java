package vn.byt.qlds.services.personal;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Service;
import vn.byt.qlds.config.db.CrudService;
import vn.byt.qlds.entity_to.PersonalTo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class PersonalToServices extends CrudService<PersonalTo, Integer> {
    public List<PersonMapping> getPersonByRegionId(String idSession, List<String> regionIds) {
        Session session = getSession(idSession);
        try {
            String inRegions = regionIds
                    .toString()
                    .replace("[", "")
                    .replace("]", "");
            String sql = "SELECT P.id, P.region_id, P.personal_id FROM " + idSession + "." + "personal  as P where region_id in ("+inRegions+")";
            SQLQuery query = session.createSQLQuery(sql);
            List<PersonMapping> results = query
                    .unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public PersonMapping transformTuple(Object[] tuple, String[] aliases) {
                            return new PersonMapping(
                                    ((Number) tuple[0]).intValue(),
                                    (String) tuple[1],
                                    ((Number) tuple[2]).intValue());
                        }

                        @Override
                        public List transformList(List list) {
                            return list;
                        }
                    }).getResultList();

            session.close();
            return results;
        } catch (Exception e) {
            session.close();
            return new ArrayList<>();
        }
    }

    public Map<String, Integer> getMappingToIdByRegionId(String idSession, List<String> regionIds) {
        Map<String, Integer> map = new HashMap<>();
        List<PersonMapping> addressTos = getPersonByRegionId(idSession, regionIds);
        addressTos.forEach(personalTo -> {
            String key = personalTo.regionId + "_" + personalTo.personalId;
            map.put(key, personalTo.id);
        });
        return map;
    }

    public static class PersonMapping {
        public Integer id;
        public String regionId;
        public Integer personalId;

        public PersonMapping(Integer id, String regionId, Integer personalId) {
            this.id = id;
            this.regionId = regionId;
            this.personalId = personalId;
        }
    }

}
