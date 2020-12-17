package com.cy.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.imageio.spi.ServiceRegistry;
import javax.servlet.Servlet;
import javax.sql.DataSource;

/**
 * @author 86158
 */
@Configuration
public class DruidConfig {
    @ConfigurationProperties(prefix = "druid.datasource")
    @Bean
    public DataSource getDataSource() {
        return new DruidDataSource();
    }
    // 配置监控
    // 1、配置一个
    public ServletRegistrationBean servletRegistrationBean(){
        ServletRegistrationBean<Servlet> registrationBean = new ServletRegistrationBean<>(new Servlet);

        registrationBean.setInitParameters(map);
    }
}
