
package VTigerPomPages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import VTigerUtilities.VTExcelUtilities;

public class VT_Projects_Pom_Page {

    private WebDriver driver;
    private WebDriverWait wait;

    // Projects navigation locators
    @FindBy(xpath = "//a[normalize-space()='More']")
    private WebElement moreMenu;

    @FindBy(xpath = "//div[@id='allMenu']/descendant::a[text()='Projects']")
    private WebElement projectsLink;

    // Create Project locator
    @FindBy(xpath = "//img[@src='themes/softed/images/btnL3Add.gif']")
    private WebElement addProjectButton;

    // Project form fields - validate against the actual form
    @FindBy(name = "projectname")
    private WebElement projectNameField;

    @FindBy(name = "startdate")
    private WebElement startDateField;

    @FindBy(name = "targetenddate")
    private WebElement targetEndDateField;
    
    @FindBy(name = "actualenddate")
    private WebElement actualEndDateField;

    @FindBy(name = "projectstatus")
    private WebElement projectStatusDropdown;

    @FindBy(id = "linktoaccountscontacts_type")
    private WebElement relatedToDropdown;

    @FindBy(xpath = "(//td[@class='dvtCellInfo']/descendant::img)[4]")
    private WebElement contactLookupButton;

    @FindBy(name = "projectpriority")
    private WebElement priorityDropdown;

    @FindBy(name = "projecttype")
    private WebElement projectTypeDropdown;

    @FindBy(name = "progress")
    private WebElement progressField;

    @FindBy(xpath = "//input[@class='crmbutton small save']")
    private WebElement saveProjectButton;
    
    // TC2 - Project Search Locators
    @FindBy(id = "bas_searchfield")
    private WebElement projectSearchFieldDropdown;

    @FindBy(name = "search_text")
    private WebElement projectSearchTextField;

    @FindBy(name = "submit")
    private WebElement projectSearchButton;

