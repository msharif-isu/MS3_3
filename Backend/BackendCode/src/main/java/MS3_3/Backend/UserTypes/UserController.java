package MS3_3.Backend.UserTypes;

import MS3_3.Backend.TravelGroups.TravelGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;

    @GetMapping("/Users/All")
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/Users/{userName}")
    public User getUser(@PathVariable String userName) {
        return userRepository.findByUserName(userName);
    }

    @PostMapping("/Users/Create")
    public User createPerson(@RequestBody User person) {
        userRepository.save(person);
        return userRepository.findByUserName(person.getUserName());
    }

    @DeleteMapping("/Users/{userName}")
    public void deleteUser(@PathVariable String userName) {
        userRepository.deleteByUserName(userName);
    }

    @PutMapping("/Users/{userName}")
    public User changeInfo(@PathVariable String userName, @RequestBody User person) {
        userRepository.deleteByUserName(userName);
        userRepository.save(person);
        return userRepository.findByUserName(person.getUserName());
    }

    @GetMapping("/Users/Groups/{userName}")
    public List<Integer> showGroups(@PathVariable String userName) {
        return userRepository.findByUserName(userName).getUserCodes();
    }

}
