package ageria.u5s7l2.services;


import ageria.u5s7l2.dto.BookingDTO;
import ageria.u5s7l2.dto.UpdateBookingDTO;
import ageria.u5s7l2.entities.Booking;
import ageria.u5s7l2.entities.Employee;
import ageria.u5s7l2.entities.Travel;
import ageria.u5s7l2.exception.BadRequestException;
import ageria.u5s7l2.exception.NotFoundException;
import ageria.u5s7l2.repositories.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    TravelService travelService;

    // GET w/ PAGES
    public Page<Booking> getAllBookings(int pages, int size, String sortBy) {
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.bookingRepository.findAll(pageable);
    }

    // GET BY ID
    public Booking findByBookingId(Long id) {
        return this.bookingRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // POST
    public Booking saveBooking(BookingDTO body) {
        Travel travelFromDB = this.travelService.findById(body.travelId());
        Employee employeeFromDb = this.employeeService.findById(body.employeeId());
        if (this.bookingRepository.existsByEmployeeAndTravelDate(employeeFromDb, travelFromDB.getDate())) {
            throw new BadRequestException("The employee with id: " + body.employeeId() + " already has a Booking in this date: " + LocalDate.now());
        }
        ;

        Booking newBooking = new Booking(travelFromDB, employeeFromDb, LocalDate.now(), body.preference());
        return this.bookingRepository.save(newBooking);
    }

    // PUT
    public Booking findByIdAndUpdate(Long id, UpdateBookingDTO body) {
        Booking bookingFromDB = this.findByBookingId(id);
        bookingFromDB.setPreference(body.preference());
        return this.bookingRepository.save(bookingFromDB);
    }

    // DELETE
    public void findByDelete(Long id) {
        Booking bookingToDelete = this.findByBookingId(id);
        this.bookingRepository.delete(bookingToDelete);
    }
}
