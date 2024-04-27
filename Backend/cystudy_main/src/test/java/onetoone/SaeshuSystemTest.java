package onetoone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.boot.test.web.server.LocalServerPort;	// SBv3

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
public class SaeshuSystemTest {

	@LocalServerPort
	int port;

	@Before
	public void setUp() {
		RestAssured.port = port;
		RestAssured.baseURI = "http://localhost:8080";
	}

	/**
	 * Tests if the group is created properly
	 */

	@Test
	public void groupTest() {
		// Send request and receive response
		Response response = RestAssured.given().
				header("Content-Type", "text/plain").
				header("charset","utf-8").
				body("").
				when().
				post("/groups/post/MATH 165 GROUP 1/john123/Calculus 1");


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
		requestBody.put("name", "Lalu");
		requestBody.put("userName", "lalu123");
		requestBody.put("emailId", "lalu@gmail.com");
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
}
