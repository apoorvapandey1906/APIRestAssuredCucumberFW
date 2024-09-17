package cucumber.Options;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		          features="src/test/java/features",
                  glue={"stepDefinitions","Hooks"},
                 plugin = {"pretty", "html:reports/myreport.html", //junit report
                				  "rerun:target/rerun.txt",
                				  "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:" //extentreports
                				  },
                		 dryRun=false,
                				 monochrome=true,
                						 publish=true, 
                  tags= "@AddPlace")
public class TestRunner {

}

