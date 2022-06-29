package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.TariffType;

@Repository
public interface TariffTypeRepository extends JpaRepository<TariffType,Integer> {
}
