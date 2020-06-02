package vn.byt.qlds.model._province.family_plan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FamilyPlanningHistoryResponse {
    public FamilyPlanningHistory familyPlanningHistory;
    public String contraceptiveName;
}
