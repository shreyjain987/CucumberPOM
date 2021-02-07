package appHooks;

import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.qa.factory.DriverFactory;
import com.qa.util.ConfigReader;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;

public class ApplicationHooks {

	private DriverFactory driverFactory;
	private WebDriver driver;
	private ConfigReader configReader;
	Properties prop;

	@Before(order = 0)
	public void getProperty() {
		configReader = new ConfigReader();
		prop = configReader.init_prop();
	}

	@Before(order = 1)
	public void launchBrowser(Scenario scenario) {
		System.out.println("*********************");
		System.out.println("Release: " + prop.getProperty("release"));
		System.out.println("Browser: " + prop.getProperty("browser"));
		System.out.println("Environment: " + prop.getProperty("environment"));
		System.out.println("User: " + prop.getProperty("userName"));
		System.out.println("Scenario: " + scenario.getName());
		System.out.println("*********************");
		String browserName = prop.getProperty("browser");
		driverFactory = new DriverFactory();
		driver = driverFactory.init_driver(browserName);
	}

	@After(order = 0)
	public void quitBrowser(Scenario scenario) {
		driver.close();
		System.out.println("Browser is Closed");

		// Quit browser
		driver.quit();
		System.out.println("Quit browser");
		System.out.println("***************************************" + "\n" + "Execution Status: "
				+ scenario.getStatus() + "\n" + "***************************************");
		
		System.out.println("----------------- End of Scenario: " + scenario.getName() + " -----------------");
	}

	@After(order = 1)
	public void tearDown(Scenario scenario) {
		if (scenario.isFailed()) {
			String screenshotName = scenario.getName().replaceAll(" ", "_");
			byte[] sourcerPath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			scenario.attach(sourcerPath, "image/png", screenshotName);
		}
	}
}
