package no.ntnu.idatt2105.marketplace.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuration class for Spring Security settings in the marketplace application.
 * This class defines the security filter chain, including authorization rules,
 * CORS configuration, CSRF protection, and JWT authentication.
 *
 * @author Jonas Reiher
 * @author Erlend Eide Zindel
 * @author Konrad Seime
 * @author Eskild Smestu
 * @version 1.0
 * @since 1.0
 * @see JWTAuthorizationFilter
 */
@Configuration
public class SecurityConfig {
    //TODO remove users/ endpoint
    /**
     * Configures the security filter chain for the application.
     * Sets up authorization rules, disables CSRF, configures CORS,
     * establishes stateless session management, and adds the JWT authorization filter.
     *
     * @param http the HttpSecurity object to configure
     * @return the built SecurityFilterChain
     * @throws Exception if an error occurs during configuration
     * @since 1.0
     */
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
                        "/api/categories",
                        "/api/categories/**",
                        "/h2-console/**",             // for databasetilgang
                        "/images/**",
                        "/images/listingImages/",
                        "/images/listingImages/**",
                        "/uploads/**",
                        "/uploads/images/**",
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
