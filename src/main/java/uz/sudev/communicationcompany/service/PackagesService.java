package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.Packages;
import uz.sudev.communicationcompany.entity.PackagesType;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.payload.PackageDto;
import uz.sudev.communicationcompany.repository.PackagesRepository;
import uz.sudev.communicationcompany.repository.PackagesTypeRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PackagesService {
    final PackagesRepository packagesRepository;
    final PackagesTypeRepository packagesTypeRepository;

    public PackagesService(PackagesRepository packagesRepository, PackagesTypeRepository packagesTypeRepository) {
        this.packagesRepository = packagesRepository;
        this.packagesTypeRepository = packagesTypeRepository;
    }

    public ResponseEntity<Page<Packages>> getPackages(int page, int size) {
        return ResponseEntity.ok(packagesRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Packages> getPackage(UUID id) {
        Optional<Packages> optionalPackages = packagesRepository.findById(id);
        return optionalPackages.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addPackage(PackageDto packageDto) {
        if (!packagesRepository.existsByName(packageDto.getName())) {
            Optional<PackagesType> optionalPackagesType = packagesTypeRepository.findById(packageDto.getPackagesTypeId());
            if (optionalPackagesType.isPresent()) {
                packagesRepository.save(new Packages(packageDto.getName(), optionalPackagesType.get(), packageDto.getPrice(), packageDto.getExpireDaysNumber(), packageDto.getAmount(), packageDto.isTransfersAfterPayment()));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The package is successfully created!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected package type is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The package is already exists!"));
        }
    }

    public ResponseEntity<Message> editPackage(UUID id, PackageDto packageDto) {
        Optional<Packages> optionalPackages = packagesRepository.findById(id);
        if (optionalPackages.isPresent()) {
            boolean exists = packagesRepository.existsByNameAndIdNot(packageDto.getName(), id);
            if (!exists) {
                Optional<PackagesType> optionalPackagesType = packagesTypeRepository.findById(packageDto.getPackagesTypeId());
                if (optionalPackagesType.isPresent()) {
                    Packages packages = optionalPackages.get();
                    packages.setName(packageDto.getName());
                    packages.setTransfersAfterPayment(packageDto.isTransfersAfterPayment());
                    packages.setAmount(packageDto.getAmount());
                    packages.setExpireDaysNumber(packageDto.getExpireDaysNumber());
                    packages.setPrice(packageDto.getPrice());
                    packages.setPackagesType(optionalPackagesType.get());
                    packagesRepository.save(packages);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The package is successfully edited!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected package type is not found!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The package is already exists!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The package is not found!"));
        }
    }

    public ResponseEntity<Message> deletePackage(UUID id) {
        Optional<Packages> optionalPackages = packagesRepository.findById(id);
        if (optionalPackages.isPresent()) {
            packagesRepository.delete(optionalPackages.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The package is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The package is not found!"));
        }
    }
}
