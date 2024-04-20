package MS3_3.Backend.Groups;

import MS3_3.Backend.AdminDashboard.AdminRepository;
import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.Ambassador.AmbassadorRepository;
import MS3_3.Backend.FileUpload.Image;
import MS3_3.Backend.FileUpload.ImageRepository;
import MS3_3.Backend.Itinerary.Itinerary;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItinerary;
import MS3_3.Backend.TravelGroupItinerary.TravelGroupItineraryRepository;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import MS3_3.Backend.TravelGroupChat.Message;
import MS3_3.Backend.TravelGroupChat.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TravelGroupController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TravelGroupRepository travelGroupRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    TravelGroupItineraryRepository travelGroupItineraryRepository;

    @Autowired
    AmbassadorRepository ambassadorRepository;
    @Autowired
    MessageRepository messageRepository;

    @Autowired
    private ImageRepository imageRepository;

    @GetMapping("/Group")
    public List<TravelGroup> getAllGroups() {
        return travelGroupRepository.findAll();
    }

    @GetMapping("/Group/{GroupId}")
    public TravelGroup getGroupsByTravelGroupId(@PathVariable int GroupId) {
        return travelGroupRepository.findByTravelGroupId(GroupId);
    }

    @PostMapping("/Group")
    public TravelGroup createNewGroup(@RequestBody TravelGroup group) {
        if (ambassadorRepository.findByUserName(group.getTravelGroupAmbassador()) != null) {
            TravelGroup savedGroup = group;
            TravelGroupItinerary newItinerary = new TravelGroupItinerary("", "", "", 0);
            travelGroupItineraryRepository.save(newItinerary);
            Ambassador groupLeader = ambassadorRepository.findByUserName(group.getTravelGroupAmbassador());
            User groupLeaderUserAccount = userRepository.findByUserName(group.getTravelGroupAmbassador());
            savedGroup.addNewMember(groupLeaderUserAccount);
            Image copy = new Image();
            copy.setImageData(imageRepository.findById(1).getImageData());
            copy.setType(imageRepository.findById(1).getType());
            copy.setName(imageRepository.findById(1).getName());
            imageRepository.save(copy);
            savedGroup.setGroupImage(copy);
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
        TravelGroup existingGroup = travelGroupRepository.findByTravelGroupId(groupId);
        existingGroup.setTravelGroupName(group.getTravelGroupName());
        existingGroup.setTravelGroupCode(group.getTravelGroupCode());
        existingGroup.setTravelGroupDescription(group.getTravelGroupDescription());
        existingGroup.setTravelGroupDestination(group.getTravelGroupDestination());
        travelGroupRepository.save(existingGroup);
        return travelGroupRepository.findByTravelGroupId(groupId);
    }

    @PutMapping("/Group/AddUser/{groupId}/{username}")
    public TravelGroup addMember(@PathVariable int groupId, @PathVariable String username) {
        TravelGroup group = travelGroupRepository.findByTravelGroupId(groupId);
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
        TravelGroup group = travelGroupRepository.findByTravelGroupId(groupId);
        User user = userRepository.findByUserName(username);
        group.removeNewMember(user);
        user.removeGroupCodes(group);
        user.removeUserCodes(group.getTravelGroupId());
        userRepository.save(user);
        travelGroupRepository.save(group);
        return group;
    }

    @DeleteMapping("/Group")
    public TravelGroup deleteGroup(@PathVariable int groupId, @PathVariable String username) {
        TravelGroup group = travelGroupRepository.findByTravelGroupId(groupId);
        if (adminRepository.findByUserName(username) == null) {
            return group;
        } else {
            int i = 0;
            List<User> memberList = travelGroupRepository.findByTravelGroupId(groupId).getMembers();
            while (memberList.size() > i) {
                User currUser = memberList.get(i);
                userRepository.findByUserName(currUser.getUserName()).removeUserCodes(groupId);
                userRepository.findByUserName(currUser.getUserName()).removeGroupCodes(travelGroupRepository.findByTravelGroupId(groupId));
                i++;
            }
        }
        travelGroupRepository.deleteByTravelGroupId(groupId);
        return null;
    }
}
