package MS3_3.Backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ItineraryBuddyApplication {

    public static void main(String[] args) throws Exception {
        //Runs on http://localhost:8080
        //Or http://coms-309-035.class.las.iastate.edu:8080/Users
        SpringApplication.run(ItineraryBuddyApplication.class, args);
    }

	/**
	 * String email,String firstName,String lastName,String userName,String password,String state,String city,
	 *                 String userType
	 * @param userRepository
	 * @return
	 */

	@Bean
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
			adminRepository.save(new Admin(user1));
			userRepository.save(user2);
			userRepository.save(user3);

		};
	}

	/**
	 * Initializes the itinerary with provided repositories.
	 *
	 * @param itineraryRepository Itinerary Repository
	 * @param dayRepository Day Repository
	 * @param placeRepository Place Repository
	 * @return CommandLineRunner
	 */
	@Bean
	CommandLineRunner initItinerary(ItineraryRepository itineraryRepository, DayRepository dayRepository, PlaceRepository placeRepository) {
		return args -> {
			Itinerary itinerary1 = new Itinerary("Itinerary1",
					"10/01/2002", "10/02/2002");
			Day day1_1 = new Day("France", itinerary1);
			Day day2_1 = new Day("England", itinerary1);
			itinerary1.addDay(day1_1);
			itinerary1.addDay(day2_1);
			Place place1_1 = new Place("Place1", "10:00", "11:30", day1_1);
			Place place2_1 = new Place("Place2", "12:00", "3:00", day1_1);
			Place place3_2 = new Place("Place3", "4:00", "7:00", day2_1);
			Place place4_2 = new Place("Place4", "8:00", "11:00", day2_1);
			day1_1.addPlace(place1_1);
			day1_1.addPlace(place2_1);
			day2_1.addPlace(place3_2);
			day2_1.addPlace(place4_2);

			Itinerary itinerary2 = new Itinerary("Itinerary2",
					"11/06/2002", "11/07/2006");
			Day day1_2 = new Day("Europe", itinerary2);
			Day day2_2 = new Day("America", itinerary2);
			itinerary2.addDay(day1_2);
			itinerary2.addDay(day2_2);
			Place place5_3 = new Place("Place5", "11:00", "1:00", day1_2);
			Place place6_3 = new Place("Place6", "2:00", "3:00", day1_2);
			Place place7_4 = new Place("Place7", "12:30", "2:00", day2_2);
			Place place8_4 = new Place("Place8", "3:00", "5:00", day2_2);
			day1_2.addPlace(place5_3);
			day1_2.addPlace(place6_3);
			day2_2.addPlace(place7_4);
			day2_2.addPlace(place8_4);

			itineraryRepository.save(itinerary1);
			itineraryRepository.save(itinerary2);
			dayRepository.save(day1_1);
			dayRepository.save(day2_1);
			dayRepository.save(day1_2);
			dayRepository.save(day2_2);
			placeRepository.save(place1_1);
			placeRepository.save(place2_1);
			placeRepository.save(place3_2);
			placeRepository.save(place4_2);
			placeRepository.save(place5_3);
			placeRepository.save(place6_3);
			placeRepository.save(place7_4);
			placeRepository.save(place8_4);
		};
	}

}
