package ageria.u5s7l2.repositories;

import ageria.u5s7l2.entities.Booking;
import ageria.u5s7l2.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    boolean existsByBookingDateAndEmployeeId(LocalDate travelDate, Long employee);


    boolean existsByEmployeeAndTravelDate(Employee employee, LocalDate travelDate);
}
