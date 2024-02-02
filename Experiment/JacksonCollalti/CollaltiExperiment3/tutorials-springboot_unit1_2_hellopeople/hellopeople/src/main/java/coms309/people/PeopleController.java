package coms309.people;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PathVariable;


import java.util.HashMap;

/**
 * Controller used to showcase Create and Read from a LIST
 *
 * @author Vivek Bengre
 */

@RestController
public class PeopleController {

    // Note that there is only ONE instance of PeopleController in 
    // Springboot system.
    HashMap<String, Person> peopleList = new  HashMap<>();

    //CRUDL (create/read/update/delete/list)
    // use POST, GET, PUT, DELETE, GET methods for CRUDL

    // THIS IS THE LIST OPERATION
    // gets all the people in the list and returns it in JSON format
    // This controller takes no input. 
    // Springboot automatically converts the list to JSON format 
    // in this case because of @ResponseBody
    // Note: To LIST, we use the GET method
    @GetMapping("/people")
    public  HashMap<String,Person> getAllPersons() {
        return peopleList;
    }

    // THIS IS THE CREATE OPERATION
    // springboot automatically converts JSON input into a person object and 
    // the method below enters it into the list.
    // It returns a string message in THIS example.
    // in this case because of @ResponseBody
    // Note: To CREATE we use POST method
    @PostMapping("/people")
    public  String createPerson(@RequestBody Person person) {
        System.out.println(person);
        peopleList.put(person.getFirstName(), person);
        return "New person "+ person.getFirstName() + " Saved";
    }

    // THIS IS THE READ OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We extract the person from the HashMap.
    // springboot automatically converts Person to JSON format when we return it
    // in this case because of @ResponseBody
    // Note: To READ we use GET method
    @GetMapping("/people/{firstName}")
    public Person getPerson(@PathVariable String firstName) {
        Person p = peopleList.get(firstName);
        return p;
    }

    @GetMapping("/people/bucketList/{firstName}")
    public String getTheBuckList(@PathVariable String firstName) {
        String BucketListString = peopleList.get(firstName).getFirstName() +
                " wants to go to " + peopleList.get(firstName).getBucketList();
        return BucketListString;
    }

    @GetMapping("/people/budget/{firstName}")
    public String getTheBudget(@PathVariable String firstName) {
        String budgetString = peopleList.get(firstName).getFirstName() +
                " sets has a budget of $" + peopleList.get(firstName).getBudget();
        return budgetString;
    }

    @GetMapping("/people/groupSize/{firstName}")
    public String getTheGroupSize(@PathVariable String firstName) {
        String groupSizeString = peopleList.get(firstName).getFirstName() +
                " wants to go with " + peopleList.get(firstName).getGroupSize() + " people";
        return groupSizeString;
    }

    @GetMapping("/people/transportation/{firstName}")
    public String getTheTransportation(@PathVariable String firstName) {
        String transportString = peopleList.get(firstName).getFirstName() +
                " wants to travel by " + peopleList.get(firstName).getTransportationType();
        return transportString;
    }

    @GetMapping("/people/info/{firstName}")
    public String getTheInfo(@PathVariable String firstName) {
        String InfoString = peopleList.get(firstName).getFirstName() +
                " on a budget of $" + peopleList.get(firstName).getBudget() + " wants to travel to " +
        peopleList.get(firstName).getBucketList() + " using " + peopleList.get(firstName).getTransportationType() + " with "
        + peopleList.get(firstName).getGroupSize() + " people";
        return InfoString;
    }

    // THIS IS THE UPDATE OPERATION
    // We extract the person from the HashMap and modify it.
    // Springboot automatically converts the Person to JSON format
    // Springboot gets the PATHVARIABLE from the URL
    // Here we are returning what we sent to the method
    // in this case because of @ResponseBody
    // Note: To UPDATE we use PUT method
    @PutMapping("/people/{firstName}")
    public Person updatePerson(@PathVariable String firstName, @RequestBody Person p) {
        peopleList.replace(firstName, p);
        return peopleList.get(firstName);
    }

    // THIS IS THE DELETE OPERATION
    // Springboot gets the PATHVARIABLE from the URL
    // We return the entire list -- converted to JSON
    // in this case because of @ResponseBody
    // Note: To DELETE we use delete method
    
    @DeleteMapping("/people/{firstName}")
    public HashMap<String, Person> deletePerson(@PathVariable String firstName) {
        peopleList.remove(firstName);
        return peopleList;
    }
}

