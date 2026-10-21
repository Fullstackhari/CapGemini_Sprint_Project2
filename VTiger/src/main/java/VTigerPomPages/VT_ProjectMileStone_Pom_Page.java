
package VTigerPomPages;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import VTigerUtilities.VTExcelUtilities;

public class VT_ProjectMileStone_Pom_Page {

    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//a[normalize-space()='More']")
    private WebElement moreMenu;

    @FindBy(xpath = "//div[@id='allMenu']/descendant::a[text()='Projects']")
    private WebElement projectsLink;

    @FindBy(id = "bas_searchfield")
    private WebElement projectSearchFieldDropdown;

    @FindBy(name = "search_text")
    private WebElement projectSearchTextField;

    @FindBy(name = "submit")
    private WebElement projectSearchButton;

    @FindBy(name = "projectstatus")
    private WebElement projectStatusDropdown;

    @FindBy(name = "projectpriority")
    private WebElement priorityDropdown;

    @FindBy(name = "progress")
    private WebElement progressDropdown;

    @FindBy(xpath = "//input[@class='crmbutton small save']")
    private WebElement saveProjectButton;
    
    @FindBy(xpath = "//a[normalize-space()='Project Tasks']")
    private WebElement projectTasksLink;
    
    @FindBy(name="projecttaskpriority")
    private WebElement taskpriority;
    
    @FindBy(name="projecttaskprogress")
    private WebElement taskprogress;
    
    @FindBy(xpath = "//a[normalize-space()='Project Milestones']")
    private WebElement projectMilestonesLink;

    @FindBy(xpath = "//img[@src='themes/softed/images/btnL3Add.gif']")
    private WebElement addMilestoneButton;

    @FindBy(name = "projectmilestonename")
    private WebElement milestoneNameField;

    @FindBy(xpath = "//img[@src='themes/softed/images/select.gif']")
    private WebElement relateLookupButton;

    @FindBy(name = "projectmilestonedate")
    private WebElement milestoneDateField;

    @FindBy(name = "projectmilestonetype")
    private WebElement milestoneTypeField;

    @FindBy(name = "description")
    private WebElement milestoneDescriptionField;

    @FindBy(xpath = "(//input[@type='submit'])[2]")
    private WebElement saveMilestoneButton;
    public VT_ProjectMileStone_Pom_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    public String[] readProjectMilestoneDataFromExcel() throws Exception {
        VTExcelUtilities excel = new VTExcelUtilities();
        String projectName = excel.readDataFromExcel("ProjectMilestone_Test_Data", 1, 0);
        String projectStatus = excel.readDataFromExcel("ProjectMilestone_Test_Data", 1, 1);
        String projectPriority = excel.readDataFromExcel("ProjectMilestone_Test_Data", 1, 2);
        String projectProgress = excel.readDataFromExcel("ProjectMilestone_Test_Data", 1, 3);

        return new String[] {
                projectName,
                projectStatus,
                projectPriority,
                projectProgress
        };
    }

    public void navigateToProjects() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(projectsLink)).click();
        wait.until(ExpectedConditions.urlContains("module=Project"));
    }

    public void searchProjectByName(String projectName) {
        wait.until(ExpectedConditions.visibilityOf(projectSearchFieldDropdown));
        new Select(projectSearchFieldDropdown).selectByVisibleText("Project Name");
        wait.until(ExpectedConditions.visibilityOf(projectSearchTextField));
        projectSearchTextField.clear();
        projectSearchTextField.sendKeys(projectName);
        wait.until(ExpectedConditions.elementToBeClickable(projectSearchButton)).click();
    }

    public void clickFilteredProject(String projectName) {
        By projectLink = By.xpath(
                "//a[contains(@href,'module=Project') " +
                "and contains(@href,'action=DetailView') " +
                "and normalize-space(.)='" + projectName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(projectLink)).click();
    }

    public void clickEditProject() {
        By editButton = By.xpath("(//input[@name='Edit'])[1]");
        wait.until(ExpectedConditions.elementToBeClickable(editButton)).click();
    }


