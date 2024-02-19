package MS3_3.Backend;

import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ItineraryBuddyApplication {

	public static void main(String[] args) throws Exception {
		//Runs on http://localhost:7070
		SpringApplication.run(ItineraryBuddyApplication.class, args);
	}

	/**
	 * String email,String firstName,String lastName,String userName,String password,String state,String city,
	 *                 String userType
	 * @param userRepository
	 * @return
	 */

	@Bean
	CommandLineRunner initUser(UserRepository userRepository) {
		return args -> {
			User user1 = new User("@iastate1", "Jackson",
					"Collalti", "Collalti1",
					"C1",
					"IL", "St Charles",
					"Admin");
            User user2 = new User("@iastate2", "Jack",
					"Collalti", "Collalti2",
					"C2",
					"IL", "St Charles",
					"Admin");
			User user3 = new User("@iastate3", "J",
					"Co", "Collalti3",
					"C3",
					"IL", "St Charles",
					"Admin");
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);

		};
	}
}
