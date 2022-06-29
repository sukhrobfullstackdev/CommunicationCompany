package uz.sudev.communicationcompany.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.sudev.communicationcompany.entity.User;
import uz.sudev.communicationcompany.payload.EmployeeDto;
import uz.sudev.communicationcompany.payload.Message;
import uz.sudev.communicationcompany.service.EmployeeService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/employee/")
public class EmployeeController {
    final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR')")
    @GetMapping("showAllEmployees")
    public ResponseEntity<Page<User>> getManagers(@RequestParam int page, @RequestParam int size) {
        return employeeService.getManagers(page,size);
    }
    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR')")
    @GetMapping("showEmployee/{id}")
    public ResponseEntity<User> getManager(@PathVariable UUID id) {
        return employeeService.getManager(id);
    }

    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR')")
    @PostMapping(value = "addManager")
    public ResponseEntity<Message> addManager(@Valid @RequestBody EmployeeDto employeeDto) {
        return employeeService.addManager(employeeDto);
    }

    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR')")
    @PutMapping(value = "editManager/{id}")
    public ResponseEntity<Message> editManager(@PathVariable UUID id, @Valid @RequestBody EmployeeDto employeeDto) {
        return employeeService.editManager(id, employeeDto);
    }
    @PreAuthorize("hasAnyRole('DEVELOPER','DIRECTOR')")
    @DeleteMapping(value = "deleteManager/{id}")
    public ResponseEntity<Message> deleteManager(@PathVariable UUID id) {
        return employeeService.deleteManager(id);
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping(value = "editEmployee")
    public ResponseEntity<Message> editEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        return employeeService.editEmployee(employeeDto);
    }
    @PreAuthorize("hasRole('EMPLOYEE')")
    @PutMapping(value = "getEmployee")
    public ResponseEntity<User> getEmployee() {
        return employeeService.getEmployee();
    }
}