public void updateProjectDetails(String status, String priority, String progress) {

    By statusLocator = By.name("projectstatus");
    By priorityLocator = By.name("projectpriority");
    By progressLocator = By.name("progress");

    for (int attempt = 1; attempt <= 3; attempt++) {
        try {
            WebElement statusElement = wait.until(
                    ExpectedConditions.refreshed(
                            ExpectedConditions.visibilityOfElementLocated(statusLocator)));

            new Select(statusElement).selectByVisibleText(status);

            WebElement priorityElement = wait.until(
                    ExpectedConditions.refreshed(
                            ExpectedConditions.visibilityOfElementLocated(priorityLocator)));

            new Select(priorityElement).selectByVisibleText(priority);

            WebElement progressElement = wait.until(
                    ExpectedConditions.refreshed(
                            ExpectedConditions.visibilityOfElementLocated(progressLocator)));

            new Select(progressElement).selectByVisibleText(progress);

            return;

        } catch (StaleElementReferenceException e) {
            if (attempt == 3) {
                throw e;
            }
        }
    }
}


    public void saveProjectChanges() {
        wait.until(ExpectedConditions.elementToBeClickable(saveProjectButton)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("dtlview_Progress")));
    }

    public String getDisplayedProgress() {
        By progressLocator = By.id("dtlview_Progress");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(progressLocator)).getText().trim();
    }
    
    public void updateProjectDetailsFromExcel() throws Exception {
        String[] data = readProjectMilestoneDataFromExcel();

        String projectName = data[0];
        String projectStatus = data[1];
        String projectPriority = data[2];
        String projectProgress = data[3];

        navigateToProjects();
        searchProjectByName(projectName);
        clickFilteredProject(projectName);
        clickEditProject();
        updateProjectDetails(projectStatus,projectPriority,projectProgress);
    }
    
    public String[] readProjectTaskUpdateDataFromExcel() throws Exception {

        VTExcelUtilities excel = new VTExcelUtilities();
        String taskName = excel.readDataFromExcel("ProjectMilestone_Test_Data", 1, 4);
        String priority = excel.readDataFromExcel("ProjectMilestone_Test_Data", 1, 5);
        String progress = excel.readDataFromExcel("ProjectMilestone_Test_Data", 1, 6);
        return new String[] {taskName, priority, progress};
    }
    
    public void searchProjectTaskByName(String taskName) {

        wait.until(ExpectedConditions.visibilityOf(projectSearchFieldDropdown));
        new Select(projectSearchFieldDropdown).selectByVisibleText("Task Name");
        wait.until(ExpectedConditions.visibilityOf(projectSearchTextField));
        projectSearchTextField.clear();
        projectSearchTextField.sendKeys(taskName);
        wait.until(ExpectedConditions.elementToBeClickable(projectSearchButton)).click();
    }
    
    public void clickFilteredProjectTask(String taskName) {

        By taskLink = By.xpath(
                "//a[contains(@href,'module=ProjectTask') " +
                "and contains(@href,'action=DetailView') " +
                "and normalize-space(.)='" + taskName + "']");
        wait.until(ExpectedConditions.elementToBeClickable(taskLink)).click();
    }
    

	public void updateProjectTaskPriorityAndProgress(String priority, String progress) {
	
	    By priorityLocator = By.name("projecttaskpriority");
	    By progressLocator = By.name("projecttaskprogress");
	
	    for (int attempt = 1; attempt <= 3; attempt++) {
	        try {
	            WebElement priorityElement = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(priorityLocator)));
	            new Select(priorityElement).selectByVisibleText(priority);
	            WebElement progressElement = wait.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(progressLocator)));
	            new Select(progressElement).selectByVisibleText(progress);
	            return;
	        } catch (StaleElementReferenceException e) {
	            if (attempt == 3) {
	                throw e;
	            }
	        }
	    }
	}

    
    public void navigateToProjectTasks() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(projectTasksLink)).click();
    }
    public void updateProjectTaskPriorityAndProgressFromExcel()
            throws Exception {

        String[] data = readProjectTaskUpdateDataFromExcel();

        String taskName = data[0];
        String priority = data[1];
        String progress = data[2];

        navigateToProjectTasks();
        searchProjectTaskByName(taskName);
        clickFilteredProjectTask(taskName);
        clickEditProject();

        updateProjectTaskPriorityAndProgress(priority, progress);
    }
    
    public String readExpectedProjectTaskProgressFromExcel()
            throws Exception {

        return new VTExcelUtilities().readDataFromExcel("ProjectMilestone_Test_Data", 1, 6);
    }
    public String[] readMilestoneDataFromExcel() throws Exception {

        VTExcelUtilities excel = new VTExcelUtilities();

        String milestoneName = excel.readDataFromExcel(
                "ProjectMilestone_Test_Data", 1, 7);
        String relatedTo = excel.readDataFromExcel(
                "ProjectMilestone_Test_Data", 1, 8);
        String milestoneDate = excel.readDataFromExcel(
                "ProjectMilestone_Test_Data", 1, 9);
        String milestoneType = excel.readDataFromExcel(
                "ProjectMilestone_Test_Data", 1, 10);
        String description = excel.readDataFromExcel(
                "ProjectMilestone_Test_Data", 1, 11);

        return new String[] {
                milestoneName, relatedTo, milestoneDate,
                milestoneType, description
        };
    }
    
    public void navigateToProjectMilestones() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(projectMilestonesLink)).click();
    }
    
    public void clickAddMilestone() {
        wait.until(ExpectedConditions.elementToBeClickable(addMilestoneButton)).click();
    }
    
    public void clickRelatedProjectLookupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(relateLookupButton)).click();
    }
    
 // Select Related Project through the lookup popup
    public void selectRelatedProject(String projectName) {
        String parentWindow = driver.getWindowHandle();

        clickRelatedProjectLookupButton();

        wait.until(driver -> driver.getWindowHandles().size() > 1);
        String popupWindow = null;
        for (String window : driver.getWindowHandles()) {
            if (!window.equals(parentWindow)) {
                popupWindow = window;
                break;
            }
        }

        if (popupWindow == null) {
            throw new RuntimeException("Related Project lookup popup did not open.");
        }

        driver.switchTo().window(popupWindow);

        try {
            By searchFieldLocator = By.xpath("//input[@type='text' and not(@name='search_url')]");

            WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchFieldLocator));
            searchField.clear();
            searchField.sendKeys(projectName);

            By searchButtonLocator = By.xpath("//*[self::input or self::button or self::a]" +"[@value='Search Now' or normalize-space(.)='Search Now']");
            List<WebElement> searchButtons =driver.findElements(searchButtonLocator);

            if (!searchButtons.isEmpty()&& searchButtons.get(0).isDisplayed()&& searchButtons.get(0).isEnabled()) 
            {
            	searchButtons.get(0).click();
            } else {
                searchField.sendKeys(Keys.ENTER);
            }

            By projectLinkLocator = By.xpath("//a[normalize-space()='" + projectName + "']");
            wait.until(ExpectedConditions.elementToBeClickable(projectLinkLocator)).click();

        } catch (TimeoutException e) {
            throw new RuntimeException("Unable to search for or select related Project: "+ projectName,e);
        } finally {
            if (driver.getWindowHandles().contains(parentWindow)) {
                driver.switchTo().window(parentWindow);
            }
        }
    }
    

	private String formatDate(String date) {
	    String[] patterns = {
	        "M/d/yy",
	        "MM/dd/yy",
	        "dd-MM-yyyy",
	        "d-M-yyyy",
	        "yyyy-MM-dd"
	    };
	
	    for (String pattern : patterns) {
	        try {
	            return LocalDate.parse(
	                    date.trim(),
	                    DateTimeFormatter.ofPattern(pattern)
	            ).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
	        } catch (DateTimeParseException e) {
	            // Try the next supported Excel date format.
	        }
	    }

    throw new IllegalArgumentException("Unsupported date format: " + date);
}

    
    public void enterMilestoneDetailsFromExcel() throws Exception {

        String[] data = readMilestoneDataFromExcel();

        wait.until(ExpectedConditions.visibilityOf(milestoneNameField)).sendKeys(data[0]);
        selectRelatedProject(data[1]);
        String formattedDate = formatDate(data[2]);
        wait.until(ExpectedConditions.visibilityOf(milestoneDateField)).clear();
        milestoneDateField.sendKeys(formattedDate);
        new Select(milestoneTypeField).selectByVisibleText(data[3]);
        wait.until(ExpectedConditions.visibilityOf(milestoneDescriptionField)).sendKeys(data[4]);
    }
    
    public void saveMilestone() {
        wait.until(ExpectedConditions.elementToBeClickable(saveMilestoneButton)).click();
    }
    
    public String getDisplayedMilestoneName() {

        By milestoneNameLocator = By.id("dtlview_Milestone Name");

        return wait.until(ExpectedConditions.visibilityOfElementLocated(milestoneNameLocator)).getText().trim();
    }
    
    public void navigateToProjectMilestoneRelatedList() throws Exception {

        String[] data = readMilestoneDataFromExcel();
        String projectName = data[1];

        By moreMenuLocator = By.xpath("//a[normalize-space()='More']");
        wait.until(ExpectedConditions.elementToBeClickable(moreMenuLocator)).click();

        By projectsLinkLocator = By.xpath(
                "//div[@id='allMenu']//a[normalize-space()='Projects']");
        wait.until(ExpectedConditions.elementToBeClickable(projectsLinkLocator)).click();

        By searchDropdownLocator = By.id("bas_searchfield");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchDropdownLocator));

        Select projectFilter = new Select(driver.findElement(searchDropdownLocator));
        projectFilter.selectByVisibleText("Project Name");

        By searchTextLocator = By.name("search_text");
        WebElement searchInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(searchTextLocator));

        searchInput.clear();
        searchInput.sendKeys(projectName);

        By searchButtonLocator = By.name("submit");
        wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator)).click();

        By projectLinkLocator = By.xpath(
                "//a[contains(@href,'module=Project') and normalize-space()='"
                        + projectName + "']");

        WebElement visibleProject = wait.until(driver ->
                driver.findElements(projectLinkLocator).stream()
                        .filter(element -> element.isDisplayed() && element.isEnabled())
                        .findFirst()
                        .orElse(null));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", visibleProject);
        visibleProject.click();

        By moreInformationTabLocator = By.xpath(
                "//a[normalize-space()='More Information']");
        wait.until(ExpectedConditions.elementToBeClickable(
                moreInformationTabLocator)).click();

        By milestoneHeaderLocator = By.xpath(
                "//a[contains(@href,'tbl_Project_ProjectMilestones') "
                        + "and contains(@href,'Project_ProjectMilestones')]");

        WebElement milestoneHeader = wait.until(
                ExpectedConditions.presenceOfElementLocated(milestoneHeaderLocator));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", milestoneHeader);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", milestoneHeader);

        By milestoneSectionLocator = By.id("tbl_Project_ProjectMilestones");
        wait.until(ExpectedConditions.visibilityOfElementLocated(
                milestoneSectionLocator));
    }
    


	public boolean isMilestoneInProjectRelatedList(String milestoneName) {
	
	    By milestoneNameLocator = By.xpath(
	            "//a[normalize-space(.)='" + milestoneName + "' "
	                    + "and contains(@href,'module=ProjectMilestone')]");
	
	    try {
	        return wait.until(driver ->
	                driver.findElements(milestoneNameLocator).stream()
	                        .anyMatch(element -> element.isDisplayed()));
	    } catch (TimeoutException e) {
	        return false;
	    }
	}
	

	public void searchProjectMilestoneByName(String milestoneName) {
	
	    wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
	    wait.until(ExpectedConditions.elementToBeClickable(projectMilestonesLink)).click();
	
	    wait.until(ExpectedConditions.visibilityOf(projectSearchFieldDropdown));
	    new Select(projectSearchFieldDropdown).selectByVisibleText("Milestone Name");
	
	    wait.until(ExpectedConditions.visibilityOf(projectSearchTextField));
	    projectSearchTextField.clear();
	    projectSearchTextField.sendKeys(milestoneName);
	
	    wait.until(ExpectedConditions.elementToBeClickable(projectSearchButton)).click();
	}

	


	public void clickFilteredMilestone(String milestoneName) {
	
	    By milestoneLocator = By.xpath(
	            "//a[contains(@href,'module=ProjectMilestone') "
	            + "and contains(@href,'action=DetailView') "
	            + "and normalize-space(.)='" + milestoneName + "']");
	
	    List<WebElement> matches = driver.findElements(milestoneLocator);
	
	    System.out.println("Milestone search result count: " + matches.size());
	
	    for (WebElement element : matches) {
	        System.out.println("Milestone link displayed: " + element.isDisplayed());
	        System.out.println("Milestone link href: " + element.getAttribute("href"));
	    }
	
	    WebElement milestone = wait.until(driver ->driver.findElements(milestoneLocator).stream().filter(element -> element.isDisplayed() && element.isEnabled()).findFirst().orElse(null));
	    milestone.click();
	}


	
	@FindBy(xpath = "(//input[@name='Delete' or @title='Delete'])[1]")
	private WebElement deleteButton;

	public void clickDeleteButton() {
	    wait.until(ExpectedConditions.elementToBeClickable(deleteButton)).click();
	}
	
	public void confirmDeleteIfPrompted() {
	    try {
	        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	        alert.accept();
	    } catch (NoAlertPresentException | TimeoutException e) {
	        // No browser alert appeared.
	    }
	}
	
	public boolean isProjectMilestoneSearchResultAbsent(String milestoneName) {

	    By milestoneLocator = By.xpath(
	            "//a[contains(@href,'module=ProjectMilestone') " +"and contains(@href,'action=DetailView') " 
	                    + "and normalize-space(.)='" + milestoneName + "']");

	    return wait.until(
	            ExpectedConditions.invisibilityOfElementLocated(milestoneLocator));
	}
	
	public boolean areAllMilestoneFieldsDisplayed() {

	    return wait.until(ExpectedConditions.visibilityOf(milestoneNameField)).isDisplayed()
	            && wait.until(ExpectedConditions.visibilityOf(relateLookupButton)).isDisplayed()
	            && wait.until(ExpectedConditions.visibilityOf(milestoneDateField)).isDisplayed()
	            && wait.until(ExpectedConditions.visibilityOf(milestoneTypeField)).isDisplayed()
	            && wait.until(ExpectedConditions.visibilityOf(milestoneDescriptionField)).isDisplayed()
	            && wait.until(ExpectedConditions.visibilityOf(saveMilestoneButton)).isDisplayed();
	}
	

	public void enterMilestoneDetailsWithoutName() throws Exception {
	
	    String[] data = readMilestoneDataFromExcel();
	    wait.until(ExpectedConditions.visibilityOf(milestoneDateField)).clear();
	    milestoneDateField.sendKeys(formatDate(data[2]));
	    new Select(milestoneTypeField).selectByVisibleText(data[3]);
	    wait.until(ExpectedConditions.visibilityOf(milestoneDescriptionField)).sendKeys(data[4]);
	}
	
	public void clickSaveMilestone() {
	    wait.until(ExpectedConditions.elementToBeClickable(saveMilestoneButton)).click();
	}
	
	public String getMilestoneValidationMessage() {
	    Alert alert = wait.until(ExpectedConditions.alertIsPresent());
	    return alert.getText();
	}
	
	public void acceptMilestoneValidationAlert() {
	    wait.until(ExpectedConditions.alertIsPresent()).accept();
	}
	
	
	public boolean isCreateMilestoneHeadingDisplayed() {

	    By headingLocator = By.xpath("//span[@class='lvtHeaderText' and text()='Creating New Project Milestone']");
	    return wait.until(ExpectedConditions.visibilityOfElementLocated(headingLocator)).isDisplayed();
	}
	
	public void enterAllMilestoneDetailsWithoutSaving() throws Exception {

	    String[] data = readMilestoneDataFromExcel();

	    wait.until(ExpectedConditions.visibilityOf(milestoneNameField)).sendKeys(data[0]);
	    selectRelatedProject(data[1]);
	    wait.until(ExpectedConditions.visibilityOf(milestoneDateField)).clear();
	    milestoneDateField.sendKeys(formatDate(data[2]));
	    new Select(milestoneTypeField).selectByVisibleText(data[3]);
	    wait.until(ExpectedConditions.visibilityOf(milestoneDescriptionField)).sendKeys(data[4]);
	}
    
    
}
