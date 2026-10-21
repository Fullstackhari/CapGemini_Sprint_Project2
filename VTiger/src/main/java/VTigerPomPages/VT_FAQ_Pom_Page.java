package VTigerPomPages;

import java.time.Duration;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import VTigerUtilities.VTExcelUtilities;

public class VT_FAQ_Pom_Page {

    private WebDriver driver;
    private WebDriverWait wait;

    // FAQ navigation locators
    @FindBy(xpath = "//table[@class='hdrTabBg']/descendant::a[text()='More']")
    private WebElement moreMenu;

    @FindBy(xpath = "//div[@id='allMenu']/descendant::a[text()='FAQ']")
    private WebElement faqLink;

    // FAQ form locators
    @FindBy(xpath = "//table[@class='small']/descendant::img[@src='themes/softed/images/btnL3Add.gif']")
    private WebElement addFAQButton;

    @FindBy(xpath = "//td[@class='dvtCellInfo']/descendant::img")
    private WebElement productLookup;

    @FindBy(name = "faqstatus")
    private WebElement faqStatus;

    @FindBy(name = "question")
    private WebElement faqQuestion;

    @FindBy(name = "faq_answer")
    private WebElement faqAnswer;

    @FindBy(xpath = "//div[@align='center']/descendant::input[@class='crmbutton small save']")
    private WebElement saveFAQButton;
    @FindBy(name = "search_text")
    private WebElement productSearchField;

    @FindBy(xpath = "//input[@value='Search Now' or @title='Search Now']")
    private WebElement productSearchButton;
    
    //Test case 2 Locators
    @FindBy(xpath = "//div[@id='basicsearchcolumns_real']/descendant::select[@id='bas_searchfield']")
    private WebElement searchFieldDropdown;

    @FindBy(xpath = "//input[@name='search_text']")
    private WebElement searchTextField;

    @FindBy(xpath = "//input[@name='submit']")
    private WebElement searchButton;
    
    @FindBy(xpath = "//table[@class='hdrTabBg']//a[normalize-space()='FAQ']")
    private WebElement faqTab;
    public void navigateToFAQList() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();

        wait.until(ExpectedConditions.elementToBeClickable(faqLink)).click();

