package uz.sudev.communicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class TariffDto {
    @NotBlank
    private String name;
    @NotEmpty
    private Double price;
    @NotEmpty
    private Double feePrice;
    @NotEmpty
    private Integer validityPeriodDays;
    @NotEmpty
    private Integer tariffTypeId;
    @NotEmpty
    private Double megabytes;
    @NotEmpty
    private Double minutesWithinTheNetwork;
    @NotEmpty
    private Double minutesOffTheNetwork;
    @NotEmpty
    private Double sms;
    @NotEmpty
    private Double priceOfPerMegabyte;
    @NotEmpty
    private Double priceOfPerMinuteWithinTheNetwork;
    @NotEmpty
    private Double priceOfPerMinuteOffTheNetwork;
    @NotEmpty
    private Double priceOfPerSms;
}
