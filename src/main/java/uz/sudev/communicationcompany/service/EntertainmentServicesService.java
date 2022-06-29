package uz.sudev.communicationcompany.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.EntertainmentServices;
import uz.sudev.communicationcompany.entity.EntertainmentServicesType;
import uz.sudev.communicationcompany.payload.EntertainmentServicesDto;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.repository.EntertainmentServicesRepository;
import uz.sudev.communicationcompany.repository.EntertainmentServicesTypeRepository;

import java.util.Optional;

@Service
public class EntertainmentServicesService {
    final EntertainmentServicesRepository entertainmentServicesRepository;
    final EntertainmentServicesTypeRepository entertainmentServicesTypeRepository;

    public EntertainmentServicesService(EntertainmentServicesRepository entertainmentServicesRepository, EntertainmentServicesTypeRepository entertainmentServicesTypeRepository) {
        this.entertainmentServicesRepository = entertainmentServicesRepository;
        this.entertainmentServicesTypeRepository = entertainmentServicesTypeRepository;
    }


    public ResponseEntity<Page<EntertainmentServices>> getEntertainmentServices(int page, int size) {
        return ResponseEntity.ok(entertainmentServicesRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<EntertainmentServices> getEntertainmentService(Integer id) {
        Optional<EntertainmentServices> optionalEntertainmentServices = entertainmentServicesRepository.findById(id);
        return optionalEntertainmentServices.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addEntertainmentService(EntertainmentServicesDto entertainmentServicesDto) {
        if (!entertainmentServicesRepository.existsByName(entertainmentServicesDto.getName())) {
            Optional<EntertainmentServicesType> optionalEntertainmentServicesType = entertainmentServicesTypeRepository.findById(entertainmentServicesDto.getEntertainmentServicesTypeId());
            if (optionalEntertainmentServicesType.isPresent()) {
                entertainmentServicesRepository.save(new EntertainmentServices(optionalEntertainmentServicesType.get(), entertainmentServicesDto.getName(), entertainmentServicesDto.getDescription()));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The entertainment service is successfully created!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected entertainment service type is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The entertainment service is already exists!"));
        }
    }

    public ResponseEntity<Message> editEntertainmentService(Integer id, EntertainmentServicesDto entertainmentServicesDto) {
        Optional<EntertainmentServices> optional = entertainmentServicesRepository.findById(id);
        if (optional.isPresent()) {
            Optional<EntertainmentServicesType> optionalEntertainmentServicesType = entertainmentServicesTypeRepository.findById(entertainmentServicesDto.getEntertainmentServicesTypeId());
            if (optionalEntertainmentServicesType.isPresent()) {
                boolean exists = entertainmentServicesRepository.existsByNameAndIdNot(entertainmentServicesDto.getName(), id);
                if (!exists) {
                    entertainmentServicesRepository.save(new EntertainmentServices(optionalEntertainmentServicesType.get(), entertainmentServicesDto.getName(), entertainmentServicesDto.getDescription()));
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The entertainment service is successfully edited!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The entertainment service is already exists!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected entertainment service type is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The entertainment service is not found!"));
        }

    }

    public ResponseEntity<Message> deleteEntertainmentService(Integer id) {
        Optional<EntertainmentServices> optionalEntertainmentServices = entertainmentServicesRepository.findById(id);
        if (optionalEntertainmentServices.isPresent()) {
            entertainmentServicesRepository.delete(optionalEntertainmentServices.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The entertainment service is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The entertainment service is not found!"));
        }
    }
}
