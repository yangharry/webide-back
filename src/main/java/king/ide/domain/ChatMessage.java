package king.ide.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
public class ChatMessage extends DateEntity{

    // 메세지 타입 : 입장, 채팅, 퇴장
    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private MessageType type;
    private String roomId;
    private String loginId; // 유저 아이디
    private String sender; // 유저 이름
    private String message;
}
