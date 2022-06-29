package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.Address;
import uz.sudev.communicationcompany.entity.Filial;
import uz.sudev.communicationcompany.entity.Role;
import uz.sudev.communicationcompany.entity.User;
import uz.sudev.communicationcompany.entity.enums.RoleName;
import uz.sudev.communicationcompany.payload.FilialDto;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.repository.AddressRepository;
import uz.sudev.communicationcompany.repository.AuthenticationRepository;
import uz.sudev.communicationcompany.repository.FilialRepository;
import uz.sudev.communicationcompany.repository.RoleRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class FilialService {
    final FilialRepository filialRepository;
    final AddressRepository addressRepository;
    final AuthenticationRepository authenticationRepository;
    final RoleRepository roleRepository;

    public FilialService(RoleRepository roleRepository, AuthenticationRepository authenticationRepository, AddressRepository addressRepository, FilialRepository filialRepository) {
        this.filialRepository = filialRepository;
        this.addressRepository = addressRepository;
        this.authenticationRepository = authenticationRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseEntity<Message> addFilial(FilialDto filialDto) {
        boolean exists = filialRepository.existsByFilialNameAndAddress_City(filialDto.getFilialName(), filialDto.getCity());
        Optional<User> optionalUserFilialLeader = authenticationRepository.findById(filialDto.getFilialLeaderId());
        Optional<User> optionalUserFilialManager = authenticationRepository.findById(filialDto.getFilialManagerId());
        Role roleFilialManager = roleRepository.findByRoleName(RoleName.ROLE_FILIAL_MANAGER);
        Role roleFilialLeader = roleRepository.findByRoleName(RoleName.ROLE_BRANCH_LEADER);
        if (!exists) {
            if (optionalUserFilialLeader.isPresent() && optionalUserFilialManager.isPresent()) {
                Address savedAddress = addressRepository.save(new Address(filialDto.getCity(), filialDto.getDistrict(), filialDto.getStreet(), filialDto.getHouseNumber()));
                User userFilialLeader = optionalUserFilialLeader.get();
                User userFilialManager = optionalUserFilialManager.get();
                Set<Role> rolesLeader = userFilialLeader.getRoles();
                rolesLeader.add(roleFilialLeader);
                userFilialLeader.setRoles(rolesLeader);
                User userFilialLeaderSaved = authenticationRepository.save(userFilialLeader);
                Set<Role> rolesManager = userFilialManager.getRoles();
                rolesManager.add(roleFilialManager);
                userFilialManager.setRoles(rolesManager);
                User userFilialManagerSaved = authenticationRepository.save(userFilialManager);
                filialRepository.save(new Filial(savedAddress, filialDto.getFilialName(), userFilialManagerSaved, userFilialLeaderSaved));
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The filial is successfully saved!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The user that you want to assign as filial manager or filial leader is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The filial is already exits!"));
        }
    }

    public ResponseEntity<Message> editFilial(Integer id, FilialDto filialDto) {
        Optional<Filial> optionalFilial = filialRepository.findById(id);
        boolean exists = filialRepository.existsByFilialNameAndAddress_CityAndIdNot(filialDto.getFilialName(), filialDto.getCity(), id);
        Optional<User> optionalUserFilialLeader = authenticationRepository.findById(filialDto.getFilialLeaderId());
        Optional<User> optionalUserFilialManager = authenticationRepository.findById(filialDto.getFilialManagerId());
        Role roleFilialManager = roleRepository.findByRoleName(RoleName.ROLE_FILIAL_MANAGER);
        Role roleFilialLeader = roleRepository.findByRoleName(RoleName.ROLE_BRANCH_LEADER);
        if (optionalFilial.isPresent()) {
            if (!exists) {
                if (optionalUserFilialLeader.isPresent() && optionalUserFilialManager.isPresent()) {
                    User userFilialLeader = optionalUserFilialLeader.get();
                    User userFilialManager = optionalUserFilialManager.get();
                    Filial filial = optionalFilial.get();
                    Address address = filial.getAddress();
                    address.setCity(filialDto.getCity());
                    address.setDistrict(filialDto.getDistrict());
                    address.setStreet(filialDto.getStreet());
                    address.setHouseNumber(filialDto.getHouseNumber());
                    addressRepository.save(address);
                    User filialLeader = filial.getFilialLeader();
                    User filialManager = filial.getFilialManager();
                    Set<Role> rolesFilialLeader = filialLeader.getRoles();
                    rolesFilialLeader.remove(roleFilialLeader);
                    filialLeader.setRoles(rolesFilialLeader);
                    authenticationRepository.save(filialLeader);
                    Set<Role> rolesFilialManager = filialManager.getRoles();
                    rolesFilialManager.remove(roleFilialManager);
                    filialManager.setRoles(rolesFilialManager);
                    authenticationRepository.save(filialManager);
                    Set<Role> rolesUserFilialManager = userFilialManager.getRoles();
                    rolesUserFilialManager.add(roleFilialManager);
                    userFilialManager.setRoles(rolesUserFilialManager);
                    User userFilialManagerSaved = authenticationRepository.save(userFilialManager);
                    Set<Role> rolesUserFilialLeader = userFilialLeader.getRoles();
                    rolesUserFilialLeader.add(roleFilialLeader);
                    userFilialLeader.setRoles(rolesUserFilialLeader);
                    User userFilialLeaderSaved = authenticationRepository.save(userFilialLeader);
                    filial.setFilialName(filialDto.getFilialName());
                    filial.setFilialLeader(userFilialLeaderSaved);
                    filial.setFilialManager(userFilialManagerSaved);
                    filialRepository.save(filial);
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The filial is successfully edited!"));
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The user that you want to assign as filial manager or branch leader is not found!"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The filial is already exists!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The filial is not found!"));
        }
    }

    public ResponseEntity<Message> deleteFilial(Integer id) {
        Optional<Filial> optionalFilial = filialRepository.findById(id);
        if (optionalFilial.isPresent()) {
            filialRepository.delete(optionalFilial.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The filial is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The filial is not found!"));
        }
    }

    public ResponseEntity<Page<Filial>> getFilials(int page, int size) {
        return ResponseEntity.ok(filialRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<Filial> getFilial(Integer id) {
        Optional<Filial> optionalFilial = filialRepository.findById(id);
        return optionalFilial.map(filial -> ResponseEntity.status(HttpStatus.OK).body(filial)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }
}
