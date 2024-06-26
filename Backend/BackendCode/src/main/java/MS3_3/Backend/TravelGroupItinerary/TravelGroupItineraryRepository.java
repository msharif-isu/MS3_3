package MS3_3.Backend.TravelGroupItinerary;

import MS3_3.Backend.TravelGroups.TravelGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupItineraryRepository extends JpaRepository<TravelGroupItinerary, Long> {
    @Transactional
    void deleteByTravelGroupItineraryId(int travelGroupItineraryId);

    TravelGroupItinerary findByTravelGroupItineraryId(int travelGroupItineraryId);

    @Transactional
    void deleteByTravelGroupItineraryId(TravelGroup travelGroup);

    TravelGroupItinerary findByTravelGroupItineraryId(TravelGroup travelGroup);
}
