package king.ide.dto;

import king.ide.domain.Folders;
import king.ide.domain.Packages;
import king.ide.service.FolderService;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PackageDto {

    private Long id;
    private Long folderId;
    private String packagename;
    private Long parentPackageId;
    private LocalDateTime createdAt;

    // folderId
    private final FolderService folderService;
    public PackageDto(FolderService folderService) {
        this.folderService = folderService;
    }

    public Packages toEntity() {
        Packages packages = new Packages();
        packages.setId(this.id);

        // folderId
        Folders folders = folderService.getFolderById(this.folderId);
        packages.setFolders(folders);

        packages.setPackagename(this.packagename);
        packages.setParentPackageId(this.parentPackageId);
        packages.setCreatedAt(this.createdAt);

        return packages;
    }
}
