package MS3_3.Backend.Groups;



import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.ArrayList;

@Entity
public class TravelGroup {


    private String travelGroupName;

    @Id
    private String travelGroupCode;

    private String travelGroupDestination;

    private String travelGroupCreator;

    private String travelGroupDescription;

    public TravelGroup(String groupId, String groupName,String travelDestination,String groupCreator,String groupDescription){
        this.travelGroupCode= groupId;
        this.travelGroupName= groupName;
        this.travelGroupDestination = travelDestination;
        this.travelGroupCreator = groupCreator;
        this.travelGroupDescription = groupDescription;
    }
    public TravelGroup() {
        //groups = new ArrayList<>();
    }

    /**
    public ArrayList<User> getMembers() {
        return members;
    }

    public void addMembers(User userName) {
        this.members.add(userName);
    }
    */

    public String getTravelGroupCode() {
        return travelGroupCode;
    }

    public void setTravelGroupCode(String groupId) {
        this.travelGroupCode = groupId;
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

    public String getTravelGroupCreator() {
        return travelGroupCreator;
    }

    public void setTravelGroupCreator(String groupCreator) {
        this.travelGroupCreator = groupCreator;
    }

    public String getTravelGroupDescription() {
        return travelGroupDescription;
    }

    public void setTravelGroupDescription(String groupDescription) {
        this.travelGroupDescription = groupDescription;
    }

}


/**
@Entity
public class Group {

    @Id
    private String groupId;

    private String groupName;

    private String groupDestination;

    private String groupCreator;

    private String groupDescription;


    public Group(String groupId, String groupName,String travelDestination,String groupCreator,String groupDescription){

        this.groupId= groupId;
        this.groupName= groupName;
       this.groupDestination = travelDestination;
       this.groupCreator = groupCreator;
       this.groupDescription = groupDescription;
    }


    public ArrayList<User> getMembers() {
        return members;
    }

    public void addMembers(User userName) {
        this.members.add(userName);
    }

    public String getGroupId() {
        return groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupDestination() {
        return groupDestination;
    }

    public void setGroupDestination(String groupDestination) {
        this.groupDestination = groupDestination;
    }

    public String getGroupCreator() {
        return groupCreator;
    }

    public void setGroupCreator(String groupCreator) {
        this.groupCreator = groupCreator;
    }

    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
    **/
/**
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
 */
