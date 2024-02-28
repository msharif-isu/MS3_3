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
    public void revokeAdmin(@PathVariable String adminUserName,@PathVariable String userName){
        if(adminRepository.existsById(adminUserName) == true){
            adminRepository.deleteByUserName(userName);
            userRepository.findByUserName(userName).setUserType("User");
        }else{}
    }

    @PostMapping("/Admin/Create")
    public  User createPerson(@RequestBody Admin person) {
        adminRepository.save(person);
        return adminRepository.findByUserName(person.getUserName());
    }

    @PutMapping("/Admin/Grant/{adminUserName}/{userName}")
    public void grantAdmin(@PathVariable String adminUserName, @PathVariable String userName){
        if(adminRepository.existsById(adminUserName) == true){
            userRepository.findByUserName(userName).setUserType("Admin");
            adminRepository.save(new Admin(userRepository.findByUserName(userName)));
        }else{}
    }

    @PutMapping("/Admin/DisablePosting/{adminUserName}/{accountToDisable}")
    public void disablePosting(@PathVariable String adminUserName,@PathVariable String accountToDisable){
        if(adminRepository.existsById(adminUserName) == true){
            userRepository.findByUserName(accountToDisable).blockPosts();
        }else{}
    }

    @PutMapping("/Users/FirstAdmin/{userName}")
    public void changeInfo(@PathVariable String userName){
        userRepository.findByUserName(userName).setUserType("Admin");
        adminRepository.save(new Admin(userRepository.findByUserName(userName)));
    }

    @GetMapping("/Admin/All")
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    @DeleteMapping("/Admin/Remove/{userName}")
    public void deleteAdmin(@PathVariable String userName){
        adminRepository.deleteByUserName(userName);
    }

    /**
     * Wait til Itinerary class is created
    @DeleteMapping
    public void deleteItinerary(){

    }
    */

}
