package king.ide.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateEntity {

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-mm-dd/HH:mm:ss")
    private LocalDateTime createdAt;

//    @LastModifiedDate
//    @DateTimeFormat(pattern = "yyyy-mm-dd/HH:mm:ss")
//    private LocalDateTime modifiedDate;
}
