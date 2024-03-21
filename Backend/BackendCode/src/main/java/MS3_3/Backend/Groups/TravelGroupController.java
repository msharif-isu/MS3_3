package MS3_3.Backend.Groups;

import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TravelGroupController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;

    @GetMapping("/Group")
    public List<TravelGroup> getAllGroups() {
        return travelGroupRepository.findAll();
    }

    @GetMapping("/Group/{GroupId}")
    public TravelGroup getGroupsById(@RequestParam int groupId) {
        return travelGroupRepository.findById(groupId);
    }

    @PostMapping("/Group")
    public TravelGroup createNewGroup(@RequestBody TravelGroup group) {
        travelGroupRepository.save(group);
        User Creator = userRepository.findByUserName(group.getTravelGroupLeader());
        travelGroupRepository.findById(group.getTravelGroupId()).addNewMember(Creator);
        return travelGroupRepository.findById(group.getTravelGroupId());
    }

    @PutMapping("/Group")
    public TravelGroup updateGroup(@RequestBody TravelGroup group) {
        travelGroupRepository.deleteById(group.getTravelGroupId());
        travelGroupRepository.save(group);
        return travelGroupRepository.findById(group.getTravelGroupId());
    }

    @PutMapping("/Group/AddUser/{groupId}/{username}")
    public TravelGroup addMember(@PathVariable int groupId, @PathVariable String username) {
        User newUser = userRepository.findByUserName(username);
        List<User> newMembers = travelGroupRepository.findById(groupId).getMembers();
        newMembers.add(newUser);
        travelGroupRepository.findById(groupId).setMembers(newMembers);
        travelGroupRepository.save(travelGroupRepository.findById(groupId));
        return travelGroupRepository.findById(groupId);
    }

    @PutMapping("/Group/RemoveUser/{groupId}/{username}")
    public TravelGroup removeMember(@PathVariable int groupId, @PathVariable String username) {
        User newUser = userRepository.findByUserName(username);
        List<User> newMembers = travelGroupRepository.findById(groupId).getMembers();
        newMembers.remove(newUser);
        travelGroupRepository.findById(groupId).setMembers(newMembers);
        travelGroupRepository.save(travelGroupRepository.findById(groupId));
        return travelGroupRepository.findById(groupId);
    }
}
