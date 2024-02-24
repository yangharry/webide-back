package king.ide.service;

import jakarta.annotation.PostConstruct;
import king.ide.domain.ChatMessage;
import king.ide.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatRepository chatRepository;

    public long save(ChatMessage message) {
        return chatRepository.save(message);
    }

    public List<ChatMessage> findByRoomId(String roomId) {
        return chatRepository.findByRoomId(roomId);
    }

    public ChatMessage findByLoginId(String loginId) {
        return chatRepository.findByLoginId(loginId);
    }

    public List<ChatMessage> findBySender(String sender) {
        return chatRepository.findBySender(sender);
    }

}
