
package ecom.VTiger;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import VTigerBaseClass.VTBaseClass;
import VTigerPomPages.VT_LoginHome_Pom_Page;
import VTigerUtilities.ScreenShotUtilities;
import VTigerUtilities.VTExcelUtilities;
import VTigerUtilities.VTProertyUtilties;

public class VT_LoginHome_Page extends VTBaseClass {

	
    // TC-VT-LOGIN-01: Verify login with valid credentials
    @Test(priority=1,groups = {"functional"})
    public void verifyLoginWithValidCredentials() throws Exception {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
    		VTProertyUtilties properties=new VTProertyUtilties();
        String username = properties.getDataFromPropertiesFile("username");
        String password = properties.getDataFromPropertiesFile("password");

        loginPage.login(username, password);
        Thread.sleep(1000);
        Assert.assertFalse(driver.getCurrentUrl().contains("index.php?module=Users&action=Login"),"Login page is still displayed.");
        
        Reporter.log("----------TC-VT-LOGIN-01: Login with valid credentials completed.----------", true);
        
    }

    // TC-VT-LOGIN-02: Verify login page elements visibility
    @Test(priority=2,groups = {"functional"})
    public void verifyLoginPageElementsVisibility() {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
		VTProertyUtilties properties=new VTProertyUtilties();
        Assert.assertTrue(loginPage.isUsernameFieldDisplayed(), "Username field is not displayed.");
        Assert.assertTrue(loginPage.isPasswordFieldDisplayed(), "Password field is not displayed.");
        Assert.assertTrue(loginPage.isLoginButtonDisplayed(), "Login button is not displayed.");
        Reporter.log("----------TC-VT-LOGIN-02: Login page elements verified.----------", true);
        
    }

    // TC-VT-LOGIN-03: Verify home page elements visibility
    @Test(priority=3,groups = {"functional"})
    public void verifyHomePageElementsVisibility() throws Exception {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
		VTProertyUtilties properties=new VTProertyUtilties();
        String username = properties.getDataFromPropertiesFile("username");
        String password = properties.getDataFromPropertiesFile("password");

        loginPage.login(username, password);
        Thread.sleep(1000);
        Assert.assertTrue(driver.getPageSource().contains("Home"),"Home page content was not found.");
        Reporter.log("----------TC-VT-LOGIN-03: Home page visibility checked.----------", true);
        
    }


    // TC-VT-LOGIN-04: Verify login with invalid username
    @Test(priority=4,groups = {"negative"})
    public void verifyLoginWithInvalidUsername() throws Exception {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
		VTProertyUtilties properties=new VTProertyUtilties();
    		VTExcelUtilities excel = new VTExcelUtilities();

    		String username = excel.readDataFromExcel("Login_Negative_Test_Data", 3, 0);
    		String password = excel.readDataFromExcel("Login_Negative_Test_Data", 3, 1);

    		loginPage.login(username, password);
    		Thread.sleep(1000);
    		Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Error message should be displayed after entering an invalid username.");
    		Thread.sleep(1000);
    		//ScreenShotUtilities.captureScreenshot(driver, "TC-LOGIN-04_InvalidUsername");
    		Reporter.log("----------TC-VT-LOGIN-04: Invalid username test completed.----------", true);
    		
    }

    // TC-VT-LOGIN-05: Verify login with invalid password
    @Test(priority=5,groups = {"negative"})
    public void verifyLoginWithInvalidPassword() throws Exception {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
		VTProertyUtilties properties=new VTProertyUtilties();
    		VTExcelUtilities excel = new VTExcelUtilities();

    		String username = excel.readDataFromExcel("Login_Negative_Test_Data", 4, 0);
    		String password = excel.readDataFromExcel("Login_Negative_Test_Data", 4, 1);

    		loginPage.login(username, password);
    		Thread.sleep(1000);
    		Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Error message should be displayed after entering an invalid password.");
    		Thread.sleep(1000);
    		//ScreenShotUtilities.captureScreenshot(driver, "TC-LOGIN-05_InvalidPassword");
    		Reporter.log("----------TC-VT-LOGIN-05: Invalid password test completed.----------", true);
    		
    }

