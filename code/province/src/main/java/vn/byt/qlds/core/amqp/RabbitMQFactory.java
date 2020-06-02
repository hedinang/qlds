package vn.byt.qlds.core.amqp;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;

public class RabbitMQFactory {
    public ConnectionFactory connectionFactory(String host, Integer port, String username, String password) {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }
}
