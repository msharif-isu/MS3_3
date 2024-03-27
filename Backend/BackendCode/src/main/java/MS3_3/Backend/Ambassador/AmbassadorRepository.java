package MS3_3.Backend.Ambassador;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmbassadorRepository extends JpaRepository<Ambassador, String> {
    @Transactional
    void deleteByUserName(String userName);

    Ambassador findByUserName(String userName);
}
