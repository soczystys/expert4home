package com.redteam.expert4home.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.redteam.expert4home.security.UserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors().and()
                .authorizeRequests()
                .antMatchers("/**")
                .hasAnyRole(ADMIN.name(), USER.name(), EXPERT.name())
//                .permitAll();
                .anyRequest()
                .authenticated()
                .and()
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .formLogin()
//                .loginPage("/login")
//                .successForwardUrl()
                .and()
                .logout().deleteCookies("JSESSIONID")
                .and()
                .rememberMe()
//                .tokenValiditySeconds((int) TimeUnit.SECONDS.toSeconds(20))
                .key("deafvbtmetenmop");
//                .rememberMeParameter("remember-me")
//                .rememberMeCookieName("remember-me");
//                .alwaysRemember(true);
    }

    @Autowired
    MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
