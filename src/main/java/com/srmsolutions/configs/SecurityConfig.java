package com.srmsolutions.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**").addResourceLocations("file:uploads/");
    }


    @Qualifier("userDetailsServiceImpl_SingleTable")
    @Autowired
    UserDetailsService userDetails;
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http    
                .authorizeRequests()
                    .antMatchers("/admin").hasRole("ADMIN")
                    .antMatchers("/event","/addEvent","/editEvent", "/deleteEvent").hasAnyRole("ADMIN", "EO")
                    .antMatchers("/employee", "/addEmployee", "/deleteEmployee").hasAnyRole("ADMIN", "HR")
                    .antMatchers("/", "/home", "/employees", "/editEmployee", "/events").permitAll()
                    .antMatchers("/css/**", "/js/**", "/fonts/**", "/img/**").permitAll()

                .and()
                .formLogin()
                    .loginPage("/login")
                    .failureUrl("/login?login_error=1")
                    .permitAll()
                .and()
                .logout()
                    .logoutSuccessUrl("/login")
                    .permitAll()
                .and().cors()
                .and().
                csrf().disable();          
    }
    
    @Autowired
    public void configureGlobalInDB(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetails).passwordEncoder(plaintextPasswordEncoder());
    }
    
    @Bean
    public PlaintextPasswordEncoder plaintextPasswordEncoder(){
        return new PlaintextPasswordEncoder();
    }
    
    public class PlaintextPasswordEncoder implements PasswordEncoder{

        @Override
        public String encode(CharSequence cs) {
            return cs.toString();
        }

        @Override
        public boolean matches(CharSequence cs, String string) {
            return cs.toString().equals(string);
        }
        
    }
    
}
