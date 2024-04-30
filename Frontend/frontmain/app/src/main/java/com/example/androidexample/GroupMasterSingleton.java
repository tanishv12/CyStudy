package com.example.androidexample;

public class GroupMasterSingleton
{
    private static GroupMasterSingleton instance;
    private String masterName;
    private GroupMasterSingleton() {}

    public static synchronized GroupMasterSingleton getInstance()
    {
        if (instance == null) {
            instance = new GroupMasterSingleton();
        }
        return instance;
    }

    public String getGroupMaster()
    {
        return masterName;
    }

    public void setGroupMaster(String masterName)
    {
        this.masterName = masterName;
    }
}
