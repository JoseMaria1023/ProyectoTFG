package com.jve.proyecto.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import jakarta.servlet.MultipartConfigElement;

@Configuration
@EnableWebMvc
public class WebConfig {
    public MultipartConfigElement multipartConfigElement() {
        return new MultipartConfigElement("");
    }
}