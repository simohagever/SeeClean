package com.example.lastproject.repstory;

/**
 * This class represents a User with personal details such as first name, last name,
 * password, email, and address.
 */
public class User {

    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String address;

    /**
     * Constructor to initialize a User object.
     *
     * @param firstName First name of the user
     * @param lastName  Last name of the user
     * @param password  Password of the user
     * @param email     Email address of the user
     * @param address   Physical address of the user
     */
    public User(String firstName, String lastName, String password, String email, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    /**
     * Gets the first name of the user.
     *
     * @return First name of the user
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user.
     *
     * @param firstName First name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the last name of the user.
     *
     * @return Last name of the user
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user.
     *
     * @param lastName Last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets the password of the user.
     *
     * @return Password of the user
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     *
     * @param password Password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email address of the user.
     *
     * @return Email address of the user
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email address of the user.
     *
     * @param email Email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the physical address of the user.
     *
     * @return Physical address of the user
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the physical address of the user.
     *
     * @param address Physical address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }
}
