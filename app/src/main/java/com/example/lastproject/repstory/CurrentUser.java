package com.example.lastproject.repstory;

/**
 * A class to manage the current user's email.
 */
public class CurrentUser {
    // Static variable to store the current user's email
    static String email;

    /**
     * Initialize the current user with the provided email.
     *
     * @param Email The email of the current user.
     */
    public static void initilizeUser(String Email) {
        email = Email;
    }

    /**
     * Get the email of the current user.
     *
     * @return The email of the current user.
     */
    public static String getEmail() {
        return email;
    }

    /**
     * Set the email of the current user.
     *
     * @param email The email of the current user.
     */
    public static void setEmail(String email) {
        CurrentUser.email = email;
    }
}