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
import vn.byt.qlds.model.PersonHealthy;
import vn.byt.qlds.model.response.PageResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PersonHealthyService extends CrudService<PersonHealthy, Integer> {
    @Autowired
    ElasticSearchService elasticSearchService;
    private static final String index = "person-healthy";

    public List<PersonHealthy> getPersonByPersonId(String idSession, int motherId) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        Criterion criterionIsDeleted = Restrictions.eq("isDeleted", false);
        Criterion criterionId = Restrictions.eq("personalId", motherId);
        LogicalExpression andExp = Restrictions.and(criterionId, criterionIsDeleted);
        criteria.add(andExp);
        List<PersonHealthy> l = (List<PersonHealthy>) criteria.list();
        session.close();
        return l;
    }

    public List findAll(Map<String, Object> request) throws IOException {
        List response = elasticSearchService.findAll(index, request);
        return (List) response.stream().map(this::convert).collect(Collectors.toList());

    }

    public PageResponse findPage(Map<String, Object> request) throws IOException {
        PageResponse response = elasticSearchService.findPaging(index, request);
        List items = (List) response.getList().stream().map(this::convert).collect(Collectors.toList());
        response.setList(items);
        return response;
    }

    private LinkedHashMap<String, Object> convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long dateUpdate = (Long) tmp.get("dateUpdate");
        Long genDate = (Long) tmp.get("genDate");
        Long createdDate = (Long) tmp.get("createdDate");
        Long dateSlss = (Long) tmp.get("dateSlss");
        Long dateSlts1 = (Long) tmp.get("dateSlts1");
        Long dateSlts2 = (Long) tmp.get("dateSlts2");
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("dateUpdate", StringUtils.convertLongToTime(dateUpdate));
        tmp.put("genDate", StringUtils.convertLongToTime(genDate));
        tmp.put("createdDate", StringUtils.convertLongToTime(createdDate));
        tmp.put("dateSlss", StringUtils.convertLongToTime(dateSlss));
        tmp.put("dateSlts1", StringUtils.convertLongToTime(dateSlts1));
        tmp.put("dateSlts2", StringUtils.convertLongToTime(dateSlts2));
        tmp.put("timeCreated", StringUtils.convertLongToTime(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTime(timeLastUpdated));
        tmp.remove("genInAge");
        return tmp;
    }
}
