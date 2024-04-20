package MS3_3.Backend.Groups;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface TravelGroupRepository extends JpaRepository<TravelGroup, Long> {
    TravelGroup findByTravelGroupId(int travelGroupId);

    @Transactional
    void deleteByTravelGroupId(int travelGroupId);
}
