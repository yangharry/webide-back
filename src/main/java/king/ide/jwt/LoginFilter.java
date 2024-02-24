package king.ide.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import king.ide.controller.request.LoginRequest;
import king.ide.domain.Authority;
import king.ide.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StreamUtils;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // 폼 데이터 아닌 json 타입으로 데이터를 받기 위한 코드
        LoginRequest loginRequest;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ServletInputStream inputStream = request.getInputStream();
            String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
            loginRequest = objectMapper.readValue(messageBody, LoginRequest.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String loginId = loginRequest.getLoginId();
        log.info("loginId : {}", loginId);
        String password = loginRequest.getPassword();
        log.info("password : {}", password);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginId, password,
                null);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        CustomUserDetails customUserDetails = (CustomUserDetails) authResult.getPrincipal();

        Long id = customUserDetails.getId();
        String name = customUserDetails.getName();
        String loginId = customUserDetails.getUsername();
        String mobileNumber = customUserDetails.getMobileNumber();

        Collection<? extends GrantedAuthority> authorities = authResult.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String authority = auth.getAuthority();

        if (authority.equals(String.valueOf(Authority.ROLE_WITHDRAWAL))) {
            response.setStatus(401);

            Map<String, Object> errorData = new HashMap<>();
            errorData.put("errorCode", 401);
            errorData.put("errorMessage", "등록되지 않은 회원입니다.");

            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorData);
            response.getWriter().write(jsonResponse);
            return;
        }

        String token = jwtUtil.createJwt(id, name, loginId, mobileNumber, authority, 86_400_000L);

        response.addHeader("Authorization", "Bearer " + token);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("httpCode", 200);
        responseData.put("message", "로그인이 완료되었습니다.");

        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(responseData);
        response.getWriter().write(jsonResponse);

        log.info("로그인 필터 성공");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {

        response.setStatus(401);

        Map<String, Object> errorData = new HashMap<>();
        errorData.put("errorCode", 401);
        errorData.put("errorMessage", "아이디 또는 비밀번호가 잘못 입력되었습니다.");

        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(errorData);
        response.getWriter().write(jsonResponse);

        log.info("로그인 필터 실패");
    }
}
