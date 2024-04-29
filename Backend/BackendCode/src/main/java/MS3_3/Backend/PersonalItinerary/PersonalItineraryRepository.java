package MS3_3.Backend.PersonalItinerary;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalItineraryRepository extends JpaRepository<PersonalItinerary, Long> {
    @Transactional
    void deleteByPersonalItineraryId(int personalItineraryId);

    PersonalItinerary findByPersonalItineraryId(int personalItineraryId);

    @Transactional
    void deleteByPersonalItineraryId(PersonalItinerary personalItinerary);
}
