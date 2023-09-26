package org.spring.ProjectTeam01.board.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfigImpl implements WebMvcConfigurer {

    String bFilePath = "file:///E:/test/saveFile08/bfiles/";
//    String iFilePath = "file:///E:/test/saveFile08/ifiles/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/upload/item/**")
//                .addResourceLocations(iFilePath);
        registry.addResourceHandler("/upload/board/**")
                .addResourceLocations(bFilePath);
        registry.addResourceHandler("/download/board/**")
                .addResourceLocations(bFilePath);
    }
}
