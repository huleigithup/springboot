package com.cy;

import com.cy.config.DruidConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author 86158
 */
@EnableConfigurationProperties({DruidConfig.class})
@SpringBootApplication
public class SpringbootDatasourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDatasourceApplication.class, args);
    }

}
