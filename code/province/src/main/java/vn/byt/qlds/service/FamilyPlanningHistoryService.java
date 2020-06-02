package vn.byt.qlds.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.es.ConfigSource;
import vn.byt.qlds.core.es.ElasticSearchService;
import vn.byt.qlds.core.sql.CrudService;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.FamilyPlanningHistory;
import vn.byt.qlds.model.response.PageResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FamilyPlanningHistoryService extends CrudService<FamilyPlanningHistory, Integer> {

    @Autowired
    ElasticSearchService elasticSearchService;
    public static final String index = "family-planning-history";

    public List<FamilyPlanningHistory> getAllByPersonId(String idSession, int personId) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        Criterion criterionIsDeleted = Restrictions.eq("isDeleted", false);
        Criterion criterionId = Restrictions.eq("personalId", personId);
        LogicalExpression andExp = Restrictions.and(criterionId, criterionIsDeleted);
        criteria.add(andExp);
        List<FamilyPlanningHistory> f = (List<FamilyPlanningHistory>) criteria.list();
        session.close();
        return f;
    }

    public List findAll(Map<String, Object> request) throws IOException {
        List response = elasticSearchService.findAll(index, request, ConfigSource.exclude_source_family_plan_history);
        return (List) response.stream().map(this::convert).collect(Collectors.toList());

    }

    public PageResponse findPage(Map<String, Object> request) throws IOException {
        PageResponse response = elasticSearchService.findPaging(index, request, ConfigSource.exclude_source_family_plan_history);
        List items = (List) response.getList().stream().map(this::convert).collect(Collectors.toList());
        response.setList(items);
        return response;
    }

    private LinkedHashMap<String, Object> convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long dateUpdate = (Long) tmp.get("dateUpdate");
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("dateUpdate", StringUtils.convertLongToTime(dateUpdate));
        tmp.put("timeCreated", StringUtils.convertLongToTime(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTime(timeLastUpdated));
        return tmp;
    }
}