    public VT_Projects_Pom_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Navigate to the Projects module
    public void openProjectsModule() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(projectsLink)).click();
    }

    // Open the New Project form
    public void clickAddProject() {
        wait.until(ExpectedConditions.elementToBeClickable(addProjectButton)).click();
    }

    public void enterProjectName(String projectName) {
        wait.until(ExpectedConditions.visibilityOf(projectNameField));
        projectNameField.clear();
        projectNameField.sendKeys(projectName);
    }

    public void enterStartDate(String startDate) {
        wait.until(ExpectedConditions.visibilityOf(startDateField));

        String formattedDate = LocalDate.parse(
                startDate,
                DateTimeFormatter.ofPattern("M/d/yy")
        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        startDateField.clear();
        startDateField.sendKeys(formattedDate);
    }

    public void enterTargetEndDate(String targetEndDate) {
        wait.until(ExpectedConditions.visibilityOf(targetEndDateField));

        String formattedDate = LocalDate.parse(
                targetEndDate,
                DateTimeFormatter.ofPattern("M/d/yy")
        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        targetEndDateField.clear();
        targetEndDateField.sendKeys(formattedDate);
    }
    
    public void enterActualEndDate(String actualEndDate) {
        wait.until(ExpectedConditions.visibilityOf(actualEndDateField));

        String formattedDate = LocalDate.parse(
                actualEndDate,
                DateTimeFormatter.ofPattern("M/d/yy")
        ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        actualEndDateField.clear();
        actualEndDateField.sendKeys(formattedDate);
    }

    public void selectProjectStatus(String status) {
        wait.until(ExpectedConditions.visibilityOf(projectStatusDropdown));
        new Select(projectStatusDropdown).selectByVisibleText(status);
    }

    public void selectRelatedTo(String relatedTo) {
        wait.until(ExpectedConditions.visibilityOf(relatedToDropdown));
        new Select(relatedToDropdown).selectByVisibleText(relatedTo);
    }

    public void clickContactLookupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(contactLookupButton)).click();
    }

    public void selectProjectPriority(String priority) {
        wait.until(ExpectedConditions.visibilityOf(priorityDropdown));
        new Select(priorityDropdown).selectByVisibleText(priority);
    }

    public void selectProjectType(String type) {
        wait.until(ExpectedConditions.visibilityOf(projectTypeDropdown));
        new Select(projectTypeDropdown).selectByVisibleText(type);
    }


	public void enterProjectProgress(String progress) {
	
	    wait.until(driver -> {
	        try {
	            WebElement dropdown = driver.findElement(
	                By.name("progress")
	            );
	
	            return dropdown.isDisplayed() && dropdown.isEnabled();
	        } catch (StaleElementReferenceException e) {
	            return false;
	        }
	    });
	
	    Select progressDropdown = new Select(
	        driver.findElement(By.name("progress"))
	    );
	
	    progressDropdown.selectByVisibleText(progress);
	}


    public void saveProject() {
        wait.until(ExpectedConditions.elementToBeClickable(saveProjectButton)).click();
    }
    
 // Navigate to the Projects list
    public void navigateToProjectsList() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(projectsLink)).click();

        wait.until(ExpectedConditions.urlContains("module=Project"));
    }

    // Search projects by project name
    public void searchProjectByName(String projectName) {
        wait.until(ExpectedConditions.visibilityOf(projectSearchFieldDropdown));

        new Select(projectSearchFieldDropdown)
                .selectByVisibleText("Project Name");

        wait.until(ExpectedConditions.visibilityOf(projectSearchTextField));

        projectSearchTextField.clear();
        projectSearchTextField.sendKeys(projectName);

        wait.until(ExpectedConditions.elementToBeClickable(projectSearchButton)).click();
    }


    // Verify that the project name is displayed
    @FindBy(xpath="//span[@id='dtlview_Project Name' and text()='Customer Portal Login Resolution']")
    private WebElement displayProject;
	   public String isProjectSearchResultDisplayed() {
	       return wait.until(
	           ExpectedConditions.visibilityOf(displayProject)
	       ).getText().trim();
	   }

    
    public void clickFilteredProduct() {
    	By ProjectLink = By.xpath(
    					"//a[contains(@href,'module=Project') " +
    	                "and contains(@href,'action=DetailView') " +
    	                "and contains(normalize-space(.),'Customer Portal Login Resolution')]"
    	    );
    	
    	 	wait.until(
                 ExpectedConditions.elementToBeClickable(ProjectLink)
         ).click();
    	
    	    System.out.println("Filtered Project Name question clicked successfully.");
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
    
 // Verify Task Name is absent from search results
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

    // Read TC1 test data from Excel
    public String[] readProjectDataFromExcel() throws Exception {
        VTExcelUtilities excel = new VTExcelUtilities();

        String projectName = excel.readDataFromExcel("Projects_Test_Data", 1, 0);
        String startDate = excel.readDataFromExcel("Projects_Test_Data", 1, 1);
        String targetEndDate = excel.readDataFromExcel("Projects_Test_Data", 1, 2);
        String status = excel.readDataFromExcel("Projects_Test_Data", 1, 3);
        String relatedTo = excel.readDataFromExcel("Projects_Test_Data", 1, 4);
        String contactName = excel.readDataFromExcel("Projects_Test_Data", 1, 5);
        String priority = excel.readDataFromExcel("Projects_Test_Data", 1, 6);
        String type = excel.readDataFromExcel("Projects_Test_Data", 1, 7);
        String progress = excel.readDataFromExcel("Projects_Test_Data", 1, 8);
        String actualEndDate = excel.readDataFromExcel("Projects_Test_Data", 1, 9);

        return new String[] {
            projectName, startDate, targetEndDate, status,
            relatedTo, contactName, priority, type, progress,
            actualEndDate
        };
    }

    // Complete the project creation flow
    public String createProjectFromExcel() throws Exception {
        String[] data = readProjectDataFromExcel();

        openProjectsModule();
        clickAddProject();

        enterProjectName(data[0]);
        enterStartDate(data[1]);
        enterTargetEndDate(data[2]);
        selectProjectStatus(data[3]);
        selectRelatedTo(data[4]);
        selectContact(data[5]);
        selectProjectPriority(data[6]);
        selectProjectType(data[7]);
        enterProjectProgress(data[8]);
        enterActualEndDate(data[9]);

        saveProject();

        return data[0];
    }
    

    public boolean isProjectDisplayed(String projectName) {
        try {
            WebElement projectElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='dtlview_Project Name' and normalize-space()='" + projectName + "']")));
            return projectElement.isDisplayed();

        } catch (org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
	
	public void selectContact(String contactName) {
	    String parentWindow = driver.getWindowHandle();

	    clickContactLookupButton();

	    wait.until(driver -> driver.getWindowHandles().size() > 1);

	    String popupWindow = null;

	    for (String window : driver.getWindowHandles()) {
	        if (!window.equals(parentWindow)) {
	            popupWindow = window;
	            break;
	        }
	    }

	    if (popupWindow == null) {
	        throw new RuntimeException("Contact lookup popup did not open.");
	    }

	    driver.switchTo().window(popupWindow);

	    try {
	        By searchFieldLocator =By.xpath("//input[@type='text' and not(@name='search_url')]");
	        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchFieldLocator));

	        searchField.clear();
	        searchField.sendKeys(contactName);
	        By searchButtonLocator = By.xpath("//*[self::input or self::button or self::a]" +"[@value='Search Now' or normalize-space(.)='Search Now']");
	        List<WebElement> searchButtons =driver.findElements(searchButtonLocator);
	        if (!searchButtons.isEmpty()&& searchButtons.get(0).isDisplayed()&& searchButtons.get(0).isEnabled()) {searchButtons.get(0).click();
	        } else {
	            searchField.sendKeys(Keys.ENTER);
	        }

	        By contactLinkLocator =By.xpath("//a[normalize-space()='" + contactName + "']");
	        wait.until(ExpectedConditions.elementToBeClickable(contactLinkLocator)).click();

	    } catch (TimeoutException e) {
	        throw new RuntimeException("Unable to search for or select contact: " + contactName, e);
	    } finally {
	        if (driver.getWindowHandles().contains(parentWindow)) {
	            driver.switchTo().window(parentWindow);
	        }
	    }
	}
	

	public void verifyProjectInContactRelatedList() throws Exception {
	
	    String[] data = readProjectDataFromExcel();
	    String projectName = data[0];
	    String contactName = data[5];
	
	    // Open Contacts
	    By contactsLink = By.xpath("//a[normalize-space()='Contacts']");
	    wait.until(ExpectedConditions.elementToBeClickable(contactsLink)).click();
	
	    // Find and click the contact
	    By contactLink = By.xpath("//a[normalize-space()='" + contactName +"' and contains(@href,'module=Contacts')]");
	
	    wait.until(driver -> driver.findElements(contactLink).stream().anyMatch(element -> element.isDisplayed() && element.isEnabled()));
	
	    WebElement visibleContact = driver.findElements(contactLink).stream().filter(element -> element.isDisplayed() && element.isEnabled()).findFirst().orElseThrow(() -> new NoSuchElementException(contactName));
	
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", visibleContact);visibleContact.click();
	
	    // Open More Information tab
	    By moreInformationTab = By.xpath("//a[normalize-space()='More Information']");
	    wait.until(ExpectedConditions.elementToBeClickable(moreInformationTab)).click();
	
	    // Expand the Projects related list
	    By projectsLink = By.xpath("//a[contains(@href,'tbl_Contacts_Projects') " +"and contains(@href,'Contacts_Projects')]");
	
	    WebElement projectsHeader = wait.until(ExpectedConditions.presenceOfElementLocated(projectsLink));
	
	    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", projectsHeader);
	    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", projectsHeader);
	
	    // Wait for the Projects related-list section
	    By relatedProjectsSection = By.xpath("//*[@id='tbl_Contacts_Projects']");
	    wait.until(ExpectedConditions.visibilityOfElementLocated(relatedProjectsSection));
	
	    // Verify the project in the contact's related list
	    By projectLink = By.xpath(
	        "//a[contains(@href,'module=Project')" +
	        " and contains(@href,'action=DetailView')" +
	        "and contains(normalize-space(.),'Customer Portal Login Resolution')]"
	    );
	
	    List<WebElement> projectLinks = wait.until(driver -> {
	        List<WebElement> links = driver.findElements(projectLink);
	        return links.stream().anyMatch(WebElement::isDisplayed)
	            ? links : null;
	    });
	
	    boolean isVerified = projectLinks.stream().anyMatch(
	        link -> link.isDisplayed()
	            && link.getText().trim().equals(projectName)
	    );
	
	    Assert.assertTrue(isVerified,"Project was not found in the Contact's related list: " + projectName);
	
	    System.out.println("Project verified in Contact's related list.");
	}
	

	public void updateProjectProgress(String newProgress) {

	    // Click Edit
	    By editButton = By.xpath("(//input[@name='Edit'])[1]");

	    wait.until(
	        ExpectedConditions.elementToBeClickable(editButton)
	    ).click();

	    // Update Progress using the existing method
	    enterProjectProgress(newProgress);

	    // Save changes using your existing Save button locator
	    wait.until(
	        ExpectedConditions.elementToBeClickable(saveProjectButton)
	    ).click();
	}

	
	public String getDisplayedProgress() {
	
	    By progressLocator =
	        By.xpath("//span[@id='dtlview_Progress']");
	
	    return wait.until(
	        ExpectedConditions.visibilityOfElementLocated(progressLocator)
	    ).getText().trim();
	}
	
	

	public String getProjectValidationPopupMessage() {
	    Alert alert = wait.until(
	        ExpectedConditions.alertIsPresent()
	    );
	
	    String message = alert.getText();
	    alert.accept();
	
	    return message;
	}
	
	
	public boolean isProjectsPageDisplayed() {
	    By projectsPageTitle = By.linkText("Projects");

	    return wait.until(ExpectedConditions.visibilityOfElementLocated(projectsPageTitle)).isDisplayed();
	}
	

	public boolean isCreatingNewProjectDisplayed() {
	
	    By createProjectTitle =By.xpath("//span[text()='Creating New Project']");
	
	    return wait.until(ExpectedConditions.visibilityOfElementLocated(createProjectTitle)).isDisplayed();
	}
	

	public void enterProjectDetailsWithoutSaving() throws Exception {
	
	    String[] data = readProjectDataFromExcel();
	
	    enterProjectName(data[0]);
	    enterStartDate(data[1]);
	    enterTargetEndDate(data[2]);
	    selectProjectStatus(data[3]);
	    selectRelatedTo(data[4]);
	    selectProjectPriority(data[6]);
	    selectProjectType(data[7]);
	    enterProjectProgress(data[8]);
	
	    System.out.println(
	        "Project details entered successfully without saving."
	    );
	}

	

	public boolean areProjectFieldsDisplayed() {
	
	    return wait.until(
	        ExpectedConditions.visibilityOf(projectNameField)
	    ).isDisplayed()
	        && startDateField.isDisplayed()
	        && targetEndDateField.isDisplayed()
	        && projectStatusDropdown.isDisplayed()
	        && relatedToDropdown.isDisplayed()
	        && priorityDropdown.isDisplayed()
	        && projectTypeDropdown.isDisplayed()
	        && progressField.isDisplayed();
	}
	
	



}
