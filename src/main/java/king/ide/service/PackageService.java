package king.ide.service;

import jakarta.transaction.Transactional;
import king.ide.domain.Files;
import king.ide.domain.Packages;
import king.ide.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PackageService {
    private PackageRepository packageRepository;
    private FileService fileService;

    public Packages createPackage(Packages Packages) { // 패키지 생성
        return packageRepository.save(Packages);
    }

    public List<Packages> getAllPackages() { // 모든 패키지 조회
        return packageRepository.findAll();
    }

    public Packages getPackageById(Long id) { // 패키지 조회
        return packageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("getPackageById ERROR" + id));
    }

    public Packages updatePackage(Long id, Packages newPackages) { // 패키지 [이름] 변경
        return packageRepository.findById(id)
                .map(aPackages -> {
                    aPackages.setPackagename(newPackages.getPackagename());
                    return packageRepository.save(aPackages);
                })
                .orElseThrow(() -> new RuntimeException("updatePackage ERROR" + id));
    }

    @Transactional
    public void deletePackageAll(Long id){ // 패키지 +  하위 컨텐트 전부 삭제
        Packages packages = packageRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("패키지 존재하지 않음 ERROR"));

        for (Files f : packages.getFiles()) {
            fileService.deleteFile(f.getId());
        }

        packageRepository.delete(packages);
    }

    public void deletePackage(Long id) { // 패키지 삭제
        Packages packages = packageRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("패키지 존재하지 않음 ERROR"));
        packageRepository.delete(packages);
    }
}
