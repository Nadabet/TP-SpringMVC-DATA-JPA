package com.enset.tp.appgestionpatient.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

     @Bean
     public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
         return new InMemoryUserDetailsManager(
                 User.builder().username("ismail").password(passwordEncoder().encode("wow")).roles("USER").build(),
                 User.builder().username("user2").password(passwordEncoder().encode("wow")).roles("USER").build(),
                 User.builder().username("admin").password(passwordEncoder().encode("wow")).roles("USER", "ADMIN").build()
         );
     }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.defaultSuccessUrl("/user/index")).formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.loginPage("/login").permitAll()).logout(httpSecurityLogoutConfigurer -> httpSecurityLogoutConfigurer.logoutUrl("/logout")).exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer.accessDeniedPage("/notAuthorized")).authorizeHttpRequests(ar->ar.requestMatchers("/admin/**").hasRole("ADMIN")).authorizeHttpRequests(ar-> ar.requestMatchers("/user/**").hasRole("USER")).authorizeHttpRequests(ar-> ar.requestMatchers("/webjars/**").permitAll()).authorizeHttpRequests(ar-> ar.anyRequest().authenticated()).rememberMe(httpSecurityRememberMeConfigurer -> httpSecurityRememberMeConfigurer.key("uniqueAndSecret"));

        return http.build();
    }

}

