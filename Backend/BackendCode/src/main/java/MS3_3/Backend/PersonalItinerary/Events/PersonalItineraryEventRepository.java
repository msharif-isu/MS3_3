package MS3_3.Backend.PersonalItinerary.Events;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalItineraryEventRepository extends JpaRepository<PersonalItineraryEvent, Long> {
    @Transactional
    void deleteByPersonalItineraryEventId(int personalItineraryEventId);

    PersonalItineraryEvent findByPersonalItineraryEventId(int personalItineraryEventId);
    }
