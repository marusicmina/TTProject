package trader.security;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<AuthFilter> loggingFilter() {
        FilterRegistrationBean<AuthFilter> registrationBean = new FilterRegistrationBean<>();
        
        // Registruješ AuthFilter kao filter u aplikaciji
        registrationBean.setFilter(new AuthFilter());
        
        // Ovdje možeš da odrediš koje URL obrasce (putanje) će filter obraditi
        registrationBean.addUrlPatterns("/trade-orders/*");  // Možeš koristiti i "/*" ako želiš da štitiš sve rute
        //registrationBean.addUrlPatterns("/traders/*");  // Možeš koristiti i "/*" ako želiš da štitiš sve rute

        // Opcionalno, možeš da postaviš redosled primene filtera
        registrationBean.setOrder(1); // Manji broj znači ranije izvršavanje

        return registrationBean;
    }
}
