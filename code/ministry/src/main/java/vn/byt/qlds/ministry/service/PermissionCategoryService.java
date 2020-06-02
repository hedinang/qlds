package vn.byt.qlds.ministry.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.ministry.core.es.ElasticSearchService;
import vn.byt.qlds.ministry.core.sql.CrudService;
import vn.byt.qlds.ministry.core.utils.StringUtils;
import vn.byt.qlds.ministry.model.PermissionCategory;
import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class PermissionCategoryService extends CrudService<PermissionCategory, Integer> {
    @Autowired
    ElasticSearchService elasticSearchService;
    private final static String index = "permission";
    ObjectMapper objectMapper = new ObjectMapper();

    public PermissionCategory getPermissionCategoriesByID(String idSession, int id) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(PermissionCategory.class);
        criteria.add((Restrictions.eq("id", id)));
        PermissionCategory permissionCategorie = (PermissionCategory) criteria.uniqueResult();
        session.close();
        return permissionCategorie;
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

    private PermissionCategory convert(Object item) {
        LinkedHashMap<String, Object> tmp = (LinkedHashMap<String, Object>) item;
        Long timeCreated = (Long) tmp.get("timeCreated");
        Long timeLastUpdated = (Long) tmp.get("timeLastUpdated");
        tmp.put("timeCreated", StringUtils.convertLongToTimestamp(timeCreated));
        tmp.put("timeLastUpdated", StringUtils.convertLongToTimestamp(timeLastUpdated));
        return objectMapper.convertValue(tmp, PermissionCategory.class);
    }
}
