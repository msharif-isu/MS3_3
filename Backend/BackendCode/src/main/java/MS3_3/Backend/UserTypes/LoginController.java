package MS3_3.Backend.UserTypes;

import MS3_3.Backend.AdminDashboard.Admin;
import MS3_3.Backend.AdminDashboard.AdminRepository;
import MS3_3.Backend.Ambassador.AmbassadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


/**
 * Postman json test text
 * <p>
 * {
 * "email": "@iastate",
 * "firstName": "Jackson",
 * "lastName": "Collalti",
 * "userName": "Collalti1",
 * "password": "Password",
 * "state": "IL",
 * "city":"St Charles",
 * "userType": "Admin"
 * }
 */

@RestController
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    AdminRepository adminRepository;
    @Autowired
    AmbassadorRepository ambassadorRepository;

    @GetMapping("/Users/login/{userName}/{password}")
    public User loginIn(@PathVariable String userName, @PathVariable String password) {
        if (userRepository.existsById(userName) && userRepository.findByUserName(userName).getPassword().equals(password)) {
            return userRepository.findByUserName(userName);
        } else {
            return null;
        }
    }

}
