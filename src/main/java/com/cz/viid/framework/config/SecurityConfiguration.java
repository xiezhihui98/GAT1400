package com.cz.viid.framework.config;

import com.cz.viid.framework.security.AuthenticationFilter;
import com.cz.viid.framework.security.AuthenticationTokenFilter;
import com.cz.viid.framework.security.VIIDDigestAuthenticationEntryPoint;
import com.cz.viid.framework.security.SimpleAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    AuthenticationFilter authenticationFilter;
    @Autowired
    AuthenticationTokenFilter authenticationTokenFilter;
    @Autowired
    SimpleAccessDeniedHandler simpleAccessDeniedHandler;
    @Autowired
    VIIDDigestAuthenticationEntryPoint VIIDDigestAuthenticationEntryPoint;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable().formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS).permitAll()
                .antMatchers("/VIID/System/Register").permitAll()
                .anyRequest().authenticated()
                .and()
                .headers().frameOptions().disable()
                .and()
                .addFilterAfter(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authenticationTokenFilter, AuthenticationFilter.class)
                .exceptionHandling(handlingConfigurer ->
                        handlingConfigurer.accessDeniedHandler(simpleAccessDeniedHandler)
                                .authenticationEntryPoint(VIIDDigestAuthenticationEntryPoint)
                )
                .build();
    }
}
