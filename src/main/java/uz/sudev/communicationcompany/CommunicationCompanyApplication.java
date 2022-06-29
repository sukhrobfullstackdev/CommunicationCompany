package uz.sudev.communicationcompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import uz.sudev.communicationcompany.payload.RegisterDto;
import uz.sudev.communicationcompany.repository.AuthenticationRepository;
import uz.sudev.communicationcompany.service.AuthenticationService;

@SpringBootApplication //(scanBasePackages= {"uz.sudev.communicationcompany"})
public class CommunicationCompanyApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommunicationCompanyApplication.class, args);
    }

}
