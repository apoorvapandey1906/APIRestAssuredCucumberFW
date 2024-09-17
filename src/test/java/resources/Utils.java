package resources;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Properties;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Utils {
	
	public static RequestSpecification req ;
	
	
	public RequestSpecification requestSpecification() throws IOException
	{
		//making req as static and introducing this if condition, will make sure that all the testcases in feature file 
		//will be mentioned in the log file. If we don't do it, then it will log for 1 tc and then get wiped out by the
		//2nd run and at the end only the last run log will be mentioned in the log file.
		if(req==null) {
		//Printing logs in a txt file
		PrintStream log = new PrintStream(new FileOutputStream("logging.txt"));
		req = new RequestSpecBuilder().setBaseUri(getGlobalValue("baseURL"))
				.addQueryParam("key", "qaclick123")
				//Below 2 method calls will log the details from both request and response on logging.txt file
				.addFilter(RequestLoggingFilter.logRequestTo(log))
				.addFilter(ResponseLoggingFilter.logResponseTo(log))
				.setContentType(ContentType.JSON).build();
		return req;
		}
		return req;
	}
	
	public static String getGlobalValue(String key) {
		Properties p = new Properties();
		try {
		
		FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
		p.load(fis);	
		}catch (Exception e) {
			e.printStackTrace();
		}
		return p.getProperty(key);
	}
	
	public String getJsonPath(Response response , String key) {
		String res = response.asString();
		JsonPath js = new JsonPath(res);
		return js.get(key).toString();
	}
}
