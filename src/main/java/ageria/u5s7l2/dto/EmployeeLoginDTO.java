package ageria.u5s7l2.dto;

import jakarta.validation.constraints.NotEmpty;

public record EmployeeLoginDTO(@NotEmpty(message = "Email  is required")
                               String email,
                               @NotEmpty(message = "Password  is required")
                               String password) {
}
