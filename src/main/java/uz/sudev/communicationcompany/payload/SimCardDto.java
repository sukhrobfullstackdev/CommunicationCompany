package uz.sudev.communicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class SimCardDto {
    private String phoneNumber;
    private Double balance;
    private UUID userId;
    private String passportNumber;
    private UUID tariffId;
    private UUID packagesId;
    private List<Integer> entertainmentServicesId;
}
