package MS3_3.Backend;

import MS3_3.Backend.AdminDashboard.AdminRepository;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ItineraryBuddyApplication {

	public static void main(String[] args) throws Exception {
		//Runs on http://localhost:8080
		//http://coms-309-035.class.las.iastate.edu:8080/Users
		SpringApplication.run(ItineraryBuddyApplication.class, args);
	}

	/**
	 * String email,String firstName,String lastName,String userName,String password,String state,String city,
	 *                 String userType
	 * @param userRepository
	 * @return
	 */
/**

	CommandLineRunner initUser(UserRepository userRepository, AdminRepository adminRepository) {
		return args -> {
			User user1 = new User("@iastate1", "Collalti1",
					"C1",
					"IL", "St Charles",
					"Admin");
			User user2 = new User("@iastate2","Collalti2",
					"C2",
					"IL", "St Charles",
					"User");
			User user3 = new User("@iastate3", "Collalti3",
					"C3",
					"IL", "St Charles",
					"User");
			adminRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);

		};
	}
*/

}
