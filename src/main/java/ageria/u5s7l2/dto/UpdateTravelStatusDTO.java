package ageria.u5s7l2.dto;

import ageria.u5s7l2.enums.TravelStatus;
import jakarta.validation.constraints.NotNull;

public record UpdateTravelStatusDTO(@NotNull(message = "Travel status is required")
                                    TravelStatus travelStatus) {
}
