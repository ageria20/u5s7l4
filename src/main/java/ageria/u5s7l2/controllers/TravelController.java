package ageria.u5s7l2.controllers;


import ageria.u5s7l2.dto.TravelDTO;
import ageria.u5s7l2.dto.UpdateTravelStatusDTO;
import ageria.u5s7l2.entities.Travel;
import ageria.u5s7l2.exception.BadRequestException;
import ageria.u5s7l2.services.TravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/travels")
public class TravelController {

    @Autowired
    TravelService travelService;

    // 1 GET PAGE
    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Page<Travel> findAll(@RequestParam(defaultValue = "0") int pages,
                                @RequestParam(defaultValue = "10") int size,
                                @RequestParam(defaultValue = "id") String sortBy) {
        return this.travelService.getAllEmployee(pages, size, sortBy);
    }

    // 1.1 GET BY ID
    @GetMapping("/{travelId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Travel findById(@PathVariable Long travelId) {
        return this.travelService.findById(travelId);
    }


    //2. POST
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Travel createEmployee(@RequestBody @Validated TravelDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = (String) bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }
        return this.travelService.saveEmployee(body);
    }

    // PUT UPDATE STATUS
    @PutMapping("/status/{travelId}")
    @ResponseStatus(HttpStatus.OK)
    public Travel updateEmployee(@PathVariable Long travelId, @RequestBody UpdateTravelStatusDTO body, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String msg = (String) bindingResult.getAllErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining());
            throw new BadRequestException(msg);
        }

        return this.travelService.findByIdAndUpdateStatus(travelId, body);
    }

    @DeleteMapping("/{travelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleteEmploye(@PathVariable Long travelId) {
        try {
            this.travelService.findByIdAndDelete(travelId);
            return "Travel Correctly DELETED";
        } catch (DataIntegrityViolationException ex) {
            throw new BadRequestException("YOU CANNOT DELETE A TRAVEL THAT IS LINKED TO A BOOKING");
        }
    }
}
