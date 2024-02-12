package MS3_3.Backend.UserTypes;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class LoginController {

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
    HashMap<String, User> userHashMap = new  HashMap<>();

    @PostMapping("/Users/CreateUser")
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

}
