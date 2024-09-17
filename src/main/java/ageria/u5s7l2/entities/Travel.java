package ageria.u5s7l2.entities;

import ageria.u5s7l2.enums.TravelStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Travel {

    @Id
    @GeneratedValue
    private Long id;
    private String destination;
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private TravelStatus travelStatus;

    public Travel(String destination, LocalDate date, TravelStatus travelStatus) {
        this.destination = destination;
        this.date = date;
        this.travelStatus = travelStatus;
    }

}
