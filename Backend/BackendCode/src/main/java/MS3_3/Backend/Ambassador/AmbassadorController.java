package MS3_3.Backend.Ambassador;


import MS3_3.Backend.AdminDashboard.AdminRepository;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AmbassadorController {

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AmbassadorRepository ambassadorRepository;

    @PostMapping("/Ambassador/Create")
    public Ambassador createPerson(@RequestBody Ambassador person) {
        person.setUserType("Ambassador");
        ambassadorRepository.save(person);
        userRepository.save(new User(person.getEmail(), person.getUserName(), person.getPassword(), person.getState(), person.getCity(),
                person.getUserType()));
        return ambassadorRepository.findByUserName(person.getUserName());
    }

    @PutMapping("/Ambassador/Revoke/{adminUserName}/{userName}")
    public User revokeAmbassador(@PathVariable String adminUserName, @PathVariable String userName) {
        if (adminRepository.existsById(adminUserName)) {
            ambassadorRepository.deleteByUserName(userName);
            userRepository.findByUserName(userName).setUserType("User");
            userRepository.save(userRepository.findByUserName(userName));
        } else {
        }
        return userRepository.findByUserName(userName);
    }


    @PutMapping("/Ambassador/Grant/{adminUserName}/{userName}")
    public Ambassador grantAmbassador(@PathVariable String adminUserName, @PathVariable String userName) {
        if (adminRepository.existsById(adminUserName)) {
            userRepository.findByUserName(userName).setUserType("Ambassador");
            ambassadorRepository.save(new Ambassador(userRepository.findByUserName(userName)));
            userRepository.save(userRepository.findByUserName(userName));
        } else {
        }
        return ambassadorRepository.findByUserName(userName);
    }

    @PutMapping("/Users/FirstAmbassador/{userName}")
    public Ambassador changeInfo(@PathVariable String userName) {
        userRepository.findByUserName(userName).setUserType("Ambassador");
        ambassadorRepository.save(new Ambassador(userRepository.findByUserName(userName)));
        userRepository.save(userRepository.findByUserName(userName));
        return ambassadorRepository.findByUserName(userName);
    }

    @GetMapping("/Ambassador/All")
    public List<Ambassador> getAllAmbassadors() {
        return ambassadorRepository.findAll();
    }

    @DeleteMapping("/Ambassador/Remove/{userName}")
    public String deleteAmbassador(@PathVariable String userName) {
        userRepository.deleteByUserName(userName);
        ambassadorRepository.deleteByUserName(userName);
        return "Ambassador " + userName + " Deleted";
    }
}
