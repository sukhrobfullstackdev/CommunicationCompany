package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.Packages;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.payload.PackageDto;
import uz.sudev.communicationcompany.service.PackagesService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/packages")
public class PackagesController {
    final PackagesService packagesService;

    public PackagesController(PackagesService packagesService) {
        this.packagesService = packagesService;
    }

    @GetMapping
    public ResponseEntity<Page<Packages>> getPackages(@RequestParam int page, @RequestParam int size) {
        return packagesService.getPackages(page, size);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Packages> getPackage(@PathVariable UUID id) {
        return packagesService.getPackage(id);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_TARIFF_MANAGER','ROLE_NUMBERS_MANAGER')")
    @PostMapping
    public ResponseEntity<Message> addPackage(@RequestBody PackageDto packageDto) {
        return packagesService.addPackage(packageDto);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_TARIFF_MANAGER','ROLE_NUMBERS_MANAGER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editPackage(@PathVariable UUID id, @RequestBody PackageDto packageDto) {
        return packagesService.editPackage(id, packageDto);
    }
    @PreAuthorize(value = "hasAnyRole('ROLE_TARIFF_MANAGER','ROLE_NUMBERS_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deletePackage(@PathVariable UUID id) {
        return packagesService.deletePackage(id);
    }
}
