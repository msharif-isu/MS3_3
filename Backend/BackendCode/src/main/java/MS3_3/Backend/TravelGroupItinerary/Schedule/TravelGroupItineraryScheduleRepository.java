package MS3_3.Backend.TravelGroupItinerary.Schedule;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupItineraryScheduleRepository extends JpaRepository<TravelGroupItinerarySchedule, Long> {
    @Transactional
    void deleteByTravelGroupItineraryScheduleId(int travelGroupItineraryScheduleId);

    TravelGroupItinerarySchedule findByTravelGroupItineraryScheduleId(int travelGroupItineraryScheduleId);
}
