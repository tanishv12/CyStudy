package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import onetoone.Courses.Course;
import onetoone.Courses.CourseRepository;
import onetoone.Groups.StudyGroup;
import onetoone.Groups.StudyGroupRepository;
import onetoone.Messages.Message;
import onetoone.Messages.MessageRepository;
import onetoone.Timing.Timing;
import onetoone.Users.User;
import onetoone.Users.UserRepository;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SaeshuSystemTest {

    @LocalServerPort
    int port;

    @Autowired
    UserRepository userRepository;

    @Autowired
    StudyGroupRepository groupRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    MessageRepository messageRepository;
    @Before
    public void setUp() {
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";


    }

    /**
     * Tests if the group is created properly
     */

    @Test
    public void groupTest() {
        // Send request and receive response
        Response response = RestAssured.given().
                header("Content-Type", "text/plain").
                header("charset", "utf-8").
                body("").
                when().
                post("/groups/post/COMS 309 GROUP 1/john123/COMS 309/5");


        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("Group created successfully!", returnString);

    }

    /**
     * Tests by creating a course that already exists
     * @throws JSONException
     */
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

    /**
     * Tests whether user is registering properly
     * @throws JSONException
     */
    @Test
    public void userTest() throws JSONException {

        // Create a JSON object with the desired data
        JSONObject requestBody = new JSONObject();
        requestBody.put("name", "Saeshu");
        requestBody.put("userName", "sk123");
        requestBody.put("emailId", "sk@gmail.com");
        requestBody.put("password","12345");

        // Send request and receive response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set content type to JSON
                .body(requestBody.toString()) // Convert JSON object to string and set as request body
                .when()
                .post("/users/register");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("User created successfully", returnString);
    }

    /**
     * Tests whether user has logged in properly
     * @throws JSONException
     */
    @Test
    public void loginTest() throws JSONException {

        // Send request and receive response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set content type to JSON
                .body("") // Convert JSON object to string and set as request body
                .when()
                .post("/users/login/saeshu123/lol");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("Login successful", returnString);
    }


    @Test
    public void getTimingTest() throws JSONException {

        // Send request and receive response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set content type to JSON
                .body("") // Convert JSON object to string and set as request body
                .when()
                .get("/timings/group/MATH 165 GROUP 1");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

    }

    @Test
    public void getTimingByUser() throws JSONException {

        // Send request and receive response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set content type to JSON
                .body("") // Convert JSON object to string and set as request body
                .when()
                .get("/timings/user/john123");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }


        @Test
        public void addTimingTest() throws JSONException {

            // Create a JSON object with the desired data
            JSONObject requestBody = new JSONObject();
            requestBody.put("date", LocalDate.ofEpochDay(2024-01-05));
            requestBody.put("day", DayOfWeek.TUESDAY);
            requestBody.put("startTime", LocalTime.of(4,37,00));
            requestBody.put("location", "SIC");
            requestBody.put("duration",1);

            // Send request and receive response
            Response response = RestAssured.given()
                    .header("Content-Type", "application/json") // Set content type to JSON
                    .body(requestBody.toString()) // Convert JSON object to string and set as request body
                    .when()
                    .post("/timings/post/MATH 165 GROUP 1/end");

            // Check status code
            int statusCode = response.getStatusCode();
            assertEquals(200, statusCode);

            // Check response body for correct response
            String returnString = response.getBody().asString();
            assertEquals("Timing added to group successfully", returnString);
        }


        @Test
        public void updateLocation() throws JSONException {

            // Send request and receive response
            Response response = RestAssured.given()
                    .header("Content-Type", "application/json") // Set content type to JSON
                    .body("SIC: location updated") // Convert JSON object to string and set as request body
                    .when()
                    .put("/timings/location/MATH 165 GROUP 1/1");

            // Check status code
            int statusCode = response.getStatusCode();
            assertEquals(200, statusCode);

            // Check response body for correct response
            String returnString = response.getBody().asString();
            assertEquals("Location added to timing successfully", returnString);
        }

        @Test
        public void getAllMessages() throws JSONException {
            // Send request and receive response
            Response response = RestAssured.given()
                    .header("Content-Type", "application/json") // Set content type to JSON
                    .body("") // Convert JSON object to string and set as request body
                    .when()
                    .get("/messages/all");

            // Check status code
            int statusCode = response.getStatusCode();
            assertEquals(200, statusCode);
        }

    @Test
    public void getMessagesByGroup() throws JSONException {
        // Send request and receive response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set content type to JSON
                .body("") // Convert JSON object to string and set as request body
                .when()
                .get("/messages/all/group/MATH 165 GROUP 1");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }

    @Test
    public void getMessagesByUser() throws JSONException {
        // Send request and receive response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set content type to JSON
                .body("") // Convert JSON object to string and set as request body
                .when()
                .get("/messages/all/user/john123");

        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);
    }


    @Test
    public void createMessage() throws JSONException {

        User user = new User("Lalu","lalu123","lalu@gmail.com","12345");
        Course course = new Course("Calculus 3","MATH",246);
        courseRepository.save(course);
        StudyGroup group = new StudyGroup("dummy group",course,"lalu123",5);
        Message message = new Message("How are you?", user, group); // Create a Message object

        userRepository.save(user);
        groupRepository.save(group);
        // Create JSON objects for user and group
        JSONObject userJson = new JSONObject();
        userJson.put("userName", user.getUserName());
        userJson.put("emailId", user.getEmailId());
        userJson.put("name", user.getName());
        userJson.put("password","12345");

        JSONObject courseJson = new JSONObject();
        courseJson.put("courseTitle","Calculus 3");
        courseJson.put("courseDepartment","MATH");
        courseJson.put("courseCode",246);


        JSONObject groupJson = new JSONObject();
        groupJson.put("groupName", group.getGroupName());
        groupJson.put("course", courseJson); // Assuming Course also needs to be converted to JSON
        groupJson.put("groupMaster","lalu123");
        groupJson.put("groupCapacity",5);
        // Add other group properties as needed

        // Create a JSON object with the desired data
        JSONObject requestBody = new JSONObject();
        requestBody.put("messageContent", message.getMessageContent()); // Include message content
        requestBody.put("user", userRepository.findByUserName("lalu123"));
        requestBody.put("group", groupRepository.findStudyGroupByGroupName("dummy course"));

        // Send request and receive response
        Response response = RestAssured.given()
                .header("Content-Type", "application/json") // Set content type to JSON
                .body(requestBody.toString()) // Convert JSON object to string and set as request body
                .when()
                .post("/messages/post/dummy group/lalu123");

        System.out.println(response.toString());
        // Check status code
        int statusCode = response.getStatusCode();
        assertEquals(200, statusCode);

        // Check response body for correct response
        String returnString = response.getBody().asString();
        assertEquals("{\"message\":\"success\"}", returnString);
    }


