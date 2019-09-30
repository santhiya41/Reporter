package Demo;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import java.io.File;

import CommonLibraries.AppConstant;
import CommonLibraries.FunctionLibraries;
import cucumber.api.Scenario;

public class Test1 {
	Scenario scenario;

	
  @Test
  public void test() throws Throwable {
	  File f1 = FunctionLibraries.fn_CreateResultFolder();
	  File f2 = FunctionLibraries.fn_CreateFeatureFolder(f1, "FeatureName");
	  File f3 = FunctionLibraries.fn_CreateTestScriptNameFolder(f2, "ScenarioName");
	  File f4 = FunctionLibraries.fn_CreateHTML(f3);
	  
	  System.out.println(f4.getPath());
	  WebDriver driver;
	//Set System propery for firefox browser
		System.setProperty("webdriver.gecko.driver", "C://Users//ssoundaram//Documents//Learning//Selenium//geckodriver//geckodriver.exe");
		//Launch the browser
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		FunctionLibraries.fn_Start_HTML(f4.getPath(), "ScenarioName", "FeatureName");
		Thread.sleep(2000);
	  FunctionLibraries.fn_Update_HTML(f4.getPath(), "ScenarioName", "PASS", "abc", "123", driver, true);
	  FunctionLibraries.fn_End_HTML(f4.getPath());
  }
}