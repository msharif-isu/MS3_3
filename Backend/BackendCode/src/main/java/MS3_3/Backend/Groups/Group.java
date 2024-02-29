package MS3_3.Backend.Groups;

import MS3_3.Backend.UserTypes.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Group {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    private String groupName;

    private String groupDestination;

    private String groupCreator;

    private String groupDescription;

    private List<User> members;


    @ManyToOne
    @JoinColumn(name = "user_userName")
    @JsonIgnore
    private User user;

    public Group(String groupName,String travelDestination,String groupCreator,String groupDescription){
       this.groupName= groupName;
       this.groupDestination = travelDestination;
       this.groupCreator = groupCreator;
       this.groupDescription = groupDescription;
       this.members = new ArrayList<>(); {
       }
    }

    public Group(){

    }

    public void getMembers(List<User> members) {
        this.members = members;
    }

    public void addMembers(User userName) {
        this.members.add(userName);
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
