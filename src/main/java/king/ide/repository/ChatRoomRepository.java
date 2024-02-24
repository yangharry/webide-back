package king.ide.repository;

import jakarta.persistence.EntityManager;
import king.ide.domain.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@RequiredArgsConstructor
@Transactional
public class ChatRoomRepository {

    private final EntityManager em;

    //채팅방 생성 후 저장
    public ChatRoom create(String name) {
        ChatRoom chatRoom = new ChatRoom();
        chatRoom.setName(name);
        em.persist(chatRoom);
        return chatRoom;
    }

    //채팅방 조회
    public Optional<ChatRoom> findById(String roomId) {
        List<ChatRoom> rooms = em.createQuery("select cr from ChatRoom cr where cr.roomId = :roomId", ChatRoom.class)
                .setParameter("roomId", roomId)
                .getResultList();

        return rooms.stream().findAny();
    }

    //채팅방 이름으로 조회
    public Optional<ChatRoom> findByName(String roomName) {
        List<ChatRoom> room = em.createQuery("select cr from ChatRoom cr where cr.name = :roomName", ChatRoom.class)
                .setParameter("roomName", roomName)
                .getResultList();

        return room.stream().findAny();
    }

    //모든 채팅방 조회
    public List<ChatRoom> findAll() {
        return em.createQuery("select cr from ChatRoom cr", ChatRoom.class).getResultList();
    }
}
