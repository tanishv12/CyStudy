package coms309.people;


/**
 * Provides the Definition/Structure for the people row
 *
 * @author Vivek Bengre
 */

public class Person {

    private String firstName;

    private String lastName;

    private String email;
    private Role role;


    public Person(){

    }

    public Person(String firstName, String lastName, String email, Role role ){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.role = role;
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

    public String getEmail(){ return this.email;}
    public void setEmail(String email){
        this.email = email;
    }
    public Role getRole(){
        return this.role;
    }

    public void setRole(Role role){
        this.role = role;
    }

    @Override
    public String toString() {
        return "Firstname: " + firstName + "\n"
                + "Lastname: " + lastName + "\n"
                + "Email: " + email + "\n"
                + "Role: " + role;
    }
}
