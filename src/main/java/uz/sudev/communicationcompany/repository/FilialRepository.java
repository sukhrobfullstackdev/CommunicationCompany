package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.Filial;
import uz.sudev.communicationcompany.entity.User;

import java.util.List;

@Repository
public interface FilialRepository extends JpaRepository<Filial,Integer> {
    boolean existsByFilialNameAndAddress_City(String filialName, String address_city);
    boolean existsByFilialNameAndAddress_CityAndIdNot(String filialName, String address_city, Integer id);
}
