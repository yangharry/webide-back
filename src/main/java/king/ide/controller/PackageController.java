package king.ide.controller;

import king.ide.domain.Packages;
import king.ide.dto.PackageDto;
import king.ide.service.PackageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/editor/packages")
public class PackageController {

    private final PackageService packageService;

    @PostMapping
    public ResponseEntity<Packages> createPackage(@RequestBody PackageDto packageDto) {
        Packages createdPackage = packageService.createPackage(packageDto.toEntity());
        return ResponseEntity.ok(createdPackage);
    }

    @GetMapping
    public ResponseEntity<List<Packages>> getAllPackages() {
        List<Packages> packagesList = packageService.getAllPackages();
        return ResponseEntity.ok(packagesList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Packages> getPackageById(@PathVariable Long id) {
        Packages packages = packageService.getPackageById(id);
        return ResponseEntity.ok(packages);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Packages> updatePackage(@PathVariable Long id, @RequestBody PackageDto packageDto) {
        Packages updatedPackage = packageService.updatePackage(id, packageDto.toEntity());
        return ResponseEntity.ok(updatedPackage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePackage(@PathVariable Long id) {
        packageService.deletePackageAll(id);
        return ResponseEntity.noContent().build();
    }
}
