package MS3_3.Backend.Groups;



import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.Ambassador.AmbassadorRepository;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.UserTypes.UserRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelGroup {

    private String travelGroupName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String travelGroupDestination;

    private String travelGroupLeader;

    private String travelGroupDescription;

    @OneToMany
    private List<User> members;



    public TravelGroup(String groupName,String userName,String travelDestination,String groupDescription){
        this.travelGroupName= groupName;
        this.travelGroupDestination = travelDestination;
        this.travelGroupLeader = userName;
        this.travelGroupDescription = groupDescription;
        members = new ArrayList<>();
    }

    public TravelGroup() {
        members = new ArrayList<>();
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> newMembers) {
        this.members = newMembers;
    }

    public void addNewMember(User user){
        this.members.add(user);
    }

    public int getTravelGroupId() {
        return id;
    }


    public String getTravelGroupName() {
        return travelGroupName;
    }

    public void setTravelGroupName(String groupName) {
        this.travelGroupName = groupName;
    }

    public String getTravelGroupDestination() {
        return travelGroupDestination;
    }

    public void setTravelGroupDestination(String groupDestination) {
        this.travelGroupDestination = groupDestination;
    }

    public String getTravelGroupLeader() {
        return travelGroupLeader;
    }


    public String getTravelGroupDescription() {
        return travelGroupDescription;
    }

    public void setTravelGroupDescription(String groupDescription) {
        this.travelGroupDescription = groupDescription;
    }

}
