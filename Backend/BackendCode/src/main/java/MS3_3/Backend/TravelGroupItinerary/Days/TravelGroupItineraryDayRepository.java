package MS3_3.Backend.TravelGroupItinerary.Days;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupItineraryDayRepository extends JpaRepository<TravelGroupItineraryDay, Long> {
    @Transactional
    void deleteById(int id);

    TravelGroupItineraryDay findById(int id);
}
