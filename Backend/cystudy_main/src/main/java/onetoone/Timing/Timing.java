package onetoone.Timing;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

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

    public Timing(LocalTime startTime, int duration, DayOfWeek day) {
        this.startTime = startTime;
        this.duration = duration;
        this.day = day;
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

}