//    @Test
//    public void updateMessage() throws JSONException {
//
//        User user = new User("Lalu","lalu123","lalu@gmail.com","12345");
//        Course course = new Course("Calculus 3","MATH",246);
//        courseRepository.save(course);
//        StudyGroup group = new StudyGroup("dummy group",course,"lalu123",5);
//        Message message = new Message("Updated: How are you?", user, group); // Create a Message object
//
//
//        userRepository.save(user);
//        groupRepository.save(group);
//        messageRepository.save(message);
//
//        // Create JSON objects for user and group
//        JSONObject userJson = new JSONObject();
//        userJson.put("userName", user.getUserName());
//        userJson.put("emailId", user.getEmailId());
//        userJson.put("name", user.getName());
//        userJson.put("password","12345");
//
//        JSONObject courseJson = new JSONObject();
//        courseJson.put("courseTitle","Calculus 3");
//        courseJson.put("courseDepartment","MATH");
//        courseJson.put("courseCode",246);
//
//
//        JSONObject groupJson = new JSONObject();
//        groupJson.put("groupName", group.getGroupName());
//        groupJson.put("course", courseJson); // Assuming Course also needs to be converted to JSON
//        groupJson.put("groupMaster","lalu123");
//        groupJson.put("groupCapacity",5);
//        // Add other group properties as needed
//
//        // Create a JSON object with the desired data
//        JSONObject requestBody = new JSONObject();
//        requestBody.put("messageContent", message.getMessageContent()); // Include message content
//        requestBody.put("sender", userJson);
//        requestBody.put("group", groupJson);
//
//        // Send request and receive response
//        Response response = RestAssured.given()
//                .header("Content-Type", "application/json") // Set content type to JSON
//                .body(requestBody.toString()) // Convert JSON object to string and set as request body
//                .when()
//                .put("/messages/update/"+message.getId());
//
//
//
//        System.out.println(response.toString());
//        // Check status code
//        int statusCode = response.getStatusCode();
//        assertEquals(200, statusCode);
//
//        // Check response body for correct response
//        String returnString = response.getBody().asString();
//        assertEquals("{\"message\":\"success\"}", returnString);
//    }


    }

