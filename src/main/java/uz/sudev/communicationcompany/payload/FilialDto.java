package uz.sudev.communicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import uz.sudev.communicationcompany.entity.User;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
public class FilialDto {
    private String city;
    private String district;
    private String street;
    private String houseNumber;
    private String filialName;
    private UUID filialManagerId;
    private UUID filialLeaderId;
}
