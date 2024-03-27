package MS3_3.Backend.Ambassador;

import MS3_3.Backend.UserTypes.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbassadorRepository extends JpaRepository<Ambassador,String> {
    @Transactional
    void deleteByUserName(String userName);

    Ambassador findByUserName(String userName);
}
