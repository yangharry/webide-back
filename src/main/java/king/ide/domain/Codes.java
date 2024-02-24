package king.ide.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
public class Codes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long id;

    /*@ManyToOne
    @JoinColumn(name = "file_id")
    private Files files;*/

    @OneToOne
    @JoinColumn(name = "file_id")
    private Files files;

    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
