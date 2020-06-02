package vn.byt.qlds.ministry.configuration;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import vn.byt.qlds.ministry.core.amqp.RabbitMQFactory;
import vn.byt.qlds.ministry.core.sql.DialectSQL;
import vn.byt.qlds.ministry.core.sql.PropertiesFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

@Configuration
public class ConfigurationBean {
    private List<String> databases = new ArrayList<>();
    @Autowired
    RabbitProperties rabbitProperties;
    @Bean
    public ConnectorManager createConnectorManager() {
        ConnectorManager connectorManager = new ConnectorManager();
        databases.add("common");
        for (String database : databases) {
            Properties properties = PropertiesFactory.createProperties(DialectSQL.MYSQL, database);
            Connector connector = new Connector(properties);
            connectorManager.addConnector(database, connector);
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
    public QldsRestTemplate qldsRestTemplate() {
        return new QldsRestTemplate();
    }

    @Bean
    public ESRestTemplate restTemplate() {
        return new ESRestTemplate();
    }

    @Bean
    RestHighLevelClient client() {

        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("172.16.9.4:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

}


