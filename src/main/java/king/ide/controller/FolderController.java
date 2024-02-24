package king.ide.controller;

import king.ide.domain.Folders;
import king.ide.dto.FolderDto;
import king.ide.dto.TreeNode;
import king.ide.service.FolderService;
import king.ide.service.TreeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/editor/folders")
public class FolderController {

    private final FolderService folderService;
    private final TreeService treeService;

    // 폴더 생성
    @PostMapping
    public ResponseEntity<Folders> createFolder(@RequestBody FolderDto folderDto) {
        Folders createdFolder = folderService.createFolder(folderDto.toEntity());
        return ResponseEntity.ok(createdFolder);
    }

    // 모든 폴더 조회
    @GetMapping
    public ResponseEntity<List<Folders>> getAllFolders() {
        List<Folders> foldersList = folderService.getAllFolders();
        return ResponseEntity.ok(foldersList);
    }

    // 선택한 폴더 조회
    @GetMapping("/{id}")
    public ResponseEntity<Folders> getFolderById(@PathVariable Long id) {
        Folders folder = folderService.getFolderById(id);
        return ResponseEntity.ok(folder);
    }

    // 선택한 폴더 업데이트
    @PutMapping("/{id}")
    public ResponseEntity<Folders> updateFolder(@PathVariable Long id, @RequestBody FolderDto folderDto) {
        Folders updatedFolder = folderService.updateFolder(id, folderDto.toEntity());
        return ResponseEntity.ok(updatedFolder);
    }

    // 선택한 폴더 삭제 + 해당 폴더와 하위 컨텐트 모두 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long id) {
        folderService.deleteFolderAll(id);
        return ResponseEntity.noContent().build();
    }

    // 최상위 개체가 폴더인 경우 Tree
    @GetMapping("/tree/{parendId}")
    public ResponseEntity<TreeNode<Object>> getFolderTree(
            @RequestParam(name = "parent", required = false) Long parentId) {
        TreeNode<Object> folderTree = (TreeNode<Object>) treeService.getFolderTree(parentId);
        return ResponseEntity.ok(folderTree);
    }
}
