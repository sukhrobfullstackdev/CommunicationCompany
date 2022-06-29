package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.SimCard;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.payload.SimCardDto;
import uz.sudev.communicationcompany.service.SimCardService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/simCard")
public class SimCardController {
    final SimCardService simCardService;


    public SimCardController(SimCardService simCardService) {
        this.simCardService = simCardService;
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping
    public ResponseEntity<Page<SimCard>> getSimCards(@RequestParam int page, @RequestParam int size) {
        return simCardService.getSimCards(page, size);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<SimCard> getSimCard(@PathVariable UUID id) {
        return simCardService.getSimCard(id);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE')")
    @GetMapping(value = "/getBalance/{phoneNumber}")
    public ResponseEntity<SimCard> getBalance(@PathVariable String phoneNumber) {
        return simCardService.getBalance(phoneNumber);
    }
    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE')")
    @GetMapping(value = "/getServices/{phoneNumber}")
    public ResponseEntity<SimCard> getServices(@PathVariable String phoneNumber) {
        return simCardService.getServices(phoneNumber);
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @GetMapping(value = "/getAllByPhoneNumber/{phoneNumber}")
    public ResponseEntity<SimCard> getAllByPhoneNumber(@PathVariable String phoneNumber) {
        return simCardService.getAllByPhoneNumber(phoneNumber);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<Message> addSimCard(@Valid SimCardDto simCardDto) {
        return simCardService.addSimCard(simCardDto);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editSimCard(@PathVariable UUID id, @Valid SimCardDto simCardDto) {
        return simCardService.editSimCard(id, simCardDto);
    }
    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping(value = "/changeService/{phoneNumber}")
    public ResponseEntity<Message> changeService(@PathVariable String phoneNumber, @Valid SimCardDto simCardDto) {
        return simCardService.changeService(phoneNumber, simCardDto);
    }

    @PreAuthorize("hasRole('DIRECTOR')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteSimCard(@PathVariable UUID id) {
        return simCardService.deleteSimCard(id);
    }
}
