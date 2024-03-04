package MS3_3.Backend.Groups;

import MS3_3.Backend.UserTypes.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupRepository extends JpaRepository<TravelGroup, String> {
    TravelGroup findByTravelGroupName(String travelGroupCode);

    @Transactional
    void deleteByTravelGroupName(String travelGroupCode);
}
