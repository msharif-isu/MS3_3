package MS3_3.Backend.PostedItinerary;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<PostedItinerary.Comment, String> {
    @Transactional
    void deleteByCommentID(String commendID);

    PostedItinerary.Comment findByCommentID(String commentID);
}
