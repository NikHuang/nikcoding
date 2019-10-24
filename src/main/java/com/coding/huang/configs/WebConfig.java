package com.coding.huang.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * Created by Administrator on 2019/10/22.
 */
@Configuration
@ComponentScan
public class WebConfig implements WebMvcConfigurer{


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        //参数处理

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器

    }



}
