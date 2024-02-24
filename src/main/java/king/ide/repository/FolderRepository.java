package king.ide.repository;

import king.ide.domain.Folders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folders, Long> {
    // Spring Data JPA 사용

    // 추가
    List<Folders> findByParentFolderId(Long parentFolderId);

}
