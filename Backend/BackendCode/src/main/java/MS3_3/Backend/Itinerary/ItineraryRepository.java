package MS3_3.Backend.Itinerary;

import MS3_3.Backend.UserTypes.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItineraryRepository extends JpaRepository<Itinerary, Integer> {
    @Transactional
    void deleteByTripCode(String tripCode);

    Itinerary findByTripCode(String tripCode);

    List<Itinerary> findByCreator(User creator);
}
