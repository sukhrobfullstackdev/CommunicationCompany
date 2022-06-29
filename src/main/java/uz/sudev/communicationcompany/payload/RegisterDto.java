package uz.sudev.communicationcompany.payload;


import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class RegisterDto {
    @Size(min = 3)
    @NotNull(message = "Please enter your first name!")
    private String firstName;
    @Size(min = 5)// javani o'zidan turib length ini tekshiradi , database ga aloqasi yoq
    @NotNull(message = "Please enter your last name!")
    private String lastName;
    @NotNull(message = "Please enter your active email!")
    @Email // example@gmail.com shakilida bo'lishini ta'minlab beradi.
    private String email;
    @NotNull(message = "Please enter your password!")
    @Size(min = 8)
    private String password;
}
