package king.ide.service;

import king.ide.domain.Codes;
import king.ide.domain.Files;
import king.ide.domain.Folders;
import king.ide.domain.Packages;
import king.ide.dto.TreeNode;
import king.ide.repository.CodeRepository;
import king.ide.repository.FileRepository;
import king.ide.repository.FolderRepository;
import king.ide.repository.PackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final FolderRepository folderRepository;
    private final PackageRepository packageRepository;
    private final FileRepository fileRepository;
    private final CodeRepository codeRepository;

    public List<Folders> getChildFolders(Long parentFolderId) { // 폴더 내 폴더 검색
        return folderRepository.findByParentFolderId(parentFolderId);
    }

    public List<Packages> getChildPackages(Long parentPackageId) { // 패키지 내 패키지
        return packageRepository.findByParentPackageId(parentPackageId);
    }

    public List<Files> getChildFiles(Long parentFolderId) { // 부모 폴더 Id -> 해당 폴더의 하위 파일 검색
        return fileRepository.findByFoldersId(parentFolderId);
    }

    public List<Files> getPackageFiles(Long parentPackageId) { // 부모 패키지 Id -> 해당 패키지의 하위 파일 검색
        return fileRepository.findByPackagesId(parentPackageId);
    }

    public Codes getCodeForFile(Long fileId) { // 파일 Id -> 해당하는 코드 검색
        return codeRepository.findByFiles_Id(fileId);
    }

    public TreeNode<Object> getFolderTree(Long parentFolderId) {
        Folders parentFolder = folderRepository.findById(parentFolderId).orElse(null);
        if (parentFolder == null) {
            return null;
        }

        TreeNode<Object> parentNode = new TreeNode<>(parentFolder);

        List<Folders> childFolders = getChildFolders(parentFolderId);
        for (Folders childFolder : childFolders) {
            TreeNode<Object> childNode = getFolderTree(childFolder.getId());
            if (childNode != null) {
                parentNode.addChild(childNode);
            }
        }

        List<Packages> childPackages = getChildPackages(parentFolderId);
        for (Packages childPackage : childPackages) {
            TreeNode<Object> childNode = getFolderTree(childPackage.getId());
            if (childNode != null) {
                parentNode.addChild(childNode);
            }
        }

        List<Files> childFiles = getChildFiles(parentFolderId);
        for (Files childFile : childFiles) {
            TreeNode<Object> childNode = new TreeNode<>(childFile);
            Codes code = getCodeForFile(childFile.getId());
            if (code != null) {
                childNode.addChild(new TreeNode<>(code));
            }
            parentNode.addChild(childNode);
        }

        return parentNode;
    }

    /*public Map<String, Object> getFolderTree(Long parentFolderId) {
        Map<String, Object> tree = new HashMap<>();
        List<Object> children = new ArrayList<>();

        // 하위 폴더
        List<Folders> childFolders = getChildFolders(parentFolderId);
        for (Folders folders : childFolders) {
            children.add(getFolderTree(folders.getId()));
        }

        // 하위 패키지
        List<Packages> childPackages = getChildPackages(parentFolderId);
        for (Packages packages : childPackages) {
            children.add(getFolderTree(packages.getId()));
        }

        // 하위 파일(코드)
        List<Files> childFiles = getChildFiles(parentFolderId);
        for (Files files : childFiles) {
            Map<String, Object> fileMap = new HashMap<>();
            fileMap.put("files", files);
            Codes codes = getCodeForFile(files.getId());
            if (codes != null) {
                fileMap.put("codes", codes);
            }
            children.add(fileMap);
        }

        tree.put("children", children);
        return tree;
    }*/
}
