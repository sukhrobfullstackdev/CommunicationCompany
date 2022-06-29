package uz.sudev.communicationcompany.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.sudev.communicationcompany.entity.User;
import uz.sudev.communicationcompany.entity.enums.RoleName;
import uz.sudev.communicationcompany.payload.LoginDto;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.payload.RegisterDto;
import uz.sudev.communicationcompany.repository.AuthenticationRepository;
import uz.sudev.communicationcompany.repository.RoleRepository;
import uz.sudev.communicationcompany.security.JWTProvider;


import java.util.Collections;
import java.util.Optional;
import java.util.UUID;


@Service
public class AuthenticationService implements UserDetailsService {
    private final AuthenticationRepository authenticationRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JavaMailSender javaMailSender;
    private final AuthenticationManager authenticationManager;
    private final JWTProvider jwtProvider;

    @Autowired
    public AuthenticationService(@Lazy AuthenticationRepository authenticationRepository,
                                 JWTProvider jwtProvider,
                                 AuthenticationManager authenticationManager,
                                 JavaMailSender javaMailSender,
                                 PasswordEncoder passwordEncoder,
                                 @Lazy RoleRepository roleRepository
    ) {
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
        this.authenticationRepository = authenticationRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = authenticationRepository.findByEmail(username);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        } else {
            throw new UsernameNotFoundException(username + " is not found!");
        }
    }

    public ResponseEntity<Message> registerUser(RegisterDto registerDto) {
        boolean exists = authenticationRepository.existsByEmail(registerDto.getEmail());
        if (!exists) {
            User user = new User();
            user.setFirstName(registerDto.getFirstName());
            user.setLastName(registerDto.getLastName());
            user.setEmail(registerDto.getEmail());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setRoles(Collections.singleton(roleRepository.findByRoleName(RoleName.ROLE_CUSTOMER)));
            user.setEmailCode(UUID.randomUUID().toString());
            authenticationRepository.save(user);
            Boolean isEmailSent = sendEmail(registerDto.getEmail(), user.getEmailCode());
            if (isEmailSent) {
                return ResponseEntity.status(HttpStatus.CREATED).body(new Message(true, "The user is successfully registered, please check your email to activate your account!"));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "An error occurred while sending the message to your email, please make sure the information you entered is correct, and try one more time!"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "The email is already in use!"));
        }
    }

    public Boolean sendEmail(String sendingEmail, String emailCode) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("sukhrobdev83@gmail.com");
            message.setTo(sendingEmail);
            message.setSubject("Email account confirmation message!");
            message.setText("<a href='http://localhost:8080/authentication/confirmEmail?emailCode=" + emailCode + "&sendingEmail=" + sendingEmail + "'>Confirm your email!</a>");
            javaMailSender.send(message);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public ResponseEntity<Message> login(LoginDto loginDto) {
        try {
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword()));
            User user = (User) authenticate.getPrincipal();
            String token = jwtProvider.generateToken(loginDto.getUsername(), user.getRoles());
            return ResponseEntity.status(HttpStatus.OK).body(new Message(true, "You have successfully logged in!", token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Password or login is incorrect!"));
        }
    }

    public ResponseEntity<Message> confirmEmail(String emailCode, String sendingEmail) {
        Optional<User> optionalUser = authenticationRepository.findByEmailAndEmailCode(sendingEmail, emailCode);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setEnabled(true);
            user.setEmailCode(null);
            authenticationRepository.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(new Message(true, "Your account is confirmed!"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new Message(false, "Your account was not confirmed!"));
        }
    }
}
