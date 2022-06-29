package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.Detailisation;
import uz.sudev.communicationcompany.repository.DetailisationRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class DetailisationService {
    final DetailisationRepository detailisationRepository;

    public DetailisationService(DetailisationRepository detailisationRepository) {
        this.detailisationRepository = detailisationRepository;
    }

    public ResponseEntity<Page<Detailisation>> getDetailisations(int page, int size) {
        return ResponseEntity.ok(detailisationRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Detailisation> getDetailisation(UUID id) {
        Optional<Detailisation> optionalDetailisation = detailisationRepository.findById(id);
        return optionalDetailisation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
