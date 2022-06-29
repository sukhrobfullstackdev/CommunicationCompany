package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address,Integer> {
}
