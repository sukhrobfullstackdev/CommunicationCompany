package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.EntertainmentServices;

@Repository
public interface EntertainmentServicesRepository extends JpaRepository<EntertainmentServices,Integer> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name, Integer id);
}
