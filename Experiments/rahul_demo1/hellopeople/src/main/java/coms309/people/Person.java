package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 * @author Rahul Sudev
 */

public class Person {

    private String firstName;

    private String lastName;

    private String address;

    private String telephone;

    private String email;

    private userType userType;

    private String password;

    public Person(){

    }

    public Person(String firstName, String lastName, String address, String telephone,String email, userType userType){
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.userType = userType;
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

    public String getEmail(){
        return this.email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public userType getUserType(){
        return userType;
    }

    public void setUserType(userType userType){
        this.userType = userType;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    @Override
    public String toString() {
        return "Firstname: " + firstName + "\n"
                + "Lastname: " + lastName + "\n"
                + "address: " + address + "\n"
                +"telephone" + telephone + "\n"
                + "email: " + email + "\n"
                + "userType " + userType;

    }
}

