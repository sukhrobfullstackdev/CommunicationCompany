package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.*;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.payload.SimCardDto;
import uz.sudev.communicationcompany.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SimCardService {
    final SimCardRepository simCardRepository;
    final AuthenticationService authenticationService;
    final TariffRepository tariffRepository;
    final PackagesRepository packagesRepository;
    final EntertainmentServicesRepository entertainmentServicesRepository;
    final AuthenticationRepository authenticationRepository;

    public SimCardService(AuthenticationRepository authenticationRepository, EntertainmentServicesRepository entertainmentServicesRepository, PackagesRepository packagesRepository, TariffRepository tariffRepository, AuthenticationService authenticationService, SimCardRepository simCardRepository) {
        this.simCardRepository = simCardRepository;
        this.authenticationService = authenticationService;
        this.tariffRepository = tariffRepository;
        this.packagesRepository = packagesRepository;
        this.entertainmentServicesRepository = entertainmentServicesRepository;
        this.authenticationRepository = authenticationRepository;
    }

    public ResponseEntity<Message> addSimCard(SimCardDto simCardDto) {
        boolean exists = simCardRepository.existsByPhoneNumber(simCardDto.getPhoneNumber());
        if (!exists) {
            SimCard simCard = new SimCard();
            simCard.setPhoneNumber(simCardDto.getPhoneNumber());
            simCard.setBalance(simCardDto.getBalance());
            simCardRepository.save(simCard);
            return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The new sim card is successfully registered!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The phone number is already exists!"));
        }
    }

    public ResponseEntity<Message> editSimCard(UUID id, SimCardDto simCardDto) {
        Optional<SimCard> optionalSimCard = simCardRepository.findById(id);
        Optional<User> optionalUser = authenticationRepository.findById(simCardDto.getUserId());
        Optional<Tariff> optionalTariff = tariffRepository.findById(simCardDto.getTariffId());
        Optional<Packages> optionalPackages = packagesRepository.findById(simCardDto.getPackagesId());
        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            boolean exists = simCardRepository.existsByPhoneNumberOrPassportNumber(simCard.getPhoneNumber(), simCardDto.getPassportNumber());
            if (exists) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "You have another phone number which assign to this passport number!"));
            }
            simCard.setBalance(simCard.getBalance());
            optionalUser.ifPresent(simCard::setUser);
            simCard.setPassportNumber(simCardDto.getPassportNumber());
            if (simCardDto.getTariffId() != null) {
                if (optionalTariff.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The tariff that you selected is not found!"));
                } else {
                    Tariff tariff = optionalTariff.get();
                    simCard.setTariff(tariff);
                    simCard.setBalance(simCard.getBalance() - (tariff.getPrice() + tariff.getFeePrice()));
                }
            }
            if (simCardDto.getPackagesId() != null) {
                if (optionalPackages.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The package that you selected is not found!"));
                } else {
                    Packages packages = optionalPackages.get();
                    simCard.setPackages(packages);
                    simCard.setBalance(simCard.getBalance() - (packages.getPrice()));
                }
            }
            List<EntertainmentServices> entertainmentServices = new ArrayList<>();
            for (Integer integer : simCardDto.getEntertainmentServicesId()) {
                Optional<EntertainmentServices> optionalEntertainmentServices = entertainmentServicesRepository.findById(integer);
                if (optionalEntertainmentServices.isPresent()) {
                    entertainmentServices.add(optionalEntertainmentServices.get());
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The entertainment service which has id = " + integer + " is not found!"));
                }
            }
            simCard.setEntertainmentServices(entertainmentServices);
            simCardRepository.save(simCard);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The sim card is successfully edited!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The sim card or user is not found!"));
        }
    }

    public ResponseEntity<Message> deleteSimCard(UUID id) {
        Optional<SimCard> optionalSimCard = simCardRepository.findById(id);
        if (optionalSimCard.isPresent()) {
            simCardRepository.delete(optionalSimCard.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The sim card is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The sim card is not found!"));
        }
    }

    public ResponseEntity<Page<SimCard>> getSimCards(int page, int size) {
        return ResponseEntity.ok(simCardRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<SimCard> getSimCard(UUID id) {
        Optional<SimCard> optionalSimCard = simCardRepository.findById(id);
        return optionalSimCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<SimCard> getBalance(String phoneNumber) {
        Optional<SimCard> optionalSimCard = simCardRepository.findByPhoneNumber(phoneNumber);
        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            return ResponseEntity.ok(new SimCard(simCard.getBalance(), simCard.getMinutesFromTariff(), simCard.getMinutesFromEntertainmentServices(), simCard.getMinutesFromPackages(), simCard.getMegabytesFromTariff(), simCard.getMegabytesFromEntertainmentServices(), simCard.getMegabytesFromPackages(), simCard.getSmsFromTariff(), simCard.getSmsFromEntertainmentServices(), simCard.getSmsFromPackages()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<SimCard> getServices(String phoneNumber) {
        Optional<SimCard> optionalSimCard = simCardRepository.findByPhoneNumber(phoneNumber);
        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            return ResponseEntity.ok(new SimCard(simCard.getEntertainmentServices(), simCard.getTariff(), simCard.getPackages()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    public ResponseEntity<SimCard> getAllByPhoneNumber(String phoneNumber) {
        Optional<SimCard> optionalSimCard = simCardRepository.findByPhoneNumber(phoneNumber);
        return optionalSimCard.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> changeService(String phoneNumber, SimCardDto simCardDto) {
        Optional<SimCard> optionalSimCard = simCardRepository.findByPhoneNumber(phoneNumber);
        if (optionalSimCard.isPresent()) {
            SimCard simCard = optionalSimCard.get();
            if (simCardDto.getPackagesId() != null) {
                Optional<Packages> optionalPackages = packagesRepository.findById(simCardDto.getPackagesId());
                if (optionalPackages.isPresent()) {
                    Packages packages = optionalPackages.get();
                    if ((simCard.getBalance() > packages.getPrice()) && !simCard.getPackages().equals(packages)) {
                        simCard.setBalance(simCard.getBalance() - packages.getPrice());
                        PackagesType packagesType = packages.getPackagesType();
                        if (packagesType.getPackagesTypeName().equals("FOR_INTERNET")) {
                            simCard.setMegabytesFromPackages(packages.getAmount());
                        } else if (packagesType.getPackagesTypeName().equals("FOR_MINUTES")) {
                            simCard.setMinutesFromPackages(packages.getAmount());
                        } else {
                            simCard.setSmsFromPackages(packages.getAmount());
                        }
                        simCard.setPackages(packages);
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "You have not enough money to change your package!"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The package that you want to buy is not found!"));
                }
            }
            if (simCardDto.getTariffId() != null) {
                Optional<Tariff> optionalTariff = tariffRepository.findById(simCardDto.getTariffId());
                if (optionalTariff.isPresent()) {
                    Tariff tariff = optionalTariff.get();
                    if ((simCard.getBalance() > tariff.getFeePrice() + tariff.getPrice()) && !simCard.getTariff().equals(tariff)) {
                        simCard.setBalance(simCard.getBalance() - (tariff.getFeePrice() + tariff.getPrice()));
                        simCard.setMinutesFromTariff(tariff.getMinutesOffTheNetwork() + tariff.getMinutesWithinTheNetwork());
                        simCard.setMegabytesFromTariff(tariff.getMegabytes());
                        simCard.setSmsFromPackages(tariff.getSms());
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "You have not enough money to change your tariff, Please fill your balance not less than " + (tariff.getFeePrice() + tariff.getPrice()) + "!"));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The tariff is not found!"));
                }
            }
            if (!simCardDto.getEntertainmentServicesId().isEmpty()) {
                List<EntertainmentServices> entertainmentServices = simCard.getEntertainmentServices();
                for (Integer integer : simCardDto.getEntertainmentServicesId()) {
                    Optional<EntertainmentServices> optionalEntertainmentServices = entertainmentServicesRepository.findById(integer);
                    if (optionalEntertainmentServices.isPresent()) {
                        EntertainmentServices entertainmentServicesCurrent = optionalEntertainmentServices.get();
                        if (!entertainmentServices.contains(entertainmentServicesCurrent)) {
                            entertainmentServices.add(entertainmentServicesCurrent);
                        }
                    } else {
                        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The entertainment service which has id " + integer + " is not found!"));
                    }
                }
                simCard.setEntertainmentServices(entertainmentServices);
            }
            simCardRepository.save(simCard);
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "success"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The sim card is not found!"));
        }
    }
}
