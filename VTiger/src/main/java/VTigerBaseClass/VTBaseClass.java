package VTigerBaseClass;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import VTigerPomPages.VT_LoginHome_Pom_Page;
import VTigerUtilities.VTProertyUtilties;

public class VTBaseClass {
	 protected WebDriver driver;
	    protected WebDriverWait wait;

	    @BeforeSuite(groups= {"functional", "integration", "negative", "smoke","system","ui"})
	    public void beforeSuite() {

	        // Runs once before the complete test suite
	    		Reporter.log("VTiger Test Suite Started", true);
	    }

	    @BeforeTest(groups= {"functional", "integration", "negative", "smoke","system","ui"})
	    public void beforeTest() {

	        // Runs before the <test> tag in TestNG XML
	        System.out.println("Executing Test Preconditions");
	    }
	    
	    @Parameters("browser")
	    @BeforeClass(groups = {"functional", "integration", "negative", "smoke", "system", "ui"})
	    public void launchBrowser(@Optional("chrome") String xmlBrowser) throws Exception {

	        VTProertyUtilties prop = new VTProertyUtilties();
	        String browser = xmlBrowser;
	        String url = prop.getDataFromPropertiesFile("url");

	        ChromeOptions settings = new ChromeOptions();
	        Map<String, Object> prefs = new HashMap<>();
	        prefs.put("profile.password_manager_leak_detection", false);
	        settings.setExperimentalOption("prefs", prefs);

	        if (browser.equalsIgnoreCase("chrome")) {
	            driver = new ChromeDriver(settings);
	        } else if (browser.equalsIgnoreCase("edge")) {
	            driver = new EdgeDriver();
	        } else if (browser.equalsIgnoreCase("firefox")) {
	            driver = new FirefoxDriver();
	        } else {
	            throw new IllegalArgumentException("Invalid browser: " + browser);
	        }

	        driver.manage().window().maximize();
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(0));
	        wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        driver.get(url);
	        Reporter.log("Browser launched successfully: " + browser, true);
	    }
	    

	    @BeforeMethod(groups = {"functional", "integration", "negative", "smoke", "system", "ui"})
	    public void LoginToWebsite(ITestResult result) throws Exception {

	        VTProertyUtilties properties = new VTProertyUtilties();
	        String url = properties.getDataFromPropertiesFile("url");
	        String testClassName = result.getTestClass().getName();

	        driver.manage().deleteAllCookies();
	        driver.get(url);

	        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

	        wait.until(ExpectedConditions.presenceOfElementLocated( By.name("user_name")));

	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("user_name")));
	        if (testClassName.endsWith("VT_LoginHome_Page")) {
	            Reporter.log("Login page opened with a fresh session.", true);
	            return;
	        }

	        String username = properties.getDataFromPropertiesFile("username");
	        String password = properties.getDataFromPropertiesFile("password");

	        VT_LoginHome_Pom_Page loginPage = new VT_LoginHome_Pom_Page(driver);
	        loginPage.login(username, password);

	        Reporter.log("Login successful", true);
	    }





	    @AfterClass(groups = {"functional", "integration", "negative","smoke", "system", "ui"})
	    	public void closeBrowser() {
	    	    if (driver != null) {
	    	        driver.quit();
	    	        driver = null;
	    	    }

	    	    Reporter.log("Browser Closed Successfully", true);
	    	}
	    
	    
	    @AfterSuite(groups= {"functional", "integration", "negative", "smoke","system","ui"})
	    public void AfterSuite() {

	        // Runs before the <test> tag in TestNG XML
	    		System.out.println("VTiger Test Suite Completed");
	    }

}
