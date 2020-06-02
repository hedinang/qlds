package vn.byt.qlds.config;

import vn.byt.qlds.core.QldsRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
public class QldsConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public QldsRestTemplate qldsRestTemplate() {
        return new QldsRestTemplate();
    }
    @Bean
    public InternalResourceViewResolver defaultViewResolver() {
        return new InternalResourceViewResolver();
    }
}
