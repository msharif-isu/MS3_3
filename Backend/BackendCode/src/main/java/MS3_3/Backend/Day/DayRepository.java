package MS3_3.Backend.Day;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
public interface DayRepository extends JpaRepository<Day,String> {
    @Transactional
    void deleteByUniqueCode(String uniqueCode);

    Day findByUniqueCode(String uniqueCode);
}
