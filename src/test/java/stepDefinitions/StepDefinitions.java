package stepDefinitions;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.text.Utilities;

import POJO.AddPlace;
import POJO.Location;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import resources.APIResources;
import resources.TestDataBuilder;
import resources.Utils;

public class StepDefinitions extends Utils{
	
	ResponseSpecification resSpec;
	RequestSpecification  res;
	Response response;
	TestDataBuilder data = new TestDataBuilder();
	JsonPath js;
	static String placeID ;

	@Given("Add Place Payload")
	public void add_place_payload() throws IOException {
		  res = given().spec(requestSpecification())
				.body(data.addPlacePayload());		
	}
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {
		 res = given().spec(requestSpecification())
					.body(data.addPlacePayload(name,language,address));	
	}
	
	@Given("DELETE Place Payload")
	public void delete_place_payload() throws IOException {
		 res = given().spec(requestSpecification())
				 .body(data.deletePlacePayload(placeID));
	}
	
	@When("User calls {string} with {string} http method")
	public void user_calls_with_post_http_method(String resource,String httpMethod) {
		//constructor of enum will be called with value of resource we pass
		APIResources apiResources = APIResources.valueOf(resource);
		System.out.println("APIName=>"+apiResources.getResource());
		
		resSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		if(httpMethod.equalsIgnoreCase("post"))
		  response = res.when().post(apiResources.getResource());
		else if(httpMethod.equalsIgnoreCase("get"))
			response = res.when().get(apiResources.getResource());
		else if(httpMethod.equalsIgnoreCase("delete"))
			response = res.when().delete(apiResources.getResource());
		  
	}
	
	@Then("The API call got success with status code as {int}.")
	public void the_api_call_got_success_with_status_code_as(Integer int1) {
	      assertEquals(response.getStatusCode(),200);
	}
	
	
	@Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String expectedValue) {
		String actualValue = getJsonPath(response, key);
		 assertEquals(expectedValue,actualValue);
	}
	
	@Then("verify place_ID created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedValue, String resource) throws IOException {
		placeID = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", placeID);
	     
		user_calls_with_post_http_method(resource,"GET");
		System.out.println(response.asPrettyString());
		
		String actualValue = getJsonPath(response, "name");
		assertEquals(expectedValue,actualValue);
	}
	
}
