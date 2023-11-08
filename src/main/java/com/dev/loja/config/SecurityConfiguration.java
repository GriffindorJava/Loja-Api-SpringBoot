package com.dev.loja.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.GET,"/admin/**" ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST,"/admin/**" ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT,"/admin/**" ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE,"/admin/**" ).hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH,"/admin/**" ).hasRole("ADMIN")
//                        .requestMatchers(HttpMethod.GET,"/carrinho/**" ).hasRole("USER")
//                        .requestMatchers(HttpMethod.POST,"/carrinho/**" ).hasRole("USER")
                        .requestMatchers(HttpMethod.GET,"/perfil/**" ).hasRole("USER")
                        .requestMatchers(HttpMethod.POST,"/perfil/**" ).hasRole("USER")
                        .requestMatchers(HttpMethod.PUT,"/perfil/**" ).hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH,"/perfil/**" ).hasRole("USER")
                        .anyRequest().permitAll())
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager am(AuthenticationConfiguration ac) throws Exception {
        return ac.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
