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

    private String travelGroupAmbassador;

    private String travelGroupDescription;

    @ManyToMany(mappedBy = "groups")
    private List<User> members;

    @ManyToOne
    @JoinColumn(name = "ambassador_user_name")
    @JsonIgnore
    private Ambassador group_creator;

    public TravelGroup(String groupName,String userName,String travelDestination,String groupDescription){
        this.travelGroupName= groupName;
        this.travelGroupDestination = travelDestination;
        this.travelGroupAmbassador = userName;
        this.travelGroupDescription = groupDescription;
        this.members = new ArrayList<>();
    }

    public void setTravelGroupAmbassador(String travelGroupAmbassador) {
        this.travelGroupAmbassador = travelGroupAmbassador;
    }

    public Ambassador getGroup_creator() {
        return group_creator;
    }

    public void setGroup_creator(Ambassador group_creator) {
        this.group_creator = group_creator;
    }

    public TravelGroup() {
        this.members = new ArrayList<>();
    }

    public String getTravelGroupAmbassador() {
        return travelGroupAmbassador;
    }



    public List<User> getMembers() {
        return this.members;
    }

    public void setMembers(List<User> newMembers) {
        this.members = newMembers;
    }

    public void addNewMember(User user){
        this.members.add(user);
    }

    public void removeNewMember(User user){
        this.members.remove(user);
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


    public String getTravelGroupDescription() {
        return travelGroupDescription;
    }

    public void setTravelGroupDescription(String groupDescription) {
        this.travelGroupDescription = groupDescription;
    }



}
