package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.EntertainmentServicesType;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.repository.EntertainmentServicesTypeRepository;

import java.util.Optional;

@Service
public class EntertainmentServicesTypeService {
    final EntertainmentServicesTypeRepository entertainmentServicesTypeRepository;

    public EntertainmentServicesTypeService(EntertainmentServicesTypeRepository entertainmentServicesTypeRepository) {
        this.entertainmentServicesTypeRepository = entertainmentServicesTypeRepository;
    }

    public ResponseEntity<Page<EntertainmentServicesType>> getEnterSerTypes(int page, int size) {
        return ResponseEntity.ok(entertainmentServicesTypeRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<EntertainmentServicesType> getEnterSerType(Integer id) {
        Optional<EntertainmentServicesType> optionalEntertainmentServicesType = entertainmentServicesTypeRepository.findById(id);
        return optionalEntertainmentServicesType.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addEnterSerType(EntertainmentServicesType entertainmentServicesType) {
        entertainmentServicesTypeRepository.save(entertainmentServicesType);
        return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The entertainment type is successfully created!"));
    }

    public ResponseEntity<Message> editEnterSerType(Integer id, EntertainmentServicesType entertainmentServicesType) {
        Optional<EntertainmentServicesType> optionalEntertainmentServicesType = entertainmentServicesTypeRepository.findById(id);
        if (optionalEntertainmentServicesType.isPresent()) {
            EntertainmentServicesType tentertainmentServicesType = optionalEntertainmentServicesType.get();
            tentertainmentServicesType.setEntertainmentServicePeriodType(entertainmentServicesType.getEntertainmentServicePeriodType());
            tentertainmentServicesType.setType(entertainmentServicesType.getType());
            entertainmentServicesTypeRepository.save(tentertainmentServicesType);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The entertainment type is successfully edited!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The entertainment type is not found!"));
        }
    }

    public ResponseEntity<Message> deleteEnterSerType(Integer id) {
        Optional<EntertainmentServicesType> optionalEntertainmentServicesType = entertainmentServicesTypeRepository.findById(id);
        if (optionalEntertainmentServicesType.isPresent()) {
            entertainmentServicesTypeRepository.delete(optionalEntertainmentServicesType.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The entertainment type is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The entertainment type is not found!"));
        }
    }
}
