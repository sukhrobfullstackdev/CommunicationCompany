package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.EntertainmentServicesType;

@Repository
public interface EntertainmentServicesTypeRepository extends JpaRepository<EntertainmentServicesType,Integer> {
}
