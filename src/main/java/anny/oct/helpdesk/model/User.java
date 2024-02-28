package anny.oct.helpdesk.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

import java.util.StringJoiner;

public class User {

    private int id;

    private String firstName;

    private String lastName;

    private int roleId;
    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid ")
    private String email;

    @NotEmpty(message = "Email shouldn't be empty")
    private String password;

    public User() {    }

    public User(int id, String firstName, String lastName, int roleId, String email, String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName=lastName;
        this.roleId= roleId;
        this.email = email;
        this.password= password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", roleId=" + roleId +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

}
