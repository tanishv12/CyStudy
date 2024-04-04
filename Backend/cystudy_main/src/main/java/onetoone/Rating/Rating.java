//package onetoone.Rating;
//
//import jakarta.persistence.*;
//
//import lombok.Data;
//import onetoone.Courses.Course;
//import onetoone.Groups.StudyGroup;
//import onetoone.Users.User;
//
//@Entity
//@Data
//@Table(name = "group_rating")
//public class Rating {
//
//    @EmbeddedId
//    private RatingKey id;
//
//    @ManyToOne
//   // @MapsId("user_id")
//    @JoinColumn(name = "user_id")
//    private User user;
//
//    @ManyToOne
//   // @MapsId("group_id")
//    @JoinColumn(name = "group_id")
//    private StudyGroup studyGroup;
//
//    @Column(name = "rating")
//    private int rating;
//
//    public Rating(RatingKey id, User user, StudyGroup studyGroup, int rating) {
//        this.id = id;
//        this.user = user;
//        this.studyGroup = studyGroup;
//        this.rating = rating;
//    }
//
//    public Rating() {
//    }
//
//    public RatingKey getId() {
//        return id;
//    }
//
//    public void setId(RatingKey id) {
//        this.id = id;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public StudyGroup getStudyGroup() {
//        return studyGroup;
//    }
//
//    public void setStudyGroup(StudyGroup studyGroup) {
//        this.studyGroup = studyGroup;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//}
