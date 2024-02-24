package king.ide.controller;

import king.ide.domain.ChatMessage;
import king.ide.service.ChatService;
import king.ide.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final SimpMessageSendingOperations messageSendingOperations;
    private final ChatService chatService;
    private final MemberService memberService;

    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        log.info("message type = {}, message = {}", message.getType(), message.getMessage());
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }

        // 이부분에서 null point error 가 납니다.
        // String loginId =
        // SecurityContextHolder.getContext().getAuthentication().getName();
        // message.setLoginId(loginId);
        chatService.save(message);

        messageSendingOperations.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }

    @GetMapping("/chat/roomId/{roomId}")
    @ResponseBody
    public List<ChatMessage> findMessageByRoomId(@PathVariable String roomId) {
        return chatService.findByRoomId(roomId);
    }

    @GetMapping("/chat/id/{loginId}")
    @ResponseBody
    public ChatMessage findMessageById(@PathVariable String loginId) {
        return chatService.findByLoginId(loginId);
    }

    @GetMapping("/chat/sender/{sender}")
    @ResponseBody
    public List<ChatMessage> findMessageBySender(@PathVariable String sender) {
        return chatService.findBySender(sender);
    }
}
