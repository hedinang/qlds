package vn.byt.qlds.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "qlds")
public class YamlConfig {
    private List<String> databases = new ArrayList<>();
    private String urlES;

    public String getUrlES() {
        return urlES;
    }

    public void setUrlES(String urlES) {
        this.urlES = urlES;
    }

    public List<String> getDatabases() {
        return databases;
    }

    public void setDatabases(List<String> databases) {
        this.databases = databases;
    }
}
