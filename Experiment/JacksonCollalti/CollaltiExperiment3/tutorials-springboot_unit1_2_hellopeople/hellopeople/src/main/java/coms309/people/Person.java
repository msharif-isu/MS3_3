package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Person {

    private String firstName;

    private String lastName;

    private String address;

    private String telephone;

    private String BucketList;

    private double budget;

    private String transportationType;

    private int groupSize;

    public Person(){
        
    }

    public Person(String firstName, String lastName, String address, String telephone,String BucketList,double budget,
    String transportationType,int groupSize){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.BucketList = BucketList;
        this.budget= budget;
        this.transportationType = transportationType;
        this.groupSize = groupSize;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return this.telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getBucketList() {
        return this.BucketList;
    }

    public void setBucketList(String BucketList) {
        this.BucketList = BucketList;
    }

    public double getBudget() {
        return this.budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getTransportationType() {
        return this.transportationType;
    }

    public void setTransportationType(String transportationType) {
        this.transportationType = transportationType;
    }

    public int getGroupSize() {
        return this.groupSize;
    }

    public void setGroupSize(int groupSize) {
        this.groupSize = groupSize;
    }

    @Override
    public String toString() {
        return firstName + " " 
               + lastName + " "
               + address + " "
               + telephone + " "
                + BucketList + " "
                + budget + " "
                + groupSize + " "
                + transportationType;
    }
}
