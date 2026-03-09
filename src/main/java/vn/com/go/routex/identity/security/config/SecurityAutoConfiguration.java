package vn.com.go.routex.identity.security.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import vn.com.go.routex.identity.security.exception.CustomAccessDeniedHandler;
import vn.com.go.routex.identity.security.exception.CustomAuthenticationEntryPoint;
import vn.com.go.routex.identity.security.jwt.JwtAuthenticationFilter;

import java.util.List;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.OPTIONS;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.http.HttpMethod.PUT;

@AutoConfiguration
@EnableMethodSecurity
public class SecurityAutoConfiguration {
    @Bean
    @ConditionalOnMissingBean(SecurityFilterChain.class)
    SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter, CustomAccessDeniedHandler accessDeniedHandler, CustomAuthenticationEntryPoint customAuthenticationEntryPoint) throws Exception {

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(corsConfigurerCustomizer())
                .sessionManagement(s ->
                        s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth ->
                        auth.anyRequest().authenticated())
                .exceptionHandling(ex ->
                    ex.accessDeniedHandler(accessDeniedHandler)
                            .authenticationEntryPoint(customAuthenticationEntryPoint))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    private Customizer<CorsConfigurer<HttpSecurity>> corsConfigurerCustomizer() {
        return cors -> cors.configurationSource(corsConfigurationSource());
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var corsConfig = new CorsConfiguration();
            corsConfig.applyPermitDefaultValues();
            corsConfig.setAllowedMethods(List.of(GET.name(), POST.name(), PUT.name(), DELETE.name(), OPTIONS.name()));
            return corsConfig;
        };
    }
}