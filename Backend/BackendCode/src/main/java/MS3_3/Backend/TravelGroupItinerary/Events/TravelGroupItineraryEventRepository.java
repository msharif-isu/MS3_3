package MS3_3.Backend.TravelGroupItinerary.Events;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupItineraryEventRepository extends JpaRepository<TravelGroupItineraryEvent, Long> {
    @Transactional
    void deleteByTravelGroupItineraryEventId(int travelGroupItineraryEventId);

    TravelGroupItineraryEvent findByTravelGroupItineraryEventId(int travelGroupItineraryEventId);
}
