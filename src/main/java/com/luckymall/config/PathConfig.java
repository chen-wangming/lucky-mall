package com.luckymall.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description: 设置虚拟路径映射绝对路径---文件上传
 * @Author: wangming.chen
 * @Date: 2019/8/8 13:57
 */
@Configuration
public class PathConfig implements WebMvcConfigurer {

    /**
     * 方法说明：虚拟路径映射
     * @param registry 静态资源控制器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").
                addResourceLocations("file:D:/IDEA/Project/lucky-mall/src/main/resources/static/").
                addResourceLocations("file:D:/IDEA/Project/lucky-mall/src/main/resources/templates/");
        registry.addResourceHandler("/image/**").
                addResourceLocations("file:D:/IDEA/Project/lucky-mall/src/main/resources/static/image/");
    }
}
