package vn.byt.qlds.ministry.core.es;

import vn.byt.qlds.ministry.model.response.PageResponse;

import java.io.IOException;
import java.io.Serializable;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface CrudService<T, ID extends Serializable> {
    public T read(ID id) throws IOException, ParseException;

    public List<T> getAll(Map<String, Object> query) throws IOException, ParseException;

    public PageResponse<T> getPage(Map<String, Object> query) throws IOException, ParseException;

    public Integer count(Map<String, Object> query) throws IOException;

}
