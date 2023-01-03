package com.petfound.backend.configuration;

import com.petfound.backend.Utils.AuthFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

@Configuration
public class FilterOrderConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean1(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new AuthFilter());
        filterRegistrationBean.addUrlPatterns("/api/*");
        //order的数值越小 则优先级越高,这里直接使用的最高优先级
        filterRegistrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return filterRegistrationBean;
    }
}
