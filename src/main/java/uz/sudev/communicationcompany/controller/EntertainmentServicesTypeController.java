package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.EntertainmentServicesType;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.service.EntertainmentServicesTypeService;

@RestController
@RequestMapping(value = "/entertainmentServicesType")
public class EntertainmentServicesTypeController {
    final EntertainmentServicesTypeService entertainmentServicesTypeService;

    public EntertainmentServicesTypeController(EntertainmentServicesTypeService entertainmentServicesTypeService) {
        this.entertainmentServicesTypeService = entertainmentServicesTypeService;
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_FILIAL_MANAGER','ROLE_TARIFF_MANAGER')")
    @GetMapping
    public ResponseEntity<Page<EntertainmentServicesType>> getEnterSerTypes(@RequestParam int page, @RequestParam int size) {
        return entertainmentServicesTypeService.getEnterSerTypes(page, size);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_FILIAL_MANAGER','ROLE_TARIFF_MANAGER')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntertainmentServicesType> getEnterSerType(@PathVariable Integer id) {
        return entertainmentServicesTypeService.getEnterSerType(id);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_FILIAL_MANAGER','ROLE_TARIFF_MANAGER')")
    @PostMapping
    public ResponseEntity<Message> addEnterSerType(@RequestBody EntertainmentServicesType entertainmentServicesType) {
        return entertainmentServicesTypeService.addEnterSerType(entertainmentServicesType);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_FILIAL_MANAGER','ROLE_TARIFF_MANAGER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editEnterSerType(@PathVariable Integer id, @RequestBody EntertainmentServicesType entertainmentServicesType) {
        return entertainmentServicesTypeService.editEnterSerType(id, entertainmentServicesType);
    }

    @PreAuthorize(value = "hasAnyRole('ROLE_FILIAL_MANAGER','ROLE_TARIFF_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteEnterSerType(@PathVariable Integer id) {
        return entertainmentServicesTypeService.deleteEnterSerType(id);
    }
}
