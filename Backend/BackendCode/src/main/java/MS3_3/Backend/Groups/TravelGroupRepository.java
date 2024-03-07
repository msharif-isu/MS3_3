package MS3_3.Backend.Groups;

import MS3_3.Backend.UserTypes.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupRepository extends JpaRepository<TravelGroup, String> {
    TravelGroup findByTravelGroupCode(String travelGroupCode);

    @Transactional
    void deleteByTravelGroupCode(String travelGroupCode);
}
