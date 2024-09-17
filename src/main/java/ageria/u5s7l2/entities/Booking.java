package ageria.u5s7l2.entities;

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
public class Booking {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "travel_id")
    private Travel travel;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDate bookingDate;

    private String preference;

    public Booking(Travel travel, Employee employee, LocalDate bookingDate, String preference) {
        this.travel = travel;
        this.employee = employee;
        this.bookingDate = bookingDate;
        this.preference = preference;
    }

    public Booking(LocalDate bookingDate, String preference) {
        this.bookingDate = bookingDate;
        this.preference = preference;
    }
}
