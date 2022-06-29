package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.Tariff;

import java.util.UUID;

@Repository
public interface TariffRepository extends JpaRepository<Tariff, UUID> {
    boolean existsByName(String name);
}
