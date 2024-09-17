Feature: Validating Place API


	@AddPlace
Scenario: Verify if Place is getting added by AddPlace API. 
Given Add Place Payload
When User calls "AddPlaceAPI" with "POST" http method
Then The API call got success with status code as 200.
And "status" in response body is "OK"
And "scope" in response body is "APP"


		@AddPlace
Scenario Outline: Verify if Place is getting added by AddPlace API. 
Given Add Place Payload with "<name>" "<language>" "<address>"
When User calls "AddPlaceAPI" with "POST" http method
Then The API call got success with status code as 200.
And "status" in response body is "OK"
And "scope" in response body is "APP"
And verify place_ID created maps to "<name>" using "GetPlaceAPI"
Examples:
 | name   | language | address            |
 |AAhouse | English	 | World cross center |
 #|ABhouse | Latin 	 | LA cross center    |
 
 @DeletePlace
 Scenario: Verify if DeletePlaceAPI functionality is working
 Given DELETE Place Payload 
When User calls "DeletePlaceAPI" with "DELETE" http method
Then The API call got success with status code as 200.
And "status" in response body is "OK"
