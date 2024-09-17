package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {

	
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		//execute this only when placeID=null and add a code to create placeID
		
		StepDefinitions sd = new StepDefinitions();
		if(StepDefinitions.placeID==null) {
		sd.add_place_payload_with("Shetty","French-IN","Asia");
		sd.user_calls_with_post_http_method("AddPlaceAPI", "POST");
		sd.verify_place_id_created_maps_to_using("Shetty", "GetPlaceAPI");
		}
	}
}
