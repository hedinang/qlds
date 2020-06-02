package vn.byt.qlds.configuration;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties("spring")
public class RabbitConfig {
    private RabbitProperties rabbitmq;

    public String getHost(){
        return rabbitmq.getHost();
    }

    public int getPort(){
        return rabbitmq.getPort();
    }

    public String getUserName(){
        return rabbitmq.getUsername();
    }

    public String getPassword(){
        return rabbitmq.getPassword();
    }
}
