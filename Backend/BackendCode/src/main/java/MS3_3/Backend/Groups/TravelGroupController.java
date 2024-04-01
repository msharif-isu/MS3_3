package MS3_3.Backend.Groups;

import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.Ambassador.AmbassadorRepository;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TravelGroupController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;

    @Autowired
    AmbassadorRepository ambassadorRepository;

    @GetMapping("/Group")
    public List<TravelGroup> getAllGroups() {
        return travelGroupRepository.findAll();
    }

    @GetMapping("/Group/{GroupId}")
    public TravelGroup getGroupsById(@PathVariable int GroupId) {
        return travelGroupRepository.findById(GroupId);
    }

    @PostMapping("/Group")
    public TravelGroup createNewGroup(@RequestBody TravelGroup group) {
        if (ambassadorRepository.findByUserName(group.getTravelGroupAmbassador()) != null) {
            TravelGroup savedGroup = group;
            Ambassador groupLeader = ambassadorRepository.findByUserName(group.getTravelGroupAmbassador());
            User groupLeaderUserAccount = userRepository.findByUserName(group.getTravelGroupAmbassador());
            savedGroup.addNewMember(groupLeaderUserAccount);
            groupLeader.addGroup(savedGroup);
            groupLeaderUserAccount.addGroupCodes(group);
            TravelGroup newGroup = travelGroupRepository.save(savedGroup);
            groupLeaderUserAccount.addUserCodes(newGroup.getTravelGroupId());
            ambassadorRepository.save(groupLeader);
            userRepository.save(groupLeaderUserAccount);
            return savedGroup;
        } else {
            return null;
        }
    }

    @PutMapping("/Group/{groupId}")
    public TravelGroup updateGroup(@PathVariable int groupId, @RequestBody TravelGroup group) {
        TravelGroup existingGroup = travelGroupRepository.findById(groupId);
        existingGroup.setTravelGroupName(group.getTravelGroupName());
        existingGroup.setTravelGroupCode(group.getTravelGroupCode());
        existingGroup.setTravelGroupDescription(group.getTravelGroupDescription());
        existingGroup.setTravelGroupDestination(group.getTravelGroupDestination());
        travelGroupRepository.save(existingGroup);
        return travelGroupRepository.findById(groupId);
    }

    @PutMapping("/Group/AddUser/{groupId}/{username}")
    public TravelGroup addMember(@PathVariable int groupId, @PathVariable String username) {
        TravelGroup group = travelGroupRepository.findById(groupId);
        User user = userRepository.findByUserName(username);
        group.addNewMember(user);
        user.addUserCodes(group.getTravelGroupId());
        user.addGroupCodes(group);
        userRepository.save(user);
        travelGroupRepository.save(group);
        return group;
    }

    @PutMapping("/Group/RemoveUser/{groupId}/{username}")
    public TravelGroup removeMember(@PathVariable int groupId, @PathVariable String username) {
        TravelGroup group = travelGroupRepository.findById(groupId);
        User user = userRepository.findByUserName(username);
        group.removeNewMember(user);
        user.removeGroupCodes(group);
        user.removeUserCodes(group.getTravelGroupId());
        userRepository.save(user);
        travelGroupRepository.save(group);
        return group;
    }
}