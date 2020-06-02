package vn.byt.qlds.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import vn.byt.qlds.model.*;

import java.util.Properties;

public class Connector {
    SessionFactory sessionFactory;

    public Connector(Properties properties) {
        this.sessionFactory = initConfig(properties).buildSessionFactory();
    }

    private Configuration initConfig(Properties properties) {
        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        configuration.addAnnotatedClass(Collaborator.class);
        configuration.addAnnotatedClass(Address.class);
        configuration.addAnnotatedClass(HouseHold.class);
        configuration.addAnnotatedClass(Person.class);
        configuration.addAnnotatedClass(PersonHealthy.class);
        configuration.addAnnotatedClass(PersonHistory.class);
        configuration.addAnnotatedClass(RegionChange.class);
        configuration.addAnnotatedClass(FamilyPlanning.class);
        configuration.addAnnotatedClass(FamilyPlanningHistory.class);
        configuration.addAnnotatedClass(Report.class);
        configuration.addAnnotatedClass(LogAction.class);
        configuration.addAnnotatedClass(Operationstatisticinfor.class);
        configuration.addAnnotatedClass(UnitParam.class);
        return configuration;
    }


}
