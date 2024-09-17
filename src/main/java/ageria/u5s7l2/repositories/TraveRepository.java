package ageria.u5s7l2.repositories;


import ageria.u5s7l2.entities.Travel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraveRepository extends JpaRepository<Travel, Long> {

    boolean existsByDestination(String destination);

}
