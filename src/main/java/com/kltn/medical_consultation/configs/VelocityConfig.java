package com.kltn.medical_consultation.configs;

import com.kltn.medical_consultation.services.VelocityService;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Properties;

/**
 * VelocityConfiguration.
 *
 */

@Configuration
@PropertySource("classpath:application.properties")
public class VelocityConfig {
    @Bean
    public VelocityEngine velocityEngine() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("input.encoding", "UTF-8");
        properties.setProperty("output.encoding", "UTF-8");
        properties.setProperty("resource.loader", "class");
        properties.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        VelocityEngine velocityEngine = new VelocityEngine(properties);
        return velocityEngine;
    }

    @Bean
    public VelocityService velocityServiceMail(VelocityEngine velocityEngine) {
        return new VelocityService(velocityEngine);
    }
}
