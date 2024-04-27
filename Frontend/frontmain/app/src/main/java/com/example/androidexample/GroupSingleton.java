package com.example.androidexample;

public class GroupSingleton
{
    private static GroupSingleton instance;
    private String GName;

    private GroupSingleton() {
    }

    // Static method to get instance of Singleton
    public static synchronized GroupSingleton getInstance() {
        if (instance == null) {
            instance = new GroupSingleton();
        }
        return instance;
    }

    // Getter and Setter for cards groupname
    public String getGroupName() {
        return GName;
    }

    public void setGroupName(String GName) {
        this.GName = GName;
    }
}
