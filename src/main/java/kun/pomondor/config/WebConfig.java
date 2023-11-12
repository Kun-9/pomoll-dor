package kun.pomondor.config;

import kun.pomondor.web.interceptor.AdminCheckInterceptor;
import kun.pomondor.web.interceptor.LoginCheckInterceptor;
import kun.pomondor.web.interceptor.LoginJoinPageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginCheckInterceptor())
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/*.ico", "/error", "/", "/home", "/member/login", "/member/join", "/js/**", "/error/**", "/ex", "/upload/*", "/etc/food/**", "/vendor/**");

        registry.addInterceptor(new AdminCheckInterceptor())
                .order(2)
                .addPathPatterns("/admin/**");

        registry.addInterceptor(new LoginJoinPageInterceptor())
                .order(3)
                .addPathPatterns("/member/login", "/member/join");
    }
}
