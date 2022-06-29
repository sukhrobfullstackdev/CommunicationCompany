package uz.sudev.communicationcompany.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import uz.sudev.communicationcompany.entity.Role;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Component
@Data
public class EmployeeDto {
    @NotNull(message = "Please enter the employee's first name!")
    private String firstName;
    @NotNull(message = "Please enter the employee's last name!")
    private String lastName;
    @NotNull(message = "Please enter the employee's email!")
    private String email;
    @NotNull(message = "Please enter the employee's password!")
    private String password;
    @NotNull(message = "Please enter the employee's password!")
    private Integer filialId;
    private Set<Integer> roles;
}
