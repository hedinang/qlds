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
import vn.byt.qlds.model.HouseHold;
import vn.byt.qlds.model.response.PageResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class HouseHoldService extends CrudService<HouseHold, Integer> {
    @Autowired
    ElasticSearchService elasticSearchService;
    public static final String index = "house-hold";

    public List<HouseHold> getHouseHoldByAddressId(String idSession, int addressId) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        Criterion criterionIsDeleted = Restrictions.eq("isDeleted", false);
        Criterion criterionId = Restrictions.eq("addressId", addressId);
        LogicalExpression andExp = Restrictions.and(criterionId, criterionIsDeleted);
        criteria.add(andExp);
        List<HouseHold> result = (List<HouseHold>) criteria.list();
        session.close();
        return result;
    }

    public List findAll(Map<String, Object> request) throws IOException {
        List response = elasticSearchService.findAll(index, request, ConfigSource.exclude_source_house_hold);
        return (List) response.stream().map(this::convert).collect(Collectors.toList());

    }

    public PageResponse findPage(Map<String, Object> request) throws IOException {
        PageResponse response = elasticSearchService.findPaging(index, request, ConfigSource.exclude_source_house_hold);
        List items = (List) response.getList().stream().map(this::convert).collect(Collectors.toList());
        response.setList(items);
        return response;
    }

    public int count(Map<String, Object> request) throws IOException {
        return elasticSearchService.count(index, request);
    }

    private LinkedHashMap<String, Object> convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long startDate = (Long) tmp.get("startDate");
        Long endDate = (Long) tmp.get("endDate");
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("startDate", StringUtils.convertLongToTime(startDate));
        tmp.put("endDate", StringUtils.convertLongToTime(endDate));
        tmp.put("timeCreated", StringUtils.convertLongToTime(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTime(timeLastUpdated));
        return tmp;
    }

}