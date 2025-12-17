package com.reparaya.config;

import jakarta.annotation.PostConstruct;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.tomcat.servlet.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TomcatConfig {

    @PostConstruct
    public void test() {
        System.out.println(">>> TOMCAT CONFIG ACTIVA <<<");
    }

    @Bean
    public WebServerFactoryCustomizer<TomcatServletWebServerFactory> containerCustomizer() {
        return factory -> factory.addConnectorCustomizers((Connector connector) -> {
            connector.setProperty("maxParameterCount", "-1");
            connector.setProperty("maxPostSize", "-1");
            connector.setProperty("maxSwallowSize", "-1");
        });
    }
}
