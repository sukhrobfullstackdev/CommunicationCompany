package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.USSDCodes;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.service.USSDCodesService;

@RestController
@RequestMapping(value = "/uSSDCodes")
public class USSDCodesController {
    final USSDCodesService ussdCodesService;

    public USSDCodesController(USSDCodesService ussdCodesService) {
        this.ussdCodesService = ussdCodesService;
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE')")
    @GetMapping
    public ResponseEntity<Page<USSDCodes>> getCodes(@RequestParam int page, @RequestParam int size) {
        return ussdCodesService.getCodes(page, size);
    }

    @PreAuthorize("hasAnyRole('CUSTOMER','EMPLOYEE')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<USSDCodes> getCode(@PathVariable Integer id) {
        return ussdCodesService.getCode(id);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping
    public ResponseEntity<Message> addCode(@RequestBody USSDCodes ussdCodes) {
        return ussdCodesService.addCode(ussdCodes);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editCode(@PathVariable Integer id, @RequestBody USSDCodes ussdCodes) {
        return ussdCodesService.editCode(id, ussdCodes);
    }

    @PreAuthorize("hasRole('ROLE_NUMBERS_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteCode(@PathVariable Integer id) {
        return ussdCodesService.deleteCode(id);
    }
}
