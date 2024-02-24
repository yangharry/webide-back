package king.ide.controller;

import king.ide.domain.Files;
import king.ide.dto.FileDto;
import king.ide.service.CodeService;
import king.ide.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/editor/files")
public class FilesController {

    private final FileService fileService;
    private final CodeService codeService;

    @PostMapping
    public ResponseEntity<Files> createFile(@RequestBody FileDto fileDto) {
        Files createdFile = fileService.createFile(fileDto.toEntity());
        return ResponseEntity.ok(createdFile);
    }

    @GetMapping
    public ResponseEntity<List<Files>> getAllFiles() {
        List<Files> filesList = fileService.getAllFiles();
        return ResponseEntity.ok(filesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Files> getFileById(@PathVariable Long id) {
        Files files = fileService.getFileById(id);
        return ResponseEntity.ok(files);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Files> updateFile(@PathVariable Long id, @RequestBody FileDto fileDto) {
        Files updatedFile = fileService.updateFile(id, fileDto.toEntity());
        return ResponseEntity.ok(updatedFile);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable Long id) {
        fileService.deleteFile(id);
        codeService.deleteCode(id);
        return ResponseEntity.noContent().build();
    }
}
