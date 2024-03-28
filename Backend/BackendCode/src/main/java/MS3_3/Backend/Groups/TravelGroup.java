package MS3_3.Backend.Groups;


import MS3_3.Backend.Ambassador.Ambassador;
import MS3_3.Backend.Itinerary.Itinerary;
import MS3_3.Backend.UserTypes.User;
import MS3_3.Backend.TravelGroupChat.Message;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class TravelGroup {

    private String travelGroupName;

    @OneToOne
    @JoinColumn(name = "shareCode")
    private Itinerary itinerary;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String travelGroupDestination;

    private String travelGroupCode;

    private String travelGroupAmbassador;

    private String travelGroupDescription;
    @ElementCollection(fetch = FetchType.EAGER)
    //@OneToMany(mappedBy = "travelGroup", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Message> chatMessages;

    @ManyToMany(mappedBy = "groupCodes")
    private List<User> members;

    @ManyToOne
    @JoinColumn(name = "ambassador_user_name")
    @JsonIgnore
    private Ambassador group_creator;


    public TravelGroup(String groupName, String groupCode, String userName, String travelDestination, String groupDescription) {
        this.travelGroupName = groupName;
        this.travelGroupCode = groupCode;
        this.travelGroupDestination = travelDestination;
        this.travelGroupAmbassador = userName;
        this.travelGroupDescription = groupDescription;
        this.members = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
    }

    public TravelGroup() {
        this.members = new ArrayList<>();
        this.chatMessages = new ArrayList<>();
    }

    public List<Message> getChatMessages() {
        return this.chatMessages;
    }

    public void setChatMessages(List<Message> chatMessages) {
        this.chatMessages = chatMessages;
    }

    public void addChatMessages(Message chat) {
        this.chatMessages.add(chat);
    }

    public void deleteChatMessages(Message chat) {
        this.chatMessages.remove(chat);
    }

    public List<User> getMembers() {
        return this.members;
    }

    public void setMembers(List<User> newMembers) {
        this.members = newMembers;
    }

    public void addNewMember(User user) {
        this.members.add(user);
    }

    public void removeNewMember(User user) {
        this.members.remove(user);
    }

    public void setTravelGroupAmbassador(String travelGroupAmbassador) {
        this.travelGroupAmbassador = travelGroupAmbassador;
    }

    public Ambassador getGroup_creator() {
        return group_creator;
    }

    public String getTravelGroupCode() {
        return travelGroupCode;
    }

    public void setTravelGroupCode(String travelGroupCode) {
        this.travelGroupCode = travelGroupCode;
    }

    public void setGroup_creator(Ambassador group_creator) {
        this.group_creator = group_creator;
    }

    public String getTravelGroupAmbassador() {
        return travelGroupAmbassador;
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
