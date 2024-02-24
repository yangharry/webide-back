package king.ide.service;

import king.ide.domain.Codes;
import king.ide.domain.Files;
import king.ide.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {
    private FileRepository fileRepository;
    private CodeService codeService;

    public Files createFile(Files files) { // 파일 생성
        return fileRepository.save(files);
    }

    public List<Files> getAllFiles() { // 모든 파일 조회
        return fileRepository.findAll();
    }

    public Files getFileById(Long id) { // 파일 조회
        return fileRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("getFileById ERROR" + id));
    }

    public Files updateFile(Long id, Files newFile) { // 파일 [이름] 변경
        Files files = getFileById(id);
        files.setFilename(newFile.getFilename());
        return fileRepository.save(files);
    }

    // 파일은 하위 컨텐트 없음

    public void deleteFile(Long id) { // 파일 삭제 + 연결 된 코드 삭제
        Files files = fileRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("파일 존재하지 않음 ERROR"));

        Codes codes = files.getCodes();
        if (codes != null) {
            codeService.deleteCode(codes.getId());
        }

        fileRepository.delete(files);
    }
}
