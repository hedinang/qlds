package vn.byt.qlds.services.change;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.ResultTransformer;
import org.springframework.stereotype.Service;
import vn.byt.qlds.config.db.CrudService;
import vn.byt.qlds.entity_from.ChangeFrom;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChangeServices extends CrudService<ChangeFrom, Integer> {
    public List<ChangeFrom> getAllByListRegionId(String idSession, List<String> regionIds) {
        Session session = getSession(idSession);
        try {
            Criteria criteria = session.createCriteria(entityClass);
            criteria.add(Restrictions.in("regionId", regionIds));
            List<ChangeFrom> list = (List<ChangeFrom>) criteria.list();
            session.close();
            return list;
        } catch (Exception e) {
            session.close();
            System.out.printf(e.getMessage());
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

    public List<String> getListRegionOfChange(String idSession) {
        Session session = getSession(idSession);
        try {
            String sql = "SELECT count(1) as total, C.Region_ID FROM " + idSession + "." + "change_history as C group by Region_ID";
            SQLQuery query = session.createSQLQuery(sql);
            List<RegionCount> results = query
                    .unwrap(org.hibernate.query.Query.class)
                    .setResultTransformer(new ResultTransformer() {
                        @Override
                        public RegionCount transformTuple(Object[] tuple, String[] aliases) {
                            return new RegionCount(
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
