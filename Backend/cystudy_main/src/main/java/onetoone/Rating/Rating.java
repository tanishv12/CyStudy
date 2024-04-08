package onetoone.Rating;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Data;
import onetoone.Courses.Course;
import onetoone.Groups.StudyGroup;
import onetoone.Users.User;

@Entity
@Table(name = "group_rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

//    @EmbeddedId
//    private RatingKey id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JoinColumn(name = "group_id")
    @JsonIgnore
    private StudyGroup studyGroup;

    @Column(name = "rating")
    private double rating;

//    @Column(name = "review")
//        private String review;

//    public String getReview() {
//        return review;
//    }
//
//    public void setReview(String review) {
//        this.review = review;
//    }

    public Rating(User user, StudyGroup studyGroup, double rating) {
        this.user = user;
        this.studyGroup = studyGroup;
        this.rating = rating;
    }

    public Rating() {
    }



//    public RatingKey getId() {
//        return id;
//    }
//
//    public void setId(RatingKey id) {
//        this.id = id;
//    }

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

    public StudyGroup getStudyGroup() {
        return studyGroup;
    }

    public void setStudyGroup(StudyGroup studyGroup) {
        this.studyGroup = studyGroup;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
         }
    }

