package MS3_3.Backend.UserTypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/Users/All")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/Users/{userName}")
    public User getUser(@PathVariable String userName) {
        return userRepository.findByUserName(userName);
    }

    @PostMapping("/Users/Create")
    public  String createPerson(@RequestBody User person) {
        System.out.println(person);
        userRepository.save(person);
        return "Account "+ person.getUserName() + " Created";
    }

    @DeleteMapping("/Users/{userName}")
    public String deleteUser(@PathVariable String userName){
        userRepository.deleteByUserName(userName);
        return "User Deleted";
    }

    @PutMapping("/Users/{userName}")
    public String changeInfo(@PathVariable String userName,@RequestBody User person){
        userRepository.deleteByUserName(userName);
        userRepository.save(person);
        return "User Info Changed";
    }

    /**
     * Postman json test text
     *
      {
          "email": "@iastate",
          "firstName": "Jackson",
          "lastName": "Collalti",
          "userName": "Collalti1",
          "password": "Password",
          "state": "IL",
          "city":"St Charles",
          "userType": "Admin"
      }
     */
    /**
     Non Repository code, delete when Repository code is fully functional

    HashMap<String, User> userHashMap = new  HashMap<>();

    @PostMapping("/Users/Create")
    public  String createPerson(@RequestBody User person) {
        System.out.println(person);
        userHashMap.put(person.getUserName(), person);
        return "Account "+ person.getUserName() + " Created";
    }

    @GetMapping("/Users/All")
    public  HashMap<String,User> getAllUsers() {
        return userHashMap;
    }

    @GetMapping("/Users/login/{userName}/{password}")
    public boolean loginIn(@PathVariable String userName, @PathVariable String password){
        //userHashMap.containsKey(userName) == true
        if(userHashMap.get(userName).getPassword().equals(password)){
            return true;
        }
        else {
            return false;
        }
    }

    @PutMapping("/Users/{userName}")
    public String changeInfo(@PathVariable String userName,@RequestBody User person){
        userHashMap.replace(userName, person);
        return "User Info Changed";
    }

    @DeleteMapping("/Users/{userName}")
    public String deleteUser(@PathVariable String userName){
        userHashMap.remove(userName);
        return "User Deleted";
    }
    */

}
