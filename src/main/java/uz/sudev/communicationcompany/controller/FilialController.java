package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.Filial;
import uz.sudev.communicationcompany.payload.FilialDto;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.service.FilialService;

@RestController
@RequestMapping(value = "/filial")
public class FilialController {
    final FilialService filialService;

    public FilialController(FilialService filialService) {
        this.filialService = filialService;
    }
    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR')")
    @GetMapping
    public ResponseEntity<Page<Filial>> getFilials(@RequestParam int page, @RequestParam int size) {
        return filialService.getFilials(page,size);
    }
    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Filial> getFilial(@PathVariable Integer id) {
        return filialService.getFilial(id);
    }
    @Transactional
    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR','FILIAL_MANAGER')")
    @PostMapping
    public ResponseEntity<Message> addFilial(@RequestBody FilialDto filialDto) {
        return filialService.addFilial(filialDto);
    }
    @Transactional
    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR','FILIAL_MANAGER')")
    @PutMapping(value = "/{id}")
    public ResponseEntity<Message> editFilial(@PathVariable Integer id,@RequestBody FilialDto filialDto) {
        return filialService.editFilial(id,filialDto);
    }
    @Transactional
    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR','FILIAL_MANAGER')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Message> deleteFilial(@PathVariable Integer id) {
        return filialService.deleteFilial(id);
    }
}
