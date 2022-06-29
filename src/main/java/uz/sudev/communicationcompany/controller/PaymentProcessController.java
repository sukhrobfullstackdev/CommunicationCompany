package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.PaymentProcess;
import uz.sudev.communicationcompany.service.PaymentProcessService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/paymentProcess")
public class PaymentProcessController {
    final PaymentProcessService paymentProcessService;

    public PaymentProcessController(PaymentProcessService paymentProcessService) {
        this.paymentProcessService = paymentProcessService;
    }

    @PreAuthorize(value = "hasRole('DIRECTOR')")
    @GetMapping
    public ResponseEntity<Page<PaymentProcess>> getPaymentProcesses(@RequestParam int page, @RequestParam int size) {
        return paymentProcessService.getPaymentProcesses(page, size);
    }

    @PreAuthorize(value = "hasAnyRole('EMPLOYEE','DIRECTOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<PaymentProcess> getPaymentProcess(@PathVariable UUID id) {
        return paymentProcessService.getPaymentProcess(id);
    }
}
