package uz.sudev.communicationcompany.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.sudev.communicationcompany.entity.Filial;
import uz.sudev.communicationcompany.entity.Role;
import uz.sudev.communicationcompany.entity.User;
import uz.sudev.communicationcompany.entity.enums.RoleName;
import uz.sudev.communicationcompany.payload.EmployeeDto;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.repository.AuthenticationRepository;
import uz.sudev.communicationcompany.repository.FilialRepository;
import uz.sudev.communicationcompany.repository.RoleRepository;

import java.util.*;

@Service
public class EmployeeService {
    final AuthenticationRepository authenticationRepository;
    final FilialRepository filialRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    public EmployeeService(PasswordEncoder passwordEncoder, RoleRepository roleRepository, FilialRepository filialRepository, AuthenticationRepository authenticationRepository) {
        this.authenticationRepository = authenticationRepository;
        this.filialRepository = filialRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<Message> addManager(EmployeeDto employeeDto) {
        boolean existsByEmail = authenticationRepository.existsByEmail(employeeDto.getEmail());
        if (!existsByEmail) {
            Optional<Filial> optionalFilial = filialRepository.findById(employeeDto.getFilialId());
            if (optionalFilial.isPresent()) {
                User user = new User();
                user.setFirstName(employeeDto.getFirstName());
                user.setLastName(employeeDto.getLastName());
                user.setEmail(employeeDto.getEmail());
                user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
                Set<Role> roles = new HashSet<>();
                if (!employeeDto.getRoles().isEmpty()) {
                    for (Integer roleId : employeeDto.getRoles()) {
                        Optional<Role> optionalRole = roleRepository.findById(roleId);
                        if (optionalRole.isPresent()) {
                            roles.add(optionalRole.get());
                        } else {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The role which has id = " + roleId + " is not found!"));
                        }
                    }
                }
                user.setRoles(roles);
                user.setEnabled(true);
                authenticationRepository.save(user);
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The manager is successfully saved!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The filial which you want to assign this manager is not found!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The email is already in use!"));
        }
    }

    public ResponseEntity<Message> editManager(UUID id, EmployeeDto employeeDto) {
        Optional<User> optionalUser = authenticationRepository.findById(id);
        if (optionalUser.isPresent()) {
            Optional<Filial> optionalFilial = filialRepository.findById(employeeDto.getFilialId());
            if (optionalFilial.isPresent()) {
                User user = optionalUser.get();
                user.setFirstName(employeeDto.getFirstName());
                user.setLastName(employeeDto.getLastName());
                user.setEmail(employeeDto.getEmail());
                user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
                user.setFilial(optionalFilial.get());
                Set<Role> roles = new HashSet<>();
                if (!employeeDto.getRoles().isEmpty()) {
                    for (Integer roleId : employeeDto.getRoles()) {
                        Optional<Role> optionalRole = roleRepository.findById(roleId);
                        if (optionalRole.isPresent()) {
                            roles.add(optionalRole.get());
                        } else {
                            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The role which has id = " + roleId + " is not found!"));
                        }
                    }
                }
                user.setRoles(roles);
                authenticationRepository.save(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The filial that you want to assign to this employee is not found!"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The employee is not found!"));
    }

    public ResponseEntity<Message> deleteManager(UUID id) {
        Optional<User> optionalUser = authenticationRepository.findById(id);
        if (optionalUser.isPresent()) {
            authenticationRepository.delete(optionalUser.get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Message(true, "The employee is successfully deleted!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new Message(false, "The employee is not found!"));
        }
    }

    public ResponseEntity<Page<User>> getManagers(int page, int size) {
        return ResponseEntity.ok(authenticationRepository.findAll(PageRequest.of(page, size)));
    }

    public ResponseEntity<User> getManager(UUID id) {
        Optional<User> optionalUser = authenticationRepository.findById(id);
        return optionalUser.map(user -> ResponseEntity.status(HttpStatus.OK).body(user)).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).body(null));
    }

    public ResponseEntity<Message> editEmployee(EmployeeDto employeeDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        user.setFirstName(employeeDto.getFirstName());
        user.setLastName(employeeDto.getLastName());
        user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
        user.setEmail(employeeDto.getEmail());
        authenticationRepository.save(user);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(true, "The user is successfully deleted!"));
    }

    public ResponseEntity<User> getEmployee() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
