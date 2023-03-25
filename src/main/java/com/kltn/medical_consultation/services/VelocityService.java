package com.kltn.medical_consultation.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.stereotype.Service;

import java.io.StringWriter;
import java.util.Map;

/**
 * Velocity service.
 */
public class VelocityService {
    private static final Logger LOGGER = LogManager.getLogger(VelocityService.class);
    private final VelocityEngine engine;


    public VelocityService(VelocityEngine engine) {
        this.engine = engine;
    }


    public String mergeTemplate(String templatePath, Map<String, Object> model) {

        try {
            VelocityContext context = new VelocityContext(model);
            StringWriter writer = new StringWriter();
            engine.mergeTemplate(templatePath, "UTF-8", context, writer);
            String result = writer.toString();
            writer.close();
            return result;
        } catch (Exception e) {
            LOGGER.error("load template fail ", e);
        }

        return templatePath;
    }

}
