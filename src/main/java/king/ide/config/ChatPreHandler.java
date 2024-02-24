package king.ide.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.MalformedJwtException;
import king.ide.domain.Authority;
import king.ide.domain.ChatMessage;
import king.ide.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextException;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageDeliveryException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import java.util.List;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class ChatPreHandler implements ChannelInterceptor {

    private final JwtUtil jwtUtil;
    String memberId;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        try {
            StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

            String authorizationHeader = String.valueOf(headerAccessor.getNativeHeader("Authorization"));

            StompCommand command = headerAccessor.getCommand();


            if(command.equals(StompCommand.UNSUBSCRIBE) || command.equals(StompCommand.MESSAGE) ||
                    command.equals(StompCommand.CONNECTED) || command.equals(StompCommand.SEND))
                return message;
            else if (command.equals(StompCommand.ERROR)) {
                throw new MessageDeliveryException("error");
            }

            if (authorizationHeader == null) {
                log.info("chat header가 없는 요청입니다.");
                throw new MalformedJwtException("jwt");
            }

            //token 분리
            String token = "";
            String authorizationHeaderStr = authorizationHeader.replace("[","").replace("]","");
            if (authorizationHeaderStr.startsWith("Bearer ")) {
                token = authorizationHeaderStr.replace("Bearer ", "");
            } else {
                log.error("Authorization 헤더 형식이 틀립니다. : {}", authorizationHeader);
                throw new MalformedJwtException("jwt");
            }

            memberId = jwtUtil.getLoginId(token);

            boolean isTokenValid = jwtUtil.validateToken(token);

            if (isTokenValid) {
                this.setAuthentication(message, headerAccessor);
            }
        } catch (ApplicationContextException e) {
            log.error("JWT에러");
            throw new MalformedJwtException("jwt");
        } catch (MessageDeliveryException e) {
            log.error("메시지 에러");
            throw new MessageDeliveryException("error");
        }
        return message;
    }

    private void setAuthentication(Message<?> message, StompHeaderAccessor headerAccessor) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(memberId, null, List.of(new SimpleGrantedAuthority(Authority.ROLE_USER.name())));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        headerAccessor.setUser(authentication);
    }
}
