package uz.sudev.communicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class TurnstileDto {
    private UUID userId;
    private Integer turnstileId;
}
