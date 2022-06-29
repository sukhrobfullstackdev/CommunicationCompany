package uz.sudev.communicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class PackageDto {
    private Integer packagesTypeId;
    private Double price;
    private Integer expireDaysNumber;
    private Double amount;
    private boolean transfersAfterPayment;
    private String name;
}
