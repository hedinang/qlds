package vn.byt.qlds.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import vn.byt.qlds.entity_from.*;
import vn.byt.qlds.entity_to.*;


import java.util.Properties;

public class Connector {
    SessionFactory sessionFactory;

    public Connector(Properties properties) {
        this.sessionFactory = initConfigTo(properties).buildSessionFactory();
    }

    public Connector(Properties properties, boolean from){
        /*from*/
        this.sessionFactory = initConfigFrom(properties).buildSessionFactory();
    }


    private Configuration initConfigFrom(Properties properties) {
        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        /*to*/
        configuration.addAnnotatedClass(AddressFrom.class);
        configuration.addAnnotatedClass(GeneratehealthFrom.class);
        configuration.addAnnotatedClass(FamilyplanningFrom.class);
        configuration.addAnnotatedClass(FamilyplanninghistoryFrom.class);
        configuration.addAnnotatedClass(FieldworkerFrom.class);
        configuration.addAnnotatedClass(HouseholdFrom.class);
        configuration.addAnnotatedClass(PersonalFrom.class);
        configuration.addAnnotatedClass(ChangeFrom.class);
        return configuration;
    }

    private Configuration initConfigTo(Properties properties) {
        Configuration configuration = new Configuration();
        configuration.setProperties(properties);
        /*to*/
        configuration.addAnnotatedClass(AddressTo.class);
        configuration.addAnnotatedClass(CollaboratorTo.class);
        configuration.addAnnotatedClass(FamilyPlanningTo.class);
        configuration.addAnnotatedClass(FamilyPlanningHistoryTo.class);
        configuration.addAnnotatedClass(GenerateHealthyTo.class);
        configuration.addAnnotatedClass(HouseholdTo.class);
        configuration.addAnnotatedClass(PersonalTo.class);
        configuration.addAnnotatedClass(PersonalHistoryTo.class);
        return configuration;
    }


}
