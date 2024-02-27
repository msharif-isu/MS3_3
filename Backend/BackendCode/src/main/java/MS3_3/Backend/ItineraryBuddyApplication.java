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
		//Runs on http://localhost:8080
		//Or http://coms-309-035.class.las.iastate.edu:8080/Users
		SpringApplication.run(ItineraryBuddyApplication.class, args);
	}

}
