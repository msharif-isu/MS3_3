package MS3_3.Backend.TravelGroupItinerary.Schedule;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupItineraryScheduleRepository extends JpaRepository<TravelGroupItinerarySchedule, Long> {
    @Transactional
    void deleteById(int Id);

    TravelGroupItinerarySchedule findById(int Id);
}
