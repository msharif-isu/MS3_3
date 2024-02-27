package MS3_3.Backend.AdminDashboard;

import MS3_3.Backend.UserTypes.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,String> {
    void deleteByUserName(String userName);

    Admin findByUserName(String userName);
}
