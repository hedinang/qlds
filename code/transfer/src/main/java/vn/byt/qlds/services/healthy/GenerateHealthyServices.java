package vn.byt.qlds.services.healthy;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Service;
import vn.byt.qlds.config.db.CrudService;
import vn.byt.qlds.entity_from.GeneratehealthFrom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GenerateHealthyServices extends CrudService<GeneratehealthFrom, Integer> {
    static class RegionCount {
        public Long total;
        public String regionId;

        public RegionCount(Long total, String regionId) {
            this.total = total;
            this.regionId = regionId;
        }
    }

    public List<GeneratehealthFrom> getAllByListRegionId(String idSession, List<String> regionIds) {
        Session session = getSession(idSession);
        try {
            Criteria criteria = session.createCriteria(entityClass);
            criteria.add(Restrictions.in("regionId", regionIds));
            List<GeneratehealthFrom> list = criteria.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.close();
            return new ArrayList<>();
        }
    }
//    public List<GeneratehealthFrom> getAllByListRegionId(String idSession, List<String> regionIds) {
//        Session session = getSession(idSession);
//        try {
//            String inRegions = regionIds
//                    .toString()
//                    .replace("[", "")
//                    .replace("]", "");
//            String sql = "SELECT * FROM "+idSession+".generatehealth where Region_ID IN ("+inRegions+")";
//            SQLQuery query = session.createSQLQuery(sql);
//            query.addEntity(entityClass);
//            List<GeneratehealthFrom> list = query.list();
//            session.close();
//            return list;
//        } catch (Exception e) {
//            session.close();
//            return new ArrayList<>();
//        }
//    }

    public List<String> getAllRegionIdOfGenerate(String idSession) {
        Session session = getSession(idSession);
        try {
            String sql = "SELECT count(1) as total, GH.Region_ID FROM " + idSession + "." + "generatehealth as GH group by Region_ID";
            SQLQuery query = session.createSQLQuery(sql);
            List<GenerateHealthyServices.RegionCount> results = query
                    .unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public GenerateHealthyServices.RegionCount transformTuple(Object[] tuple, String[] aliases) {
                            return new GenerateHealthyServices.RegionCount(
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
