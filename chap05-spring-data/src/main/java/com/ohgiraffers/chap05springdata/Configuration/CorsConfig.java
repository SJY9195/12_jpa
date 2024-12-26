package com.ohgiraffers.chap05springdata.Configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class CorsConfig implements WebMvcConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            // 모든 요청에 대해 CORS 설정을 추가
            registry.addMapping("/**")
                    .allowedOrigins("*") // 허용할 출처 설정
                    .allowedMethods("GET", "POST", "PUT", "DELETE") // 허용할 HTTP 메서드 설정
                    .allowedHeaders("*"); // 허용할 헤더 설정
        }
    }
