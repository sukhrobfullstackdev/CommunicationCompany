package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.Tariff;
import uz.sudev.communicationcompany.entity.TariffType;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.payload.TariffDto;
import uz.sudev.communicationcompany.repository.TariffRepository;
import uz.sudev.communicationcompany.repository.TariffTypeRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class TariffService {
    final TariffRepository tariffRepository;
    final TariffTypeRepository tariffTypeRepository;

    public TariffService(TariffRepository tariffRepository, TariffTypeRepository tariffTypeRepository) {
        this.tariffRepository = tariffRepository;
        this.tariffTypeRepository = tariffTypeRepository;
    }

    public ResponseEntity<Page<Tariff>> getTariffs(int page, int size) {
        return ResponseEntity.ok(tariffRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Tariff> getTariff(UUID id) {
        Optional<Tariff> optionalTariff = tariffRepository.findById(id);
        return optionalTariff.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> addTariff(TariffDto tariffDto) {
        if (!tariffRepository.existsByName(tariffDto.getName())) {
            Optional<TariffType> optionalTariffType = tariffTypeRepository.findById(tariffDto.getTariffTypeId());
            if (optionalTariffType.isPresent()) {
                Tariff tariff = new Tariff();
                tariff.setName(tariffDto.getName());
                tariff.setMegabytes(tariffDto.getMegabytes());
                tariff.setFeePrice(tariffDto.getFeePrice());
                tariff.setPrice(tariffDto.getPrice());
                tariff.setPriceOfPerMegabyte(tariffDto.getPriceOfPerMegabyte());
                tariff.setPriceOfPerSms(tariffDto.getPriceOfPerSms());
                tariff.setPriceOfPerMinuteOffTheNetwork(tariffDto.getPriceOfPerMinuteOffTheNetwork());
                tariff.setMinutesOffTheNetwork(tariffDto.getMinutesOffTheNetwork());
                tariff.setSms(tariffDto.getSms());
                tariff.setMinutesWithinTheNetwork(tariffDto.getMinutesWithinTheNetwork());
                tariff.setValidityPeriodDays(tariffDto.getValidityPeriodDays());
                tariff.setValidityPeriodDays(tariffDto.getValidityPeriodDays());
                tariff.setTariffType(optionalTariffType.get());
                tariffRepository.save(tariff);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The tariff is successfully created!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected tariff type is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The tariff is already exists!"));
        }
    }

    public ResponseEntity<Message> editTariff(UUID id, TariffDto tariffDto) {
        Optional<Tariff> optionalTariff = tariffRepository.findById(id);
        if (optionalTariff.isPresent()) {
            if (!tariffRepository.existsByName(tariffDto.getName())) {
                Optional<TariffType> optionalTariffType = tariffTypeRepository.findById(tariffDto.getTariffTypeId());
                if (optionalTariffType.isPresent()) {
                    Tariff tariff = optionalTariff.get();
                    tariff.setName(tariffDto.getName());
                    tariff.setMegabytes(tariffDto.getMegabytes());
                    tariff.setFeePrice(tariffDto.getFeePrice());
                    tariff.setPrice(tariffDto.getPrice());
                    tariff.setPriceOfPerMegabyte(tariffDto.getPriceOfPerMegabyte());
                    tariff.setPriceOfPerSms(tariffDto.getPriceOfPerSms());
                    tariff.setPriceOfPerMinuteOffTheNetwork(tariffDto.getPriceOfPerMinuteOffTheNetwork());
                    tariff.setMinutesOffTheNetwork(tariffDto.getMinutesOffTheNetwork());
                    tariff.setSms(tariffDto.getSms());
                    tariff.setMinutesWithinTheNetwork(tariffDto.getMinutesWithinTheNetwork());
                    tariff.setValidityPeriodDays(tariffDto.getValidityPeriodDays());
                    tariff.setValidityPeriodDays(tariffDto.getValidityPeriodDays());
                    tariff.setTariffType(optionalTariffType.get());
                    tariffRepository.save(tariff);
                    return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The tariff is successfully created!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The selected tariff type is not found!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The tariff is already exists!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The tariff is not found!"));
        }
    }

    public ResponseEntity<Message> deleteTariff(UUID id) {
        Optional<Tariff> optionalTariff = tariffRepository.findById(id);
        if (optionalTariff.isPresent()) {
            tariffRepository.delete(optionalTariff.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The tariff is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The tariff is not found!"));
        }
    }
}
