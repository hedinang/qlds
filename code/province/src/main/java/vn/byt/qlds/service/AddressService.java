package vn.byt.qlds.service;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.byt.qlds.core.es.ElasticSearchService;
import vn.byt.qlds.core.sql.CrudService;
import vn.byt.qlds.core.utils.StringUtils;
import vn.byt.qlds.model.Address;
import vn.byt.qlds.model.request.AddressRequest;
import vn.byt.qlds.model.response.PageResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class AddressService extends CrudService<Address, Integer> {
    @Autowired
    ElasticSearchService elasticSearchService;
    private static final String index = "address";

    public List<Address> searchListAddress(String idSession, AddressRequest addressRequest) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        List<Criterion> listCriterion = new ArrayList<>();
        criteria.add(Restrictions.eq("isDeleted", false));
        if (addressRequest.getAddressIdOld() != null) {
            criteria.add(Restrictions.eq("addressIdOld", addressRequest.getAddressIdOld()));
        }
        if (addressRequest.getCollaboratorId() != null) {
            criteria.add(Restrictions.eq("collaboratorId", addressRequest.getCollaboratorId()));
        }
        if (addressRequest.getExportStatus() != null) {
            criteria.add(Restrictions.eq("exportStatus", addressRequest.getExportStatus()));
        }
        if (addressRequest.getNotes() != null) {
            criteria.add(Restrictions.eq("notes", addressRequest.getNotes()));
        }
        if (addressRequest.getId() != null) {
            criteria.add(Restrictions.eq("id", addressRequest.getId()));
        }
        if (addressRequest.getLevels() != null) {
            criteria.add(Restrictions.eq("levels", addressRequest.getLevels()));
        }
        if (addressRequest.getRegionId() != null) {
            criteria.add(Restrictions.eq("regionId", addressRequest.getRegionId()));
        }
        if (addressRequest.getParent() != null) {
            criteria.add(Restrictions.eq("parent", addressRequest.getParent()));
        }
        if (addressRequest.getName() != null) {
            criteria.add(Restrictions.eq("name", addressRequest.getName()));
        }
        if (addressRequest.getFullName() != null) {
            criteria.add(Restrictions.eq("fullName", addressRequest.getFullName()));
        }
        List<Address> list = criteria.list();
        session.close();
        return list;
    }

    public List<Address> getAddressByRegionId(String idSession, String code) {
        Session session = getSession(idSession);
        Criteria criteria = session.createCriteria(entityClass);
        Criterion criterionIsDeleted = Restrictions.eq("isDeleted", false);
        Criterion criterionId = Restrictions.eq("regionId", code);
        LogicalExpression andExp = Restrictions.and(criterionId, criterionIsDeleted);
        criteria.add(andExp);
        List<Address> list = criteria.list();
        session.close();
        return list;
    }

    public List findAllAddress(Map<String, Object> request) throws IOException {
        List response = elasticSearchService.findAll(index, request);
        return (List) response.stream().map(this::convert).collect(Collectors.toList());

    }

    public PageResponse findPageAddress(Map<String, Object> request) throws IOException {
        PageResponse response = elasticSearchService.findPaging(index, request);
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
