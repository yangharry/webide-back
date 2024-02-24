package king.ide.controller;

import king.ide.domain.Codes;
import king.ide.dto.CodeDto;
import king.ide.service.CodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/editor/codes")
public class CodeController {

    private final CodeService codeService;

    @PostMapping
    public ResponseEntity<Codes> createCode(@RequestBody CodeDto codeDto) {
        Codes createdCode = codeService.createCode(codeDto.toEntity());
        return ResponseEntity.ok(createdCode);
    }

    @GetMapping
    public ResponseEntity<List<Codes>> getAllCodes() {
        List<Codes> codesList = codeService.getAllCodes();
        return ResponseEntity.ok(codesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Codes> getCodeById(@PathVariable Long id) {
        Codes codes = codeService.getCodeById(id);
        return ResponseEntity.ok(codes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Codes> updateCode(@PathVariable Long id, @RequestBody CodeDto codeDto) {
        Codes updatedCode = codeService.updateCode(id, codeDto.toEntity());
        return ResponseEntity.ok(updatedCode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCode(@PathVariable Long id) {
        codeService.deleteCode(id);
        return ResponseEntity.noContent().build();
    }
}
