package vn.byt.qlds;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = WebMvcAutoConfiguration.class)
@EnableWebMvc
public class WebAppApplication {

    public static void main(String[] args) {
        System.out.println("Working Directory = " +
                System.getProperty("user.dir"));
        SpringApplication.run(WebAppApplication.class, args);
    }
}