        wait.until(ExpectedConditions.urlContains("module=Faq"));
    }
    
    @FindBy(xpath = "(//input[@name='Delete' or @title='Delete'])[2]")
    private WebElement deleteButton;

    public void clickDeleteButton() {
        wait.until(
            ExpectedConditions.elementToBeClickable(deleteButton)
        ).click();
    }

    public void confirmDeleteIfPrompted() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (NoAlertPresentException | org.openqa.selenium.TimeoutException e) {
            // No browser alert appeared.
        }
    }
    
    public boolean isFAQSearchResultAbsent(String question) {

        By questionResult = By.xpath(
            "//span[@vtfieldname='question' " +
            "and @vtmodule='Faq' " +
            "and normalize-space(.)='" + question + "']"
        );

        return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(questionResult)
        );
    }
    
    public boolean isProjectSearchResultAbsent(String projectName) {
        By projectResult = By.xpath(
            "//span[@vtfieldname='projectname' " +
            "and @vtmodule='Project' " +
            "and normalize-space(.)='" + projectName + "']"
        );

        return wait.until(
            ExpectedConditions.invisibilityOfElementLocated(projectResult)
        );
    }
    
    public boolean areFAQFormFieldsDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(productLookup)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOf(faqStatus)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOf(faqQuestion)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOf(faqAnswer)).isDisplayed()
                && wait.until(ExpectedConditions.visibilityOf(saveFAQButton)).isDisplayed();
    }
    
    public boolean isFAQOptionDisplayedUnderMore() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();

        return wait.until(
                ExpectedConditions.visibilityOf(faqLink)
        ).isDisplayed();
    }
    

	public void saveFAQWithMandatoryFieldsBlank() {
	    wait.until(ExpectedConditions.elementToBeClickable(saveFAQButton)).click();
	}
	

	public String getFAQValidationAlertText() {
	    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	    String message = alert.getText();
	    alert.accept();
	    return message;
	}
	
	public boolean isFAQPageDisplayed() {
	    return wait.until(
	            ExpectedConditions.visibilityOfElementLocated(
	                    By.linkText("FAQ")
	            )
	    ).isDisplayed();
	}
	
	@FindBy(xpath = "//span[@class='lvtHeaderText' and text()='Creating New Faq']")
	private WebElement creatingNewFAQHeader;

	public boolean isCreatingNewFAQPageDisplayed() {
	    return wait.until(
	            ExpectedConditions.visibilityOf(creatingNewFAQHeader)
	    ).isDisplayed();
	}
    
    public VT_FAQ_Pom_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Navigation methods
    public void openFAQModule() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(faqLink)).click();
    }

    public void clickAddFAQ() {
        wait.until(ExpectedConditions.elementToBeClickable(addFAQButton)).click();
    }

    public void selectFAQStatus(String status) {
        wait.until(ExpectedConditions.visibilityOf(faqStatus));
        new Select(faqStatus).selectByVisibleText(status);
    }

    public void enterFAQQuestion(String question) {
        wait.until(ExpectedConditions.visibilityOf(faqQuestion));
        faqQuestion.clear();
        faqQuestion.sendKeys(question);
    }

    public void enterFAQAnswer(String answer) {
        wait.until(ExpectedConditions.visibilityOf(faqAnswer));
        faqAnswer.clear();
        faqAnswer.sendKeys(answer);
    }

    public void saveFAQ() {
        wait.until(ExpectedConditions.elementToBeClickable(saveFAQButton)).click();
    }
    
    public void createFAQFromExcel(
            String productName,
            String status,
            String question,
            String answer) {

        openFAQModule();
        clickAddFAQ();

        selectProduct(productName);
        selectFAQStatus(status);
        enterFAQQuestion(question);
        enterFAQAnswer(answer);

        saveFAQ();
    }
    
    public String[] readFAQDataFromExcel() throws Exception {

        VTExcelUtilities excel = new VTExcelUtilities();

        String productName =excel.readDataFromExcel("FAQ_Test_Data", 1, 0);
        String status =excel.readDataFromExcel("FAQ_Test_Data", 1, 1);
        String question =excel.readDataFromExcel("FAQ_Test_Data", 1, 2);
        String answer =excel.readDataFromExcel("FAQ_Test_Data", 1, 3);
        return new String[] {
                productName, status, question, answer
        };
    }

    public String createFAQFromExcel() throws Exception {

        String[] data = readFAQDataFromExcel();
        String question = data[2];

        openFAQModule();
        clickAddFAQ();

        selectProduct(data[0]);
        selectFAQStatus(data[1]);
        enterFAQQuestion(question);
        enterFAQAnswer(data[3]);

        saveFAQ();

        return question;
    }
    
    public void selectProduct(String productName) {

        String parentWindow = driver.getWindowHandle();
        wait.until(ExpectedConditions.elementToBeClickable(productLookup)).click();
        wait.until(driver -> driver.getWindowHandles().size() > 1);
        String popupWindow = null;
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parentWindow)) {
                popupWindow = window;
                break;
            }
        }

        if (popupWindow == null) {
            throw new RuntimeException("Product lookup popup did not open.");
        }
        driver.switchTo().window(popupWindow);
        try {
            By searchFieldLocator = By.xpath("//input[@type='text' and not(@name='search_url')]");
            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchFieldLocator));

            searchField.clear();
            searchField.sendKeys(productName);
            By searchButtonLocator = By.xpath("//*[self::input or self::button or self::a]" +"[@value='Search Now' or normalize-space(.)='Search Now']");
            List<WebElement> searchButtons =
                    driver.findElements(searchButtonLocator);

            if (!searchButtons.isEmpty()&& searchButtons.get(0).isDisplayed()&& searchButtons.get(0).isEnabled()) {
                searchButtons.get(0).click();

            } else {
                searchField.sendKeys(Keys.ENTER);
            }

            By productLinkLocator = By.xpath("//a[normalize-space()='" + productName + "']");
            wait.until(ExpectedConditions.elementToBeClickable(productLinkLocator)).click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to search for or select product: " + productName,e);

        } finally {
            if (driver.getWindowHandles().contains(parentWindow)) {
                driver.switchTo().window(parentWindow);
            }
        }
    }
    
    
    public boolean isFAQQuestionDisplayed(String question) {

        try {
            WebElement questionElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[normalize-space(text())='" + question + "']")));
            return questionElement.isDisplayed();

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
    
    
    public String createUniqueQuestion(String question, String scenario) {
        return question + " - " + scenario + " - " + System.currentTimeMillis();
    }
    
    public void searchFAQByQuestion(String question) {

        wait.until(ExpectedConditions.visibilityOf(searchFieldDropdown));

        new Select(searchFieldDropdown).selectByVisibleText("Question");

        wait.until(ExpectedConditions.visibilityOf(searchTextField));

        searchTextField.clear();
        searchTextField.sendKeys(question);

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        System.out.println("FAQ search completed.");
    }
    





	public void clickFilteredFAQQuestion() {
	    By questionLink = By.xpath(
	        "//span[@vtfieldname='question' and @vtmodule='Faq']" +
	        "/preceding-sibling::a[contains(@href,'module=Faq')" +
	        " and contains(@href,'action=DetailView')]"
	    );
	
	    WebElement link = wait.until(
	        ExpectedConditions.elementToBeClickable(questionLink)
	    );
	
	    new org.openqa.selenium.interactions.Actions(driver)
	        .moveToElement(link)
	        .click()
	        .perform();
	
	    System.out.println("Filtered FAQ question clicked successfully.");
	}



	public String getDisplayedQuestion() {
	    By questionLocator = By.xpath(
	        "//td[normalize-space()='Question']/following-sibling::td[1]"
	    );

	    return wait.until(
	        ExpectedConditions.visibilityOfElementLocated(questionLocator)
	    ).getText().trim();
	}

    
}