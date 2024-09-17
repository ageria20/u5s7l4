package ageria.u5s7l2.dto;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public record EmployeeDTO(
        @NotEmpty(message = "Username is required")
        @Size(min = 2, max = 10, message = "Username has to be from 3 to 10 characters")
        String username,
        @NotEmpty(message = "Name is required")
        @Size(min = 2, max = 30, message = "Name has to be from 3 to 30 characters")
        String name,
        @NotEmpty(message = "Surname is required")
        String surname,
        @NotEmpty(message = "Email  is required")
        String email,
        @NotEmpty(message = "Password  is required")
        String password) {
}
