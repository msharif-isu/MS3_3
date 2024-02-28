package MS3_3.Backend.UserTypes;

import MS3_3.Backend.AdminDashboard.Admin;
import MS3_3.Backend.AdminDashboard.AdminRepository;
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

    @Autowired
    AdminRepository adminRepository;

    @GetMapping("/Users/All")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/Users/login/{userName}/{password}")
    public User loginIn(@PathVariable String userName, @PathVariable String password){
        if(userRepository.existsById(userName) && userRepository.findByUserName(userName).getPassword().equals(password)){
            return userRepository.findByUserName(userName);
        }
        else {
            return null;
        }
    }

    @GetMapping("/Users/{userName}")
    public User getUser(@PathVariable String userName) {
        return userRepository.findByUserName(userName);
    }

    @PostMapping("/Users/Create")
    public  User createPerson(@RequestBody User person) {
        userRepository.save(person);
        if (person.getUserType().equals("Admin")){
            adminRepository.save(new Admin(userRepository.findByUserName(person.getUserName())));
        }
        return userRepository.findByUserName(person.getUserName());
    }

    @DeleteMapping("/Users/{userName}")
    public void deleteUser(@PathVariable String userName){
        userRepository.deleteByUserName(userName);
    }

    @PutMapping("/Users/{userName}")
    public User changeInfo(@PathVariable String userName,@RequestBody User person){
        userRepository.deleteByUserName(userName);
        userRepository.save(person);
        return userRepository.findByUserName(person.getUserName());
    }


}
