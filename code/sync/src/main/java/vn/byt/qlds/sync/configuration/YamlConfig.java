package vn.byt.qlds.sync.configuration;

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
    private String hostES;
    private int portES;

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    private String protocol;

    public String getHostES() {
        return hostES;
    }

    public void setHostES(String hostES) {
        this.hostES = hostES;
    }

    public int getPortES() {
        return portES;
    }

    public void setPortES(int portES) {
        this.portES = portES;
    }

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
