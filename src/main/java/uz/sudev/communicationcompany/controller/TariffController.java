package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.Tariff;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.payload.TariffDto;
import uz.sudev.communicationcompany.service.TariffService;

import java.util.UUID;

@RestController
@RequestMapping(value = "/tariff")
public class TariffController {
    final TariffService tariffService;

    public TariffController(TariffService tariffService) {
        this.tariffService = tariffService;
    }

    @GetMapping
    public ResponseEntity<Page<Tariff>> getTariffs(@RequestParam int page, @RequestParam int size) {
        return tariffService.getTariffs(page, size);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Tariff> getTariff(@PathVariable UUID id) {
        return tariffService.getTariff(id);
    }

    @PreAuthorize(value = "hasRole('TARIFF_MANAGER')")
    @PostMapping
    public ResponseEntity<Message> addTariff(@RequestBody TariffDto tariffDto) {
        return tariffService.addTariff(tariffDto);
    }

    @PreAuthorize(value = "hasRole('TARIFF_MANAGER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editTariff(@PathVariable UUID id, @RequestBody TariffDto tariffDto) {
        return tariffService.editTariff(id, tariffDto);
    }

    @PreAuthorize(value = "hasRole('TARIFF_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteTariff(@PathVariable UUID id) {
        return tariffService.deleteTariff(id);
    }
}
