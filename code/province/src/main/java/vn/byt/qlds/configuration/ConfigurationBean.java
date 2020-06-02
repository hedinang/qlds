package vn.byt.qlds.configuration;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import vn.byt.qlds.core.amqp.RabbitMQFactory;
import vn.byt.qlds.core.sql.DialectSQL;
import vn.byt.qlds.core.sql.PropertiesFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class ConfigurationBean {
    private List<String> provinces = new ArrayList<>();
    @Autowired
    YamlConfig yamlConfig;
    @Autowired
    RabbitProperties rabbitProperties;

    @Bean
    public ConnectorManager createConnectorManager() {
        ConnectorManager connectorManager = new ConnectorManager();
        provinces.addAll(yamlConfig.getDatabases());
        for (String database : provinces) {
            Properties properties = PropertiesFactory.createProperties(DialectSQL.MYSQL, database);
            Connector connector = new Connector(properties);
            connectorManager.addConnector(database, connector);
            System.out.println("Connected to database "+ database);
        }
        return connectorManager;
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitMQFactory rabbitMQFactory = new RabbitMQFactory();
        ConnectionFactory connectionFactory = rabbitMQFactory.connectionFactory(
                rabbitProperties.getHost(),
                rabbitProperties.getPort(),
                rabbitProperties.getUsername(),
                rabbitProperties.getPassword()
        );
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public ESRestTemplate restTemplate() {
        return new ESRestTemplate();
    }

}

