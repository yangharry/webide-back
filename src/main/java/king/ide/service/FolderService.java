package king.ide.service;

import jakarta.transaction.Transactional;
import king.ide.domain.Folders;
import king.ide.domain.Packages;
import king.ide.repository.FolderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FolderService {

    private FolderRepository folderRepository;
    private PackageService packageService;

    public Folders createFolder(Folders folders) { // 폴더 생성
        return folderRepository.save(folders);
    }

    public List<Folders> getAllFolders() { // 모든 폴더 조회
        return folderRepository.findAll();
    }

    public Folders getFolderById(Long id) { // 폴더 조회
        return folderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("getFolderById ERROR" + id));
    }

    public Folders updateFolder(Long id, Folders newFolder) { // 폴더[이름] 변경
        return folderRepository.findById(id)
                .map(folders -> {
                    folders.setFoldername(newFolder.getFoldername());
                    return folderRepository.save(folders);
                })
                .orElseThrow(() -> new RuntimeException("updateFolder ERROR" + id));
    }

    @Transactional
    public void deleteFolderAll(Long id) { // 폴더 + 하위 컨텐트 전부 삭제
        Folders folder = folderRepository
                            .findById(id)
                            .orElseThrow(() -> new RuntimeException("폴더 존재하지 않음 ERROR"));

        for (Packages p : folder.getPackages()) {
            packageService.deletePackageAll(p.getId());
        }

        folderRepository.delete(folder);
    }

    public void deleteFolder(Long id) { // 폴더 삭제
        Folders folder = getFolderById(id);
        folderRepository.delete(folder);
    }
}
