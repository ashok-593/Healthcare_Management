package com.example.security;
 
import java.util.Arrays;
import java.util.Collections;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import jakarta.servlet.http.HttpServletRequest;

 

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@SecurityScheme(
        name = "Bear Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SecurityConfig {
 
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }
 
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
        .cors(cors -> {
            cors.configurationSource(new CorsConfigurationSource() {

				@Override
				public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
					// TODO Auto-generated method stub
					CorsConfiguration cfg = new CorsConfiguration();

                    cfg.setAllowedOriginPatterns(Collections.singletonList("*"));
                    //cfg.setAllowedOriginPatterns(Collections.singletonList("https://eccomers96.netlify.app/"));
                    cfg.setAllowedOriginPatterns(Collections.singletonList("http://localhost:4200"));
                    cfg.setAllowedMethods(Collections.singletonList("*"));

                    cfg.setAllowCredentials(true);
                    cfg.setAllowedHeaders(Collections.singletonList("*"));
                    cfg.setExposedHeaders(Arrays.asList("Authorization"));

                    return cfg;
				}
            });
        })

        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> auth
                .requestMatchers( "/api/users/login","/api/users/register").permitAll()
                .anyRequest().authenticated()
            ).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
           
        return http.build();
    }
 
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
     
        return new ProviderManager(Collections.singletonList(authProvider));
    }
}



 