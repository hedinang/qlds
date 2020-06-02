package vn.byt.qlds.configuration;


import io.swagger.jaxrs.config.BeanConfig;
import io.swagger.jaxrs.listing.ApiListingResource;
import io.swagger.jaxrs.listing.SwaggerSerializers;
import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.ws.rs.Path;
import java.util.Set;


@Component
public class QldsResourceConfig extends ResourceConfig {
    @Value("${spring.jersey.application-path:/}")
    private String apiPath;

    public QldsResourceConfig() {
        registerEndpoints();
    }

    private void registerEndpoints() {
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setUrls(ClasspathHelper.forPackage("vn.byt.qlds"))
                .setScanners(new TypeAnnotationsScanner()));

        Set<Class<?>> endpoints = reflections.getTypesAnnotatedWith(Path.class, true);
        register(MultiPartFeature.class);

        for (Class<?> endpoint : endpoints) {
            register(endpoint);
        }
    }
}


