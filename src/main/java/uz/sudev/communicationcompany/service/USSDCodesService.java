package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.USSDCodes;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.repository.USSDCodesRepository;

import java.util.Optional;

@Service
public class USSDCodesService {
    final USSDCodesRepository ussdCodesRepository;

    public USSDCodesService(USSDCodesRepository ussdCodesRepository) {
        this.ussdCodesRepository = ussdCodesRepository;
    }

    public ResponseEntity<Page<USSDCodes>> getCodes(int page, int size) {
        return ResponseEntity.ok(ussdCodesRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<USSDCodes> getCode(Integer id) {
        Optional<USSDCodes> optionalUSSDCodes = ussdCodesRepository.findById(id);
        return optionalUSSDCodes.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addCode(USSDCodes ussdCodes) {
        if (!ussdCodesRepository.existsByUSSDCodeOrName(ussdCodes.getUSSDCode(), ussdCodes.getName())) {
            ussdCodesRepository.save(ussdCodes);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The ussd code is successfully created!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The ussd code is already exists!"));
        }
    }

    public ResponseEntity<Message> editCode(Integer id, USSDCodes ussdCodes) {
        Optional<USSDCodes> optionalUSSDCodes = ussdCodesRepository.findById(id);
        if (optionalUSSDCodes.isPresent()) {
            if (!ussdCodesRepository.existsByUSSDCodeOrName(ussdCodes.getUSSDCode(), ussdCodes.getName())) {
                USSDCodes ussdCodesEditing = optionalUSSDCodes.get();
                ussdCodesEditing.setName(ussdCodes.getName());
                ussdCodesEditing.setUSSDCode(ussdCodes.getUSSDCode());
                ussdCodesEditing.setDescription(ussdCodes.getDescription());
                ussdCodesRepository.save(ussdCodesEditing);
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The ussd code is successfully edited!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The ussd code is already exists!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The ussd code is not found!"));
        }
    }

    public ResponseEntity<Message> deleteCode(Integer id) {
        Optional<USSDCodes> optionalUSSDCodes = ussdCodesRepository.findById(id);
        if (optionalUSSDCodes.isPresent()) {
            ussdCodesRepository.delete(optionalUSSDCodes.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The ussd code is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The ussd code is not found!"));
        }
    }
}
