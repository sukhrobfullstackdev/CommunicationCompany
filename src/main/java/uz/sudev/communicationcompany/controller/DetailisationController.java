package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.Detailisation;
import uz.sudev.communicationcompany.service.DetailisationService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/detailisation")
public class DetailisationController {
    final DetailisationService detailisationService;

    public DetailisationController(DetailisationService detailisationService) {
        this.detailisationService = detailisationService;
    }
    @PreAuthorize(value = "hasRole('EMPLOYEE')")
    @GetMapping
    public ResponseEntity<Page<Detailisation>> getDetailisations(@RequestParam int page,@RequestParam int size){
        return detailisationService.getDetailisations(page,size);
    }
    @PreAuthorize(value = "hasRole('EMPLOYEE')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Detailisation> getDetailisation(@PathVariable UUID id){
        return detailisationService.getDetailisation(id);
    }
}
