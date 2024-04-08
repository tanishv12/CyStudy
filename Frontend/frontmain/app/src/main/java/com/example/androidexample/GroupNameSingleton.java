package com.example.androidexample;

public class GroupNameSingleton
{
    private static GroupNameSingleton instance;
    private String GrpName;
    private GroupNameSingleton() {}

    // Static method to get instance of Singleton
    public static synchronized GroupNameSingleton getInstance()
    {
        if (instance == null) {
            instance = new GroupNameSingleton();
        }
        return instance;
    }

    // Getter and Setter for userName
    public String getGroupName()
    {
        return GrpName;
    }

    public void setGroupName(String GrpName)
    {
        this.GrpName = GrpName;
    }

}

