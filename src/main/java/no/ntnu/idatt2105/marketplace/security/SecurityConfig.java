package no.ntnu.idatt2105.marketplace.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    //TODO remove users/ endpoint
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .headers(headers -> headers.frameOptions(frame -> frame.disable())) // tillat H2 console i iframe
            .cors(cors -> {})
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "api/users/register",
                        "api/users/login",
                        "api/users/",
                        "/api/listings/all",
                        "/api/listings/id/**",
                        "/api/listings/random",
                        "/api/listings/categories",
                        "/api/listings/conditions",
                        "/h2-console/**",             // for databasetilgang
                        "/images/**",
                        "/v3/api-docs/**",
                        "/swagger-ui/**",
                        "/swagger-ui.html",
                    "/ws/**")
                .permitAll()
                .requestMatchers(
                        "api/users/register",
                        "api/users/login",
                        "/api/users/",
                        "/api/listings/all",
                        "/api/listings/id/**",
                        "api/users",
                        "/api/listings/recommended",
                    "/ws/**").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterBefore(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
