package vn.byt.qlds.ministry.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.byt.qlds.ministry.model.UnitCategory;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UnitCategoryRequest extends UnitCategory {
    public PageRequest pageRequest;
}