    // TC-VT-LOGIN-06: Verify login with blank username
    @Test(priority=6,groups = {"negative"})
    public void verifyLoginWithBlankUsername() throws Exception {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
		VTProertyUtilties properties=new VTProertyUtilties();
    		VTExcelUtilities excel = new VTExcelUtilities();

    		String username = excel.readDataFromExcel("Login_Negative_Test_Data", 2, 0);
    		String password = excel.readDataFromExcel("Login_Negative_Test_Data", 2, 1);

    		loginPage.login(username, password);
    		Thread.sleep(1000);
    		Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Error message should be displayed after submitting a blank username.");
    		Thread.sleep(1000);
    		//ScreenShotUtilities.captureScreenshot(driver, "TC-LOGIN-06_BlankUsername");
    		Reporter.log("----------TC-VT-LOGIN-06: Blank username test completed.----------", true);
    		
    }

    // TC-VT-LOGIN-07: Verify login with blank password
    @Test(priority=7,groups = {"negative"})
    public void verifyLoginWithBlankPassword() throws Exception {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
		VTProertyUtilties properties=new VTProertyUtilties();
    		VTExcelUtilities excel = new VTExcelUtilities();

    		String username = excel.readDataFromExcel("Login_Negative_Test_Data", 1, 0);
    		String password = excel.readDataFromExcel("Login_Negative_Test_Data", 1, 1);

    		loginPage.login(username, password);
    		Thread.sleep(1000);
    		Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Error message should be displayed after submitting a blank password.");
    		Thread.sleep(1000);
    		//ScreenShotUtilities.captureScreenshot(driver, "TC-LOGIN-07_BlankPassword");
    		Reporter.log("----------TC-VT-LOGIN-07: Blank password test completed.----------", true);
    		
    }


    // TC-VT-LOGIN-08: Verify navigation from Login page to Home page
    @Test(priority=8,groups = {"integration"})
    public void verifyLoginToHomePageIntegration() throws Exception {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
		VTProertyUtilties properties=new VTProertyUtilties();
        String username = properties.getDataFromPropertiesFile("username");
        String password = properties.getDataFromPropertiesFile("password");
        loginPage.login(username, password);
        Thread.sleep(1000);
        Assert.assertTrue(driver.getPageSource().contains("Home"),"Navigation to the home page failed.");
        Reporter.log("----------TC-VT-LOGIN-08: Login-to-home-page integration verified.----------", true);
        
    }

    // TC-VT-LOGIN-09: Verify critical login flow
	@Test(priority=9,groups = {"smoke"})
	public void verifyLoginSmokeTest() throws Exception {
	    VT_LoginHome_Pom_Page loginPage = new VT_LoginHome_Pom_Page(driver);
	    VTProertyUtilties properties = new VTProertyUtilties();
	
	    String username = properties.getDataFromPropertiesFile("username");
	    String password = properties.getDataFromPropertiesFile("password");
	
	    loginPage.login(username, password);
	    Thread.sleep(1000);
	    Assert.assertTrue(loginPage.isHomePageDisplayed(),"Critical login flow failed: Home page was not displayed.");
	
	    Reporter.log("----------TC-VT-LOGIN-09: Login smoke test passed.----------",true);
	}


    // TC-VT-LOGIN-10: Verify login input boundary values
    @Test(priority=10,groups = {"BVA"})
    public void verifyLoginInputBoundaryValues() throws InterruptedException {
    		VT_LoginHome_Pom_Page loginPage=new VT_LoginHome_Pom_Page (driver);
		VTProertyUtilties properties=new VTProertyUtilties();
        String boundaryUsername = "A".repeat(256);
        String boundaryPassword = "B".repeat(256);

        loginPage.login(boundaryUsername, boundaryPassword);
        Thread.sleep(1000);
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),"Error message should be displayed for boundary input values.");
        Reporter.log("----------TC-VT-LOGIN-10: Login boundary values checked.----------", true);
        System.out.println("Login Test Cases Passed Succesfully");
        
    }
}
