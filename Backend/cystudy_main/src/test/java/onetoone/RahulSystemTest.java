package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;


import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;
import onetoone.Groups.StudyGroup;
import onetoone.Groups.StudyGroupRepository;
import onetoone.Rating.Rating;
import onetoone.Rating.RatingRepository;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import onetoone.Rating.Rating;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class RahulSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudyGroupRepository studyGroupRepository;

    @Autowired
    RatingRepository ratingRepository;

    @Autowired
    CourseRepository courseRepository;

    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    public void GroupTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                get("/groups/all");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

//		// Check response body for correct response
//		String returnString = response.getBody().asString();
//		try {
//			JSONArray returnArr = new JSONArray(returnString);
//			JSONObject returnObj = returnArr.getJSONObject(returnArr.length()-1);
//			assertEquals("olleh", returnObj.get("data"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
    }

    @Test
    public void courseTest() throws JSONException {

        // Create a JSON object with the desired data
        JSONObject requestBody = new JSONObject();
        requestBody.put("courseCode", "311");
        requestBody.put("courseDepartment", "COM S");
        requestBody.put("courseName", "Intro to Desiging Algorithms");

        // Send request and receive response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set content type to JSON
                .body(requestBody.toString()) // Convert JSON object to string and set as request body
                .when()
                .post("/courses/post");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(400, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("Course already exists", returnString);
    }

    @Test
    public void RatingTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                post("/rating/post/MATH 165 GROUP 1/rahul123/5");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals("success", returnObj.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //
    @Test
    public void avgRatingTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                get("rating/average/MATH 165 GROUP 1/end");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals(0, returnObj.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void updateRatingTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                post("/rating/post/MATH 165 GROUP 1/sam123/4");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals("Updated Rating!", returnObj.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//    @Test
//    public void userGroupRatingTest() {
//        // Send request and receive response
//        Response response = RestAssured.given().
//                header("Content-Type", "text/plain").
//                header("charset", "utf-8").
//                body("").
//                when().
//                get("/rating/1/1");
//
//
//        // Check status code
//        int statusCode = response.getStatusCode();
//        assertEquals(200, statusCode);
//
//        // Check response body for correct response
//        String returnString = response.getBody().asString();
//        assertEquals("User is not in group", returnString );
//    }

    @Test
    public void GroupAddTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                put("/groups/update/john123/3");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
//		try {
//			JSONArray returnArr = new JSONArray(returnString);
//			JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
//			assertEquals("success", returnObj.get("data"));
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
    }
    @Test
    public void RatingMasterTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                post("/rating/post/MATH 165 GROUP 1/john123/5");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals("Group Master cannot rate group", returnObj.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void RatingUpdateTest() {
        User user = userRepository.findByUserName("sam123");
        StudyGroup group = studyGroupRepository.findStudyGroupByGroupName("MATH 165 GROUP 1");
        Rating rating = new Rating(user, group , 5);
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                post("/updateRating/MATH 165 GROUP 1/sam123/3");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        try {
            JSONArray returnArr = new JSONArray(returnString);
            JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
            assertEquals("successfully updated rating", returnObj.get("data"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void UpdateUsernameTest() throws JSONException {

        // Create a JSON object with the desired data
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("courseCode", "311");
//        requestBody.put("courseDepartment", "COM S");
//        requestBody.put("courseName", "Intro to Desiging Algorithms");

        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                put("/users/username/john123/nol123/beepboop");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
		try {
			JSONArray returnArr = new JSONArray(returnString);
			JSONObject returnObj = returnArr.getJSONObject(returnArr.length() - 1);
			assertEquals("User name changed successfully", returnObj.get("data"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }

}

