package MS3_3.Backend.Groups;

import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelGroupController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;

    @GetMapping("/Group/All")
    public List<TravelGroup> getAllGroups() {
        return travelGroupRepository.findAll();
    }

    @PostMapping("/Group/Create")
    public TravelGroup createNewGroup(@RequestBody TravelGroup group) {
        travelGroupRepository.save(group);
        return travelGroupRepository.findByTravelGroupName(group.getTravelGroupCode());
    }

    @PutMapping("/Group/Update")
    public TravelGroup updateGroup(@RequestBody TravelGroup group) {
        travelGroupRepository.deleteById(group.getTravelGroupCode());
        travelGroupRepository.save(group);
        return travelGroupRepository.findByTravelGroupName(group.getTravelGroupCode());
    }

    @PutMapping("/Group/AddUser/{groupId}/{UserAdded}")
    public TravelGroup addMember(@RequestParam String groupId, @RequestParam String username) {
       // groupRepository.findByGroupId(groupId).addMembers(userRepository.findByUserName(username));
        return travelGroupRepository.findByTravelGroupName(groupId);
    }



}
