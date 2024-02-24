package king.ide.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String roomId;

    private String name;

}
