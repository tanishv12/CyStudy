package onetoone.Calendar;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import onetoone.Groups.StudyGroup;
import onetoone.Users.User;

import java.time.LocalDateTime;
import java.util.HashSet;

@Entity
@Table(name="calendar")
public class Calendar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    private User user;

    @OneToMany
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private HashSet<StudyGroup> groupSet;

    public Calendar(User user, HashSet<StudyGroup> groupSet) {
        this.user = user;
        this.groupSet = groupSet;
    }

    public Calendar() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public HashSet<StudyGroup> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(HashSet<StudyGroup> groupSet) {
        this.groupSet = groupSet;
    }
}
