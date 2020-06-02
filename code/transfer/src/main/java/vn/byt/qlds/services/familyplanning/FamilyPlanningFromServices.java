package vn.byt.qlds.services.familyplanning;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Service;
import vn.byt.qlds.config.db.CrudService;
import vn.byt.qlds.entity_from.FamilyplanningFrom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FamilyPlanningFromServices extends CrudService<FamilyplanningFrom, Integer> {

    public List<FamilyplanningFrom> getAllByListRegionId(String idSession, List<String> regionIds) {
        Session session = getSession(idSession);
        try {
            String inRegions = regionIds
                    .toString()
                    .replace("[", "")
                    .replace("]", "");
            String sql = "SELECT * FROM "+idSession+".familyplanning where Region_ID IN ("+inRegions+")";
            SQLQuery query = session.createSQLQuery(sql);
            query.addEntity(entityClass);
            List<FamilyplanningFrom> list = query.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.close();
            return new ArrayList<>();
        }
    }

    static class RegionCount {
        public Long total;
        public String regionId;

        public RegionCount(Long total, String regionId) {
            this.total = total;
            this.regionId = regionId;
        }
    }

    public List<String> getListRegionOfFamilyPlanning(String idSession) {
        Session session = getSession(idSession);
        try {
            String sql = "SELECT count(1) as total, FP.Region_ID FROM " + idSession + "." + "familyplanning as FP group by Region_ID";
            SQLQuery query = session.createSQLQuery(sql);
            List<FamilyPlanningFromServices.RegionCount> results = query
                    .unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public FamilyPlanningFromServices.RegionCount transformTuple(Object[] tuple, String[] aliases) {
                            return new FamilyPlanningFromServices.RegionCount(
                                    ((Number) tuple[0]).longValue(),
                                    (String) tuple[1]);
                        }

                        @Override
                        public List transformList(List list) {
                            return list;
                        }
                    }).getResultList();
            List<String> regions = results
                    .stream()
                    .map(row -> row.regionId)
                    .collect(Collectors.toList());
            session.close();
            return regions;
        } catch (Exception e) {
            session.close();
            return new ArrayList<>();
        }
    }
}
