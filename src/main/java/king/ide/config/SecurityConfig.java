package king.ide.config;

import king.ide.domain.Authority;
import king.ide.jwt.JwtFilter;
import king.ide.jwt.JwtUtil;
import king.ide.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // csrf disable
                .csrf(AbstractHttpConfigurer::disable)
                // form 로그인 방식 disable
                .formLogin(AbstractHttpConfigurer::disable)
                // http basic 인증 방식 disable
                .httpBasic(AbstractHttpConfigurer::disable)
                // 경로별 인가 작업
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/signup", "/login", "/duplicate/**", "/ws-stomp/websocket").permitAll()
                        .requestMatchers("/admin").hasAnyAuthority(String.valueOf(Authority.ROLE_ADMIN))
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(jwtUtil), LoginFilter.class)
                .addFilterBefore(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil),
                        UsernamePasswordAuthenticationFilter.class)
                // 세션 설정
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}
// "/signup", "/login", "/duplicate/**", "/chat/**"
