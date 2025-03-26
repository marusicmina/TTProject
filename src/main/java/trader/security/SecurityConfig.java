package trader.security;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//@EnableWebSecurity
public class SecurityConfig {//extends{// WebSecurityConfigurerAdapter {
/*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/traders/register", "/traders/login").permitAll()  // Ove rute su dostupne svima
                .antMatchers(HttpMethod.GET, "/traders/**").authenticated()  // Rute za GET samo za ulogovane korisnike
                .antMatchers(HttpMethod.POST, "/traders").authenticated()  // Rute za POST samo za ulogovane korisnike
                .anyRequest().authenticated()  // Sve druge rute zahtevaju autentifikaciju
                .and()
            .formLogin()
                .loginPage("/login")  // Strana za login, možete definisati po želji
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
*/
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


