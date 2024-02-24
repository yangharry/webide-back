package king.ide.dto;

import king.ide.domain.Codes;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class CodeDto {

    private Long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Codes toEntity() {
        Codes codes = new Codes();
        codes.setId(this.id);

        codes.setContent(this.content);
        codes.setCreatedAt(this.createdAt);
        codes.setUpdatedAt(this.updatedAt);

        return codes;
    }
}
