package MS3_3.Backend.Groups;

import MS3_3.Backend.UserTypes.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TravelGroupRepository extends JpaRepository<TravelGroup, Long> {
    TravelGroup findById(int id);

    @Transactional
    void deleteById(int id);
}
