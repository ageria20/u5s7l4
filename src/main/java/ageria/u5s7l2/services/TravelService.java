package ageria.u5s7l2.services;


import ageria.u5s7l2.dto.TravelDTO;
import ageria.u5s7l2.dto.UpdateTravelStatusDTO;
import ageria.u5s7l2.entities.Travel;
import ageria.u5s7l2.enums.TravelStatus;
import ageria.u5s7l2.exception.BadRequestException;
import ageria.u5s7l2.exception.NotFoundException;
import ageria.u5s7l2.repositories.TraveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TravelService {

    @Autowired
    TraveRepository travelRepository;

    // 1. GET
    public Page<Travel> getAllEmployee(int pages, int size, String sortBy) {
        Pageable pageable = PageRequest.of(pages, size, Sort.by(sortBy));
        return this.travelRepository.findAll(pageable);
    }

    // 1.1 GET by id
    public Travel findById(Long id) {
        return this.travelRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    // 2. POST
    public Travel saveEmployee(TravelDTO body) {
        if (travelRepository.existsByDestination(body.destination())) {
            throw new BadRequestException("Travel with this destination already exists");
        }
        Travel newTravel = new Travel(body.destination(), body.travelDate(), body.travelStatus());


        return this.travelRepository.save(newTravel);

    }

    public Travel autoStatusUpdate(Long id) {
        Travel travelFromDb = this.findById(id);
        if (travelFromDb.getDate().isAfter(LocalDate.now())) {
            travelFromDb.setTravelStatus(TravelStatus.COMPLETED);
        } else {
            travelFromDb.setTravelStatus(TravelStatus.SCHEDULED);
        }
        return this.travelRepository.save(travelFromDb);
    }

    public Travel findByIdAndUpdateStatus(Long id, UpdateTravelStatusDTO body) {
        Travel travelFromDb = this.findById(id);

        travelFromDb.setTravelStatus(body.travelStatus());
        return this.travelRepository.save(travelFromDb);
    }

    public void findByIdAndDelete(Long id) {
        Travel travelToDelete = this.findById(id);
        this.travelRepository.delete(travelToDelete);
    }
}
