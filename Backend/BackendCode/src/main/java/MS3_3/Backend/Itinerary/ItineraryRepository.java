package MS3_3.Backend.Itinerary;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItineraryRepository extends JpaRepository<Itinerary,Integer> {
    @Transactional
    void deleteByShareCode(int shareCode);

    Itinerary findByShareCode(int shareCode);
}
