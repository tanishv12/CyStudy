package com.example.androidexample;

/**
 * This class is used to create a singleton that allows the username
 * to be accessed from the registration page to anywhere in the
 * application.
 */
public class UsernameSingleton {
    private static UsernameSingleton instance;
    private String userName;

    // Private constructor to prevent instantiation
    private UsernameSingleton() {}

    // Static method to get instance of Singleton
    public static synchronized UsernameSingleton getInstance()
    {
        if (instance == null) {
            instance = new UsernameSingleton();
        }
        return instance;
    }

    // Getter and Setter for userName
    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }
}
