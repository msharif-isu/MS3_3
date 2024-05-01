package MS3_3.Backend.CommunityPost.Events;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommunityPostEventRepository extends JpaRepository<CommunityPostEvent, Integer> {
    @Transactional
    void deleteByCommunityPostEventId(int communityPostEventId);

    CommunityPostEvent findByCommunityPostEventId(int communityPostEventId);
}
