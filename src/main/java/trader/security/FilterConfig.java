package trader.security;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> loggingFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        
        registrationBean.setFilter(new AuthFilter());
        
        registrationBean.addUrlPatterns("/trade-orders");  
        
       registrationBean.setOrder(1); 

        return registrationBean;
    }
}
