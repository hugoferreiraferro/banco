package com.project.banco.infra.security;

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

@Configuration
@EnableWebSecurity //falo para o spring ativar o webSecurity para eu mesmo configurar na classe
public class SecurityConfiger {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable()) //desativo o csrf
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //verificação apenas em token
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(HttpMethod.POST, ("/auth/login")).permitAll()
                        .requestMatchers(HttpMethod.POST, ("/auth/register")).permitAll()
                        .requestMatchers(HttpMethod.POST, "/product").hasRole("USER")
                        .anyRequest().authenticated())
                .build();//permit oqeu quaqluer outro seja autenticado automaticamente
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); //criptgrafia de senha
    }

}
