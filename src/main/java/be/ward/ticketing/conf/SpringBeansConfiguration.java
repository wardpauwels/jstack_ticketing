package be.ward.ticketing.conf;

import be.ward.ticketing.util.filters.CustomCorsFilter;
import be.ward.ticketing.util.handlers.RESTAuthenticationEntryPoint;
import be.ward.ticketing.util.handlers.RESTAuthenticationFailureHandler;
import be.ward.ticketing.util.handlers.RESTAuthenticationSuccessHandler;
import be.ward.ticketing.util.handlers.RESTLogoutSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class SpringBeansConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RESTAuthenticationEntryPoint authenticationEntryPoint() {
        return new RESTAuthenticationEntryPoint();
    }

    @Bean
    public RESTAuthenticationFailureHandler authenticationFailureHandler() {
        return new RESTAuthenticationFailureHandler();
    }

    @Bean
    public RESTAuthenticationSuccessHandler authenticationSuccessHandler() {
        return new RESTAuthenticationSuccessHandler();
    }

    @Bean
    public CustomCorsFilter customCorsFilter() {
        return new CustomCorsFilter();
    }

    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        return new RESTLogoutSuccessHandler();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*");
            }
        };
    }
}