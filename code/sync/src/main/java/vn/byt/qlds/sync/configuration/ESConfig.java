package vn.byt.qlds.sync.configuration;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AbstractFactoryBean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

@Configuration
public class ESConfig extends AbstractFactoryBean<RestHighLevelClient> {
    private RestHighLevelClient restHighLevelClient;

    @Override
    public Class<RestHighLevelClient> getObjectType() {
        return RestHighLevelClient.class;
    }

    @Autowired
    YamlConfig yamlConfig;

    @NotNull
    @Override
    protected RestHighLevelClient createInstance() {
        try {
            restHighLevelClient = new RestHighLevelClient(
                    RestClient.builder(
                            new HttpHost(
                                    yamlConfig.getHostES(),
                                    yamlConfig.getPortES(),
                                    yamlConfig.getProtocol()))
            );
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return restHighLevelClient;
    }

    @Override
    public void destroy() {
        try {
            restHighLevelClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}