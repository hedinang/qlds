package vn.byt.qlds.sync.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.byt.qlds.sync.core.sql.DialectSQL;
import vn.byt.qlds.sync.core.sql.PropertiesFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class ConfigurationBean {
    private List<String> databases = new ArrayList<>();
    @Autowired
    YamlConfig yamlConfig;
    @Bean
    public ConnectorManager createConnectorManager() {
        ConnectorManager connectorManager = new ConnectorManager();
        databases.addAll(yamlConfig.getDatabases());
        for (String database : databases) {
            Properties properties = PropertiesFactory.createProperties(DialectSQL.MYSQL, database);
            Connector connector = new Connector(properties);
            connectorManager.addConnector(database, connector);
        }
        return connectorManager;
    }

    @Bean
    public QldsRestTemplate qldsRestTemplate() {
        return new QldsRestTemplate();
    }

}

