package onetoone.Timing;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import onetoone.Groups.StudyGroup;
import org.springframework.cglib.core.Local;


import javax.xml.datatype.Duration;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="timing")
public class Timing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDate date;
    private LocalTime startTime;
    private int duration;
    private DayOfWeek day;

    private String location;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "group_id")
    private StudyGroup studyGroup;

    public Timing(LocalDate date, LocalTime startTime, int duration, DayOfWeek day, String location) {
        this.date = date;
        this.startTime = startTime;
        this.duration = duration;
        this.day = day;
        this.location = location;
    }


    public Timing() {

    }

    private LocalTime createStartTime(String startTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");

        // Parse the string into a LocalTime object
        LocalTime time = LocalTime.parse(startTime, formatter);
        return time;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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

    public String sendStartTime(LocalTime time) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        String formattedTime = time.format(formatter);

        return formattedTime;
    }
}

