package vn.byt.qlds.ministry.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import vn.byt.qlds.ministry.model.*;

import java.util.Properties;

public class Connector {
    SessionFactory sessionFactory;

    public Connector(Properties properties) {
        this.sessionFactory = initConfig(properties).buildSessionFactory();
    }

    private Configuration initConfig(Properties properties) {
        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Gender.class);
        configuration.addAnnotatedClass(Account.class);
        configuration.addAnnotatedClass(AreaCategory.class);
        configuration.addAnnotatedClass(UnitLevelCategory.class);
        configuration.addAnnotatedClass(UnitCategory.class);
        configuration.addAnnotatedClass(AccountRole.class);
        configuration.addAnnotatedClass(PermissionCategory.class);
        configuration.addAnnotatedClass(RolePermission.class);
        configuration.addAnnotatedClass(ContraceptiveCategory.class);
        configuration.addAnnotatedClass(DeadCategory.class);
        configuration.addAnnotatedClass(DisabilityCategory.class);
        configuration.addAnnotatedClass(EducationCategory.class);
        configuration.addAnnotatedClass(MaritalStatus.class);
        configuration.addAnnotatedClass(Nationality.class);
        configuration.addAnnotatedClass(NationCategory.class);
        configuration.addAnnotatedClass(Notification.class);
        configuration.addAnnotatedClass(ReasonChange.class);
        configuration.addAnnotatedClass(RegionChange.class);
        configuration.addAnnotatedClass(RegionChangeType.class);
        configuration.addAnnotatedClass(Relationship.class);
        configuration.addAnnotatedClass(ReportDesign.class);
        configuration.addAnnotatedClass(Report.class);
        configuration.addAnnotatedClass(ResidenceStatus.class);
        configuration.addAnnotatedClass(TechnicalCategory.class);
        configuration.addAnnotatedClass(UserGroupCategory.class);
        configuration.addAnnotatedClass(TransferPerson.class);
        configuration.addAnnotatedClass(TransferAddress.class);
        configuration.addAnnotatedClass(TransferHouseHold.class);
        configuration.addAnnotatedClass(SeparationHouseHold.class);
        configuration.addAnnotatedClass(SystemParams.class);
        return configuration;
    }


}
