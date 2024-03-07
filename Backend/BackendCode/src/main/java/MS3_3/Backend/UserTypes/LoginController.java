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

@RestController
public class LoginController {
    @Autowired
    UserRepository userRepository;


    @GetMapping("/Users/login/{userName}/{password}")
    public User loginIn(@PathVariable String userName, @PathVariable String password){
        if(userRepository.existsById(userName) && userRepository.findByUserName(userName).getPassword().equals(password)){
            return userRepository.findByUserName(userName);
        }
        else {
            return null;
        }
    }

}
