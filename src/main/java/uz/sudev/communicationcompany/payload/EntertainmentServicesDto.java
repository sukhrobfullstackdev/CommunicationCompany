package uz.sudev.communicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class EntertainmentServicesDto {
    private Integer entertainmentServicesTypeId;
    private String name;
    private String description;
}
