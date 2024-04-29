package onetoone.Timing;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import onetoone.Groups.StudyGroup;


import javax.xml.datatype.Duration;
import java.time.DayOfWeek;
import java.time.LocalTime;

@Entity
@Table(name="timing")
public class Timing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalTime startTime;
    private int duration;
    private DayOfWeek day;

    private String location;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    private StudyGroup studyGroup;

    public Timing(LocalTime startTime, int duration, DayOfWeek day, String location) {
        this.startTime = startTime;
        this.duration = duration;
        this.day = day;
        this.location = location;
    }


    public Timing() {

    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }


    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public DayOfWeek getDay() {
        return day;
    }

    public void setDay(DayOfWeek day) {
        this.day = day;
    }

    public StudyGroup getGroup() {
        return studyGroup;
    }

    public void setGroup(StudyGroup group) {
        this.studyGroup = group;
    }
}

