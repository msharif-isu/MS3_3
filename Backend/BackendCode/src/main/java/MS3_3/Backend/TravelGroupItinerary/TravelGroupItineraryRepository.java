package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.Itinerary.Itinerary;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupItineraryRepository extends JpaRepository<TravelGroupItinerary, Long> {
    @Transactional
    void deleteById(int Id);

    TravelGroupItinerary findById(int Id);
}
