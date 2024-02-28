package MS3_3.Backend.Ambassador;


import MS3_3.Backend.AdminDashboard.AdminRepository;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AmbassadorController {
        //Class Requirements
        //Revoke Ambassador by username,
        //Grant Ambassador by username,
        // Disable posting ability,
        // Delete Public Itinerary
        @Autowired
        AdminRepository adminRepository;

        @Autowired
        UserRepository userRepository;

    @Autowired
    AmbassadorRepository ambassadorRepository;

        @PutMapping("/Ambassador/Revoke/{AmbassadorUserName}/{userName}")
        public void revokeAmbassador(@PathVariable String AmbassadorUserName, @PathVariable String userName){
            if(ambassadorRepository.existsById(AmbassadorUserName) == true){
                ambassadorRepository.deleteByUserName(userName);
                userRepository.findByUserName(userName).setUserType("User");
            }else{}
        }

        @PostMapping("/Ambassador/Create")
        public User createPerson(@RequestBody Ambassador person) {
            ambassadorRepository.save(person);
            return ambassadorRepository.findByUserName(person.getUserName());
        }

        @PutMapping("/Ambassador/Grant/{AmbassadorUserName}/{userName}")
        public void grantAmbassador(@PathVariable String AmbassadorUserName, @PathVariable String userName){
            if(ambassadorRepository.existsById(AmbassadorUserName) == true){
                userRepository.findByUserName(userName).setUserType("Ambassador");
                ambassadorRepository.save(new Ambassador(userRepository.findByUserName(userName)));
            }else{}
        }

        @PutMapping("/Ambassador/DisablePosting/{AmbassadorUserName}/{accountToDisable}")
        public void disablePosting(@PathVariable String AmbassadorUserName,@PathVariable String accountToDisable){
            if(ambassadorRepository.existsById(AmbassadorUserName) == true){
                userRepository.findByUserName(accountToDisable).blockPosts();
            }else{}
        }

        @PutMapping("/Users/FirstAmbassador/{userName}")
        public void changeInfo(@PathVariable String userName){
            userRepository.findByUserName(userName).setUserType("Ambassador");
            ambassadorRepository.save(new Ambassador(userRepository.findByUserName(userName)));
        }

        @GetMapping("/Ambassador/All")
        public List<Ambassador> getAllAmbassadors() {
            return ambassadorRepository.findAll();
        }

        @DeleteMapping("/Ambassador/Remove/{userName}")
        public void deleteAmbassador(@PathVariable String userName){
            ambassadorRepository.deleteByUserName(userName);
        }
}
