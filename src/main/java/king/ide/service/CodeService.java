package king.ide.service;

import king.ide.domain.Codes;
import king.ide.repository.CodeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CodeService {
    private CodeRepository codeRepository;

    public Codes createCode(Codes codes) { // 코드 생성
        return codeRepository.save(codes);
    }

    public List<Codes> getAllCodes() { // 모든 코드 조회
        return codeRepository.findAll();
    }

    public Codes getCodeById(Long id) { // 코드 조회
        return codeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("getCodeById ERROR" + id));
    }

    public Codes updateCode(Long id, Codes newCode) { // 코드 [내용] 변경
        Codes codes = codeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("코드 존재하지 않음 ERROR"));
        codes.setContent(newCode.getContent());
        return codeRepository.save(codes);
    }

    // 코드는 하위 컨텐트 없음

    public void deleteCode(Long id) { // 코드 삭제
        Codes codes = codeRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("코드 존재하지 않음 ERROR"));

        codeRepository.delete(codes);
    }
}
