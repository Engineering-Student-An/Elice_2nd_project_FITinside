package com.team2.fitinside.config;

import com.team2.fitinside.jwt.JwtAccessDeniedHandler;
import com.team2.fitinside.jwt.JwtAuthenticationEntryPoint;
import com.team2.fitinside.jwt.JwtFilter;
import com.team2.fitinside.jwt.TokenProvider;
import com.team2.fitinside.oath.OAuth2SuccessHandler;
import com.team2.fitinside.oath.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.boot.autoconfigure.security.servlet.PathRequest.toH2Console;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@Component
public class SecurityConfig{

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final CustomOAuth2UserService oAuth2UserService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.httpBasic(AbstractHttpConfigurer::disable);

        http.csrf(AbstractHttpConfigurer::disable);

        http.sessionManagement(AbstractHttpConfigurer::disable);

        http.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint).accessDeniedHandler(jwtAccessDeniedHandler));

        http.authorizeHttpRequests(auth ->
                auth
                        .requestMatchers("/api/auth/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/oath2/**").permitAll()
                        .requestMatchers("/api/categories/**", "/api/banners/**").permitAll()
                        .requestMatchers("/api/products/**", "/api/categories/**").permitAll()
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
        );
        // oauth2 설정
        http.oauth2Login(oauth -> // OAuth2 로그인 기능에 대한 여러 설정의 진입점
                // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정을 담당
                oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
                        // 로그인 성공 시 핸들러
                        .successHandler(oAuth2SuccessHandler)
        );

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        http.addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // 쿠키 사용 허용
        config.setAllowCredentials(true);

        // 오리진 허용
        config.setAllowedOrigins(List.of("https://fit-inside.netlify.app"));

        // 모든 메서드 허용
        config.setAllowedMethods(Arrays.asList(
                HttpMethod.GET.name(),
                HttpMethod.POST.name(),
                HttpMethod.PUT.name(),
                HttpMethod.PATCH.name(),
                HttpMethod.DELETE.name(),
                HttpMethod.OPTIONS.name()
        ));

        // 모든 요청 헤더 허용
        config.addAllowedHeader("*");

        // 응답 헤더 허용
        config.setExposedHeaders(Arrays.asList("Content-Type", "Authorization")); // 필요한 헤더 추가

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
