package vn.byt.qlds.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.byt.qlds.config.db.DialectSQL;
import vn.byt.qlds.config.db.PropertiesFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class ConfigurationBean {
    private List<String> databaseFrom = new ArrayList<>();
    private List<String> databaseTo = new ArrayList<>();
    @Autowired
    YamlConfig yamlConfig;

    @Bean
    public ConnectorManager createConnectorManager() {
        ConnectorManager connectorManager = new ConnectorManager();
        databaseFrom.addAll(yamlConfig.getFrom());
        databaseTo.addAll(yamlConfig.getDatabases());

        for (String database : databaseFrom) {
            Properties properties = PropertiesFactory.createProperties(DialectSQL.MYSQL, database);
            Connector connector = new Connector(properties, true);
            connectorManager.addConnector(database, connector);
            System.out.println("Connected from database " + database);
        }

        for (String database : databaseTo) {
            Properties properties = PropertiesFactory.createProperties(DialectSQL.MYSQL, database);
            Connector connector = new Connector(properties);
            connectorManager.addConnector(database, connector);
            System.out.println("Connected to database " + database);
        }

        return connectorManager;
    }

}

