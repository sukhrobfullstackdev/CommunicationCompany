package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.Packages;
import uz.sudev.communicationcompany.entity.PackagesType;

import java.util.UUID;

@Repository
public interface PackagesRepository extends JpaRepository<Packages, UUID> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, UUID id);
}
