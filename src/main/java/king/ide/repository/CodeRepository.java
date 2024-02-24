package king.ide.repository;

import king.ide.domain.Codes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodeRepository extends JpaRepository<Codes, Long> {
    // Spring Data JPA 사용

    // 추가
    Codes findByFiles_Id(Long fileId);
}
