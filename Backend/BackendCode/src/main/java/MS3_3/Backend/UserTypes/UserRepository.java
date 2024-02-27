package MS3_3.Backend.UserTypes;

import jakarta.transaction.Transactional;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,String>{

    @Transactional
    void deleteByUserName(String userName);

    User findByUserName(String userName);
}
