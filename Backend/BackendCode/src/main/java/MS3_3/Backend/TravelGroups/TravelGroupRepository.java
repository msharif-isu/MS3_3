package MS3_3.Backend.TravelGroups;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupRepository extends JpaRepository<TravelGroup, Long> {
    TravelGroup findByTravelGroupId(int travelGroupId);

    @Transactional
    void deleteByTravelGroupId(int travelGroupId);
}
