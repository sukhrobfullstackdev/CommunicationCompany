package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.PaymentProcess;
import uz.sudev.communicationcompany.repository.PaymentProcessRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentProcessService {
    final PaymentProcessRepository paymentProcessRepository;

    public PaymentProcessService(PaymentProcessRepository paymentProcessRepository) {
        this.paymentProcessRepository = paymentProcessRepository;
    }

    public ResponseEntity<Page<PaymentProcess>> getPaymentProcesses(int page, int size) {
        return ResponseEntity.ok(paymentProcessRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<PaymentProcess> getPaymentProcess(UUID id) {
        Optional<PaymentProcess> optionalPaymentProcess = paymentProcessRepository.findById(id);
        return optionalPaymentProcess.map(paymentProcess -> ResponseEntity.status(HttpStatus.OK).body(paymentProcess)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
