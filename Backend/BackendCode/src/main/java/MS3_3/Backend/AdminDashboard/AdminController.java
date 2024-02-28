package MS3_3.Backend.AdminDashboard;

import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    //Class Requirements
    //Revoke admin by username,
    //Grant admin by username,
    // Disable posting ability,
    // Delete Public Itinerary
    @Autowired
    AdminRepository adminRepository;

    @Autowired
    UserRepository userRepository;

    @PutMapping("/Admin/Revoke/{adminUserName}/{userName}")
    public User revokeAdmin(@PathVariable String adminUserName,@PathVariable String userName){
        if(adminRepository.existsById(adminUserName) == true){
            adminRepository.deleteByUserName(userName);
            userRepository.findByUserName(userName).setUserType("User");
        }else{}
        return userRepository.findByUserName(userName);
    }

    @PostMapping("/Admin/Create")
    public  Admin createPerson(@RequestBody Admin person) {
        adminRepository.save(person);
        userRepository.save(new User(person.getEmail(), person.getUserName(),person.getPassword(),person.getState(),person.getCity(),
                person.getUserType()));
        return adminRepository.findByUserName(person.getUserName());
    }

    @PutMapping("/Admin/Grant/{adminUserName}/{userName}")
    public Admin grantAdmin(@PathVariable String adminUserName, @PathVariable String userName){
        if(adminRepository.existsById(adminUserName) == true){
            userRepository.findByUserName(userName).setUserType("Admin");
            adminRepository.save(new Admin(userRepository.findByUserName(userName)));
        }else{}
        return adminRepository.findByUserName(userName);
    }

    @PutMapping("/Admin/DisablePosting/{adminUserName}/{accountToDisable}")
    public Admin disablePosting(@PathVariable String adminUserName,@PathVariable String accountToDisable){
        if(adminRepository.existsById(adminUserName) == true){
            userRepository.findByUserName(accountToDisable).blockPosts();
        }else{}
        return adminRepository.findByUserName(accountToDisable);
    }

    @PutMapping("/Users/FirstAdmin/{userName}")
    public Admin changeInfo(@PathVariable String userName){
        userRepository.findByUserName(userName).setUserType("Admin");
        adminRepository.save(new Admin(userRepository.findByUserName(userName)));
        return adminRepository.findByUserName(userName);
    }

    @GetMapping("/Admin/All")
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @DeleteMapping("/Admin/Remove/{userName}")
    public String deleteAdmin(@PathVariable String userName){
        adminRepository.deleteByUserName(userName);
        userRepository.deleteByUserName(userName);
        return "Account " + userName + " Deleted";
    }

    /**
     * Wait til Itinerary class is created
    @DeleteMapping
    public Admin deleteItinerary(){

    }
    */

}
