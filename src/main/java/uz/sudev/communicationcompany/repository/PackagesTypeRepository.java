package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.PackagesType;

@Repository
public interface PackagesTypeRepository extends JpaRepository<PackagesType,Integer> {
}
