package ageria.u5s7l2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record BookingDTO(@NotNull(message = "Travel ID is required")
                         Long travelId,
                         @NotNull(message = "Employee ID is required")
                         Long employeeId,

                         @NotEmpty(message = "The preference is required")
                         String preference) {
}
