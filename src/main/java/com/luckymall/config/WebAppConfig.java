package com.luckymall.config;

import com.luckymall.interceptor.AuthorityInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 路径拦截
 * @Author: wangming.chen
 * @Date: 2019/8/20 11:11
 */
@Configuration
public class WebAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加排除拦截的路径
        String[] excludes = new String[]{"/", "/login", "/notLogin", "/static/**",
                "/templates/**", "/index", "/user/loginUser", "/user/registerUser", "/error/**",
                "/user/hasUsername", "/register", "/productType/listType", "/product/**",
                "/cart/addToCart", "/order/buy", "/admin/login", "/admin/loginAdmin", "/productType/**",
                "/product/**", "/user/admin/**", "/order/admin/**", "/score/**"};
        registry.addInterceptor(new AuthorityInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
    }
}
