package com.example.androidexample;

public class CourseNameSingleton
{
    private static CourseNameSingleton instance;
    private String courseName;

    private CourseNameSingleton() {
    }

    // Static method to get instance of Singleton
    public static synchronized CourseNameSingleton getInstance() {
        if (instance == null) {
            instance = new CourseNameSingleton();
        }
        return instance;
    }

    // Getter and Setter for cards course name
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
