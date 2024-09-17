package ageria.u5s7l2.dto;

import ageria.u5s7l2.enums.TravelStatus;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record TravelDTO(@NotNull(message = "Destination is required")
                        String destination,
                        @NotNull(message = "Date is required")
                        LocalDate travelDate,
                        @NotNull(message = "Travel status is required")
                        TravelStatus travelStatus) {
}
