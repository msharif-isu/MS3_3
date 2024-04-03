package MS3_3.Backend.Day;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DayRepository extends JpaRepository<Day, Integer> {
    @Transactional
    void deleteByUniqueCode(int uniqueCode);

    Day findByUniqueCode(int uniqueCode);
}
