package king.ide.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import king.ide.domain.Authority;
import king.ide.domain.Member;
import king.ide.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestURI = request.getRequestURI();
        if (requestURI.startsWith("/signup") || requestURI.startsWith("/login") || requestURI.startsWith(
                "/duplicate") || requestURI.startsWith("/ws-stomp/websocket")) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.setStatus(401);

            Map<String, Object> errorData = new HashMap<>();
            errorData.put("errorCode", 401);
            errorData.put("errorMessage", "잘못된 토큰입니다.");

            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorData);
            response.getWriter().write(jsonResponse);
            return;
        }

        String token = authorization.split(" ")[1];

        if (jwtUtil.isExpired(token)) {

            response.setStatus(401);

            Map<String, Object> errorData = new HashMap<>();
            errorData.put("errorCode", 401);
            errorData.put("errorMessage", "만료된 토큰입니다.");

            response.setContentType("application/json;charset=UTF-8");
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(errorData);
            response.getWriter().write(jsonResponse);
            return;
        }

        String loginId = jwtUtil.getLoginId(token);
        String authority = jwtUtil.getAuthority(token);

        Member member = new Member(loginId, Authority.valueOf(authority));

        CustomUserDetails customUserDetails = new CustomUserDetails(member);

        Authentication authToken = new UsernamePasswordAuthenticationToken(
                customUserDetails, null, customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);
    }
}
