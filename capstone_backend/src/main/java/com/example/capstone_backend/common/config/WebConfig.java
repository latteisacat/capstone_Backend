package com.example.capstone_backend.common.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    //todo: 나중에 전체 허용 해제할것.
    @Override
    public void addCorsMappings(final CorsRegistry corsRegistry){
        corsRegistry.addMapping("/**");
    }
}
