package uz.sudev.communicationcompany.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.payload.LoginDto;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.payload.RegisterDto;
import uz.sudev.communicationcompany.service.AuthenticationService;


@RestController
@RequestMapping(value = "/authentication")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping(value = "/register")
    public ResponseEntity<Message> register(@RequestBody RegisterDto registerDto) {
        return authenticationService.registerUser(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<Message> login(@RequestBody LoginDto loginDto) {
        return authenticationService.login(loginDto);
    }

    @GetMapping("/confirmEmail")
    public ResponseEntity<Message> confirmEmail(@RequestParam String emailCode, @RequestParam String sendingEmail) {
        return authenticationService.confirmEmail(emailCode, sendingEmail);
    }
}
