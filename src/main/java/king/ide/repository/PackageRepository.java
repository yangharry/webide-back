package king.ide.repository;

import king.ide.domain.Packages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<Packages, Long> {
    // Spring Data JPA 사용

    // 추가
    List<Packages> findByParentPackageId(Long parentPackageId);
}
