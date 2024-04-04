package MS3_3.Backend.PostedItinerary;

import MS3_3.Backend.UserTypes.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostedItineraryRepository extends JpaRepository<PostedItinerary, String> {
    @Transactional
    void deleteByPostID(String postID);

    PostedItinerary findByPostID(String postID);

    List<PostedItinerary> findByCreator(User creator);
}
