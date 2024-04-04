package MS3_3.Backend.PostedItinerary;

import MS3_3.Backend.Itinerary.Itinerary;
import MS3_3.Backend.UserTypes.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostedItineraryRepository extends JpaRepository<Itinerary, String> {
    @Transactional
    void deleteByPostID(String postID);

    Itinerary findByPostID(String postID);

    List<Itinerary> findByCreator(User creator);
}
