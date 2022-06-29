package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.USSDCodes;

@Repository
public interface USSDCodesRepository extends JpaRepository<USSDCodes,Integer> {
    boolean existsByUSSDCodeOrName(String USSDCode, String name);
}
