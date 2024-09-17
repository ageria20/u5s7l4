package ageria.u5s7l2.repositories;

import ageria.u5s7l2.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    Optional<Employee> findByEmail(String emial);

    boolean existsByEmail(String email);


}


