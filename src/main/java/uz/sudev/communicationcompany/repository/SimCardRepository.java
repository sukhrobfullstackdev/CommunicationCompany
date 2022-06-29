package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.SimCard;

import javax.validation.constraints.Size;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SimCardRepository extends JpaRepository<SimCard, UUID> {
    boolean existsByPhoneNumber(String phoneNumber);
    boolean existsByPhoneNumberOrPassportNumber(String phoneNumber, String passportNumber);
    Optional<SimCard> findByPhoneNumber(String phoneNumber);
}
