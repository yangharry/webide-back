package king.ide.repository;

import jakarta.persistence.EntityManager;
import king.ide.domain.ChatMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ChatRepository {

    private final EntityManager em;

    public long save(ChatMessage message) {
        em.persist(message);
        return message.getId();
    }

    public List<ChatMessage> findByRoomId(String roomId) {
        return em.createQuery("select cm from ChatMessage cm where cm.roomId = :roomId", ChatMessage.class)
                .setParameter("roomId", roomId)
                .getResultList();
    }

    public ChatMessage findByLoginId(String loginId) {
        return em.createQuery("select cm from ChatMessage cm where cm.loginId = :loginId", ChatMessage.class)
                .setParameter("loginId", loginId)
                .getSingleResult();
    }

    public List<ChatMessage> findBySender(String sender) {
        return em.createQuery("select cm from ChatMessage cm where cm.sender = :sender", ChatMessage.class)
                .setParameter("sender", sender)
                .getResultList();
    }
}
