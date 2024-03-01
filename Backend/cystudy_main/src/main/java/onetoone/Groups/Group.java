package onetoone.Groups;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.sql.Timestamp;

@Entity
public class Group {

    @Id
    public int id;

    public String groupName;

    public Timestamp creationTime;





}
