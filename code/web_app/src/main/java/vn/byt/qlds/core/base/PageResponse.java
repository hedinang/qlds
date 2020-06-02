package vn.byt.qlds.core.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageResponse<T> implements Serializable {
    private List<T> list;
    private int page;
    private int total;
    private int limit;
}
