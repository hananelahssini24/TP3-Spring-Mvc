package com.lahssini.Tp3MVCThylemeaf.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(PasswordEncoder passwordEncoder){
      String EncadedPass=  passwordEncoder.encode("12345");
        return new InMemoryUserDetailsManager(

                User.withUsername("user1").password(EncadedPass).roles("USER").build(),
                User.withUsername("user2").password(EncadedPass).roles("USER").build(),
                User.withUsername("admin").password(passwordEncoder.encode("admin")).roles("ADMIN","USER").build()
        );
    }
    //
    /*@Bean
    public UserDetailsService userDetailsService(){
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return null;
            }
        }
    }*/
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(ar->ar.loginPage("/login").defaultSuccessUrl("/",true).permitAll())
                .rememberMe(Customizer.withDefaults())
                .authorizeHttpRequests(ar ->ar.requestMatchers("/webjars/**").permitAll())
                //.authorizeHttpRequests(ar ->ar.requestMatchers("/admin/**").hasRole("ADMIN"))
                //.authorizeHttpRequests(ar ->ar.requestMatchers("/user/**").hasRole("USER"))
                .exceptionHandling(ar->ar.accessDeniedPage("/notAuthorized"))
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .build();
    }
}
