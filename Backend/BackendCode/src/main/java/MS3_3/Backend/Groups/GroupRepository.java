package MS3_3.Backend.Groups;

import MS3_3.Backend.UserTypes.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group,Long> {
    Group findById(int id);

    @Transactional
    void deleteById(int id);
}
