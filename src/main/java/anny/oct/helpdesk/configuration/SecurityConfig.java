package anny.oct.helpdesk.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(jsr250Enabled = true, prePostEnabled = true, securedEnabled = true )
public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Bean
    public PasswordEncoder getEncoder() {
        return new BCryptPasswordEncoder(4);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()

                .authorizeRequests()
                .antMatchers("/login")
                .permitAll()

                .and()
                .authorizeRequests()
                .antMatchers("/tickets/create/**")
                .hasAnyRole("EMPLOYEE", "MANAGER" )

                .anyRequest()
                .authenticated()

                .and()
                .formLogin()

                .loginProcessingUrl("/login")
                .failureUrl("/404")
                .defaultSuccessUrl("/tickets", true)

                .permitAll()
                .and()

                .logout()
                .logoutSuccessUrl("/")
                .logoutUrl("/logout")
                .invalidateHttpSession(Boolean.TRUE)
                .permitAll()
                .and()
                .sessionManagement()
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                .expiredUrl("/login");
    }
}
