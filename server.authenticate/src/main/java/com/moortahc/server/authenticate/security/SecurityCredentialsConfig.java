package com.moortahc.server.authenticate.security;

import com.moortahc.server.authenticate.model.UserRole;
import com.moortahc.server.authenticate.repo.UserRepository;
import com.moortahc.server.authenticate.service.UserDetailsServiceImpl;
import com.moortahc.server.common.security.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity    // Enable security config. This annotation denotes config for spring security.
public class SecurityCredentialsConfig extends WebSecurityConfigurerAdapter {
    
    private final UserDetailsService userDetailsService;
    
    private final JwtConfig jwtConfig;
    
    @Autowired
    public SecurityCredentialsConfig(UserDetailsServiceImpl userDetailsService, JwtConfig jwtConfig) {
        this.userDetailsService = userDetailsService;
        this.jwtConfig = jwtConfig;
    }
    
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // handle an authorized attempts
                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                // Add a filter to validate user credentials and add token in the response header
            
                // What's the authenticationManager()?
                // An object provided by WebSecurityConfigurerAdapter, used to authenticate the user passing user's credentials
                // The filter needs this auth manager to authenticate the user.
                .addFilter(new JwtEmailPasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .authorizeRequests()
                // allow all POST requests
                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
                    .antMatchers("/h2-console/**").permitAll()
                // any other requests must be authenticated
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable();
    }
    
    // Spring has UserDetailsService interface, which can be overriden to provide our implementation for fetching user from database (or any other source).
    // The UserDetailsService object is used by the auth manager to load the user from database.
    // In addition, we need to define the password encoder also. So, auth manager can compare and verify passwords.
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}