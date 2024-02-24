package king.ide.dto;

import king.ide.domain.Files;
import king.ide.domain.Packages;
import king.ide.service.PackageService;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FileDto {

    private Long id;
    private Long packageId;
    private String filename;
    private String filepath;
    private Long fileSize;
    private String fileType;

    // packageId
    private final PackageService packageService;
    public FileDto(PackageService packageService) {
        this.packageService = packageService;
    }

    public Files toEntity() {
        Files files = new Files();
        files.setId(this.id);

        // packageId
        Packages packages = packageService.getPackageById(this.packageId);
        files.setPackages(packages);

        files.setFilename(this.filename);
        files.setFilepath(this.filepath);
        files.setFileSize(this.fileSize);
        files.setFileType(this.fileType);

        return files;
    }
}
