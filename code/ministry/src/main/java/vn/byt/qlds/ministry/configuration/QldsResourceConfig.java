package vn.byt.qlds.ministry.configuration;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import vn.byt.qlds.ministry.endpoint.*;

@Configuration
public class QldsResourceConfig extends ResourceConfig {
    public QldsResourceConfig() {
        register(AccountEndpoint.class);
        register(AccountRoleEndpoint.class);
        register(AreaCategoryEndpoint.class);
        register(ContraceptiveCategoryEndpoint.class);
        register(DeadCategoryEndpoint.class);
        register(DisabilityCategoryEndpoint.class);
        register(EducationCategoryEndpoint.class);
        register(GenderEndpoint.class);
        register(MaritalStatusEndpoint.class);
        register(NationalityEndpoint.class);
        register(NationCategoryEndpoint.class);
        register(NotificationEndpoint.class);
        register(PermissionCategoryEndpoint.class);
        register(ReasonChangeEndpoint.class);
        register(RegionChangeEndpoint.class);
        register(RegionChangeTypeEndpoint.class);
        register(RelationshipEndpoint.class);
        register(ReportDesignEndpoint.class);
        register(ReportEndpoint.class);
        register(ResidenceStatusEndpoint.class);
        register(RolePermissionEndpoint.class);
        register(SeparationHouseHoldEndpoint.class);
        register(TechnicalCategoryEndpoint.class);
        register(TransferAddressEndpoint.class);
        register(TransferHouseHoldEndpoint.class);
        register(TransferPersonEndpoint.class);
        register(UnitCategoryEndpoint.class);
        register(UnitLevelCategoryEndpoint.class);
        register(UserGroupCategoryEndpoint.class);
    }

}


