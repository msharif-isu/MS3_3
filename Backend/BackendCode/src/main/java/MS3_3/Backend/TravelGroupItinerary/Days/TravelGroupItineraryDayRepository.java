package MS3_3.Backend.TravelGroupItinerary.Days;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupItineraryDayRepository extends JpaRepository<TravelGroupItineraryDay, Long> {
    @Transactional
    void deleteByTravelGroupItineraryDayId(int travelGroupItineraryDayId);

    TravelGroupItineraryDay findByTravelGroupItineraryDayId(int travelGroupItineraryDayId);
}
