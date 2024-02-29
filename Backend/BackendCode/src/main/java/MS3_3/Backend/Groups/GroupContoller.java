package MS3_3.Backend.Groups;

import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import org.antlr.v4.runtime.tree.pattern.ParseTreePattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GroupContoller {
    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    @GetMapping("/Group/All")
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @PostMapping("/Group/Create")
    public Group createNewGroup(@RequestBody Group group) {
        groupRepository.save(group);
        return groupRepository.findById(group.getId());
    }

    @PutMapping("/Group/Update")
    public Group updateGroup(@RequestBody Group group) {
        groupRepository.deleteById(group.getId());
        groupRepository.save(group);
        return groupRepository.findById(group.getId());
    }

    @PutMapping("/Group/AddUser/{groupId}/{UserAdded}")
    public Group addMember(@RequestParam int groupId, @RequestParam String username) {
        groupRepository.findById(groupId).addMembers(userRepository.findByUserName(username));
        return groupRepository.findById(groupId);
    }



}
