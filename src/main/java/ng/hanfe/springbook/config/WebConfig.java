package ng.hanfe.springbook.config;

import ng.hanfe.springbook.controller.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**") // 拦截所有路径
                .excludePathPatterns("/login") // 登录页面不需要拦截
                .excludePathPatterns("/register") // 注册页面不需要拦截
                .excludePathPatterns("/api/login") // 排除 API 接口路径
                .excludePathPatterns("/api/register") // 排除 API 接口路径
                .excludePathPatterns("/api/logout") // 排除 API 接口路径
                .excludePathPatterns("/css/**") // 排除 CSS 路径
                .excludePathPatterns("/js/**") // 排除 JS 路径
                .excludePathPatterns("/error"); // 排除 错误 路径
    }
}
