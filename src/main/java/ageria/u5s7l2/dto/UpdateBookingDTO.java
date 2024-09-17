package ageria.u5s7l2.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateBookingDTO(
        @NotNull(message = "Preference is required to update")
        String preference) {
}
