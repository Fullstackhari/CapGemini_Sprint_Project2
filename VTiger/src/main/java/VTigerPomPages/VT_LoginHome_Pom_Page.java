package VTigerPomPages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VT_LoginHome_Pom_Page {

    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[@class='input']/descendant::input[@name='user_name']")
    private WebElement usernameTextField;

    @FindBy(xpath = "//div[@class='input']/descendant::input[@name='user_password']")
    private WebElement passwordTextField;

    @FindBy(id = "submitButton")
    private WebElement loginButton;

    @FindBy(xpath = "//td[@class='small']/descendant::img[@src='themes/softed/images/user.PNG']")
    private WebElement logoutoption;

    @FindBy(xpath = "//a[text()='Sign Out']")
    private WebElement logoubtn;
    
    @FindBy(xpath = "//div[@class='errorMessage']")
    private WebElement errorMessage;
    
    public VT_LoginHome_Pom_Page(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public void enterUsername(String username) {
        wait.until(ExpectedConditions.visibilityOf(usernameTextField));
        usernameTextField.clear();
        usernameTextField.sendKeys(username);
    }

    public void enterPassword(String password) {
        wait.until(ExpectedConditions.visibilityOf(passwordTextField));
        passwordTextField.clear();
        passwordTextField.sendKeys(password);
    }

    public void clickLoginButton() {
        wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        loginButton.click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLoginButton();
    }

    public boolean isUsernameFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(usernameTextField)).isDisplayed();
    }

    public boolean isPasswordFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(passwordTextField)).isDisplayed();
    }

    public boolean isLoginButtonDisplayed() {
        return wait.until(
            ExpectedConditions.visibilityOf(loginButton)
        ).isDisplayed();
    }

    public void getLogoubtn() {
        wait.until(ExpectedConditions.elementToBeClickable(logoubtn)).click();
    }

    public void getLogoutoption() {
        wait.until(ExpectedConditions.elementToBeClickable(logoutoption)).click();
    }


	public boolean isHomePageDisplayed() {
	    return wait.until(ExpectedConditions.visibilityOf(logoutoption)).isDisplayed();
	}
	
	public boolean isErrorMessageDisplayed() {
	    return wait.until(
	        ExpectedConditions.visibilityOf(errorMessage)
	    ).isDisplayed();
	}

}