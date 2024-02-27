package MS3_3.Backend.AdminDashboard;

import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping("/Admin/Revoke/{adminUserName}/{username}")
    public void revokeAdmin(@PathVariable String adminUserName,@PathVariable String userName){
        if(adminRepository.existsById(adminUserName) == true){
            adminRepository.deleteByUserName(userName);
            userRepository.findByUserName(userName).setUserType("User");
        }else{}
    }

    @PutMapping("/Admin/Grant/{adminUserName}/{username}")
    public void grantAdmin(@PathVariable String adminUserName, @PathVariable String userName){
        if(adminRepository.existsById(adminUserName) == true){
            userRepository.findByUserName(userName).setUserType("Admin");
            adminRepository.save(new Admin(userRepository.findByUserName(userName)));
        }else{}
    }

    @PutMapping("/Admin/DisablePosting/{adminUserName}/{username}")
    public void disablePosting(@PathVariable String adminUserName,@PathVariable String accountToDisable){
        if(adminRepository.existsById(adminUserName) == true){
            userRepository.findByUserName(accountToDisable).blockPosts();
        }else{}
    }

    /**
     * Wait til Itinerary class is created

    @DeleteMapping
    public void deleteItinerary(){

    }
    */

}
