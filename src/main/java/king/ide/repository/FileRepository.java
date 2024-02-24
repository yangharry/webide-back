package king.ide.repository;

import king.ide.domain.Files;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileRepository extends JpaRepository<Files, Long> {
    // Spring Data JPA 사용

    // 추가
    List<Files> findByFoldersId(Long folderId);

    List<Files> findByPackagesId(Long pacakageId);
}
