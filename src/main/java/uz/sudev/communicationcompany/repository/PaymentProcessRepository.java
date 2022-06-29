package uz.sudev.communicationcompany.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.sudev.communicationcompany.entity.PaymentProcess;

import java.util.UUID;

@Repository
public interface PaymentProcessRepository extends JpaRepository<PaymentProcess, UUID> {
}
