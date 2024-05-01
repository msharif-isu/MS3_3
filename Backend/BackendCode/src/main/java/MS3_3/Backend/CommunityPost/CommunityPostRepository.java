package MS3_3.Backend.CommunityPost;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostRepository extends JpaRepository<CommunityPost, Integer> {
    @Transactional
    void deleteByCommunityPostId(int communityPostId);

    CommunityPost findByCommunityPostId(int communityPostId);
}
