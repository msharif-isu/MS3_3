package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.Itinerary.Itinerary;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupItineraryRepository extends JpaRepository<Itinerary, Integer> {
    @Transactional
    void deleteByShareCode(int shareCode);

    Itinerary findByShareCode(int shareCode);
}
