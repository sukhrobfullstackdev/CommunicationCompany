package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.EntertainmentServices;
import uz.sudev.communicationcompany.payload.EntertainmentServicesDto;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.service.EntertainmentServicesService;

@RestController
@RequestMapping(value = "/entertainmentServices")
public class EntertainmentServicesController {
    final EntertainmentServicesService entertainmentServicesService;

    public EntertainmentServicesController(EntertainmentServicesService entertainmentServicesService) {
        this.entertainmentServicesService = entertainmentServicesService;
    }

    @PreAuthorize(value = "hasRole('ROLE_TARIFF_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<EntertainmentServices>> getEntertainmentServices(@RequestParam int page, @RequestParam int size) {
        return entertainmentServicesService.getEntertainmentServices(page, size);
    }

    @PreAuthorize(value = "hasRole('ROLE_TARIFF_MANAGER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntertainmentServices> getEntertainmentService(@PathVariable Integer id) {
        return entertainmentServicesService.getEntertainmentService(id);
    }

    @PreAuthorize(value = "hasRole('ROLE_TARIFF_MANAGER')")
    @PostMapping
    public ResponseEntity<Message> addEntertainmentService(@RequestBody EntertainmentServicesDto entertainmentServicesDto) {
        return entertainmentServicesService.addEntertainmentService(entertainmentServicesDto);
    }

    @PreAuthorize(value = "hasRole('ROLE_TARIFF_MANAGER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editEntertainmentService(@PathVariable Integer id, @RequestBody EntertainmentServicesDto entertainmentServicesDto) {
        return entertainmentServicesService.editEntertainmentService(id, entertainmentServicesDto);
    }
    @PreAuthorize(value = "hasRole('ROLE_TARIFF_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteEntertainmentService(@PathVariable Integer id) {
        return entertainmentServicesService.deleteEntertainmentService(id);
    }
}
