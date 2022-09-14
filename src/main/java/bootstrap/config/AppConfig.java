package bootstrap.config;

import bootstrap.handler.CurrentUserInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class AppConfig implements WebMvcConfigurer {

    @Autowired
    CurrentUserInterceptor currentUserInterceptor;

    //Add Spring MVC lifecycle interceptors for pre- and post-processing of controller method invocations
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(currentUserInterceptor);
    }
}
