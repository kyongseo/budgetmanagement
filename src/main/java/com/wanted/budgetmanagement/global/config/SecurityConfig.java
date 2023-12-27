package com.wanted.budgetmanagement.global.config;


import com.wanted.budgetmanagement.api.user.service.UserService;
import com.wanted.budgetmanagement.domain.user.repository.UserRepository;
import com.wanted.budgetmanagement.global.jwt.JwtAuthenticationProvider;
import com.wanted.budgetmanagement.global.jwt.JwtFilter;
import com.wanted.budgetmanagement.global.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.RequestCacheAwareFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .anonymous(AbstractHttpConfigurer::disable)
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilter(getJwtProviderManager()),
                        RequestCacheAwareFilter.class)
                .securityMatcher("/api/**")
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(new AntPathRequestMatcher("/api/users/**", "POST")).permitAll();
                    auth.requestMatchers(new AntPathRequestMatcher("/api/**")).hasRole("USER");
                })
                .build();
    }

    private AuthenticationManager getJwtProviderManager() {
        return new ProviderManager(new JwtAuthenticationProvider(userRepository, jwtProvider));
    }


}
