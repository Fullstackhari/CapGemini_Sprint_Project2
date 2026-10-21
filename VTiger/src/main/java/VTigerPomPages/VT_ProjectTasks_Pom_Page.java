
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

public class VT_ProjectTasks_Pom_Page {

    private WebDriver driver;
    private WebDriverWait wait;

    // Project Tasks navigation
    @FindBy(xpath = "//a[normalize-space()='More']")
    private WebElement moreMenu;

    @FindBy(xpath = "//div[@id='allMenu']//a[normalize-space()='Project Tasks']")
    private WebElement projectTasksLink;

    // Add Project Task
    @FindBy(xpath = "//img[@src='themes/softed/images/btnL3Add.gif']")
    private WebElement addTaskButton;

    // Project Task form fields
    // Confirm these names against the actual form using Inspect.
    @FindBy(name = "projecttaskname")
    private WebElement taskNameField;

    @FindBy(name = "projecttaskpriority")
    private WebElement taskPriorityDropdown;

    @FindBy(name = "projecttaskprogress")
    private WebElement taskProgressDropdown;

    @FindBy(name = "startdate")
    private WebElement taskStartDateField;

    @FindBy(name = "projecttasktype")
    private WebElement taskTypeDropdown;

    @FindBy(name = "projecttaskhours")
    private WebElement workedHoursField;

    @FindBy(name = "enddate")
    private WebElement taskEndDateField;

    // Related Project lookup
    // Verify the input and lookup icon locators against the actual form.
    @FindBy(xpath = "(//td[@class='dvtCellInfo']/descendant::img)[1]")
    private WebElement relatedProjectLookupButton;

    // Save button
    @FindBy(xpath = "//input[@class='crmbutton small save']")
    private WebElement saveTaskButton;

    // Task search
    @FindBy(id = "bas_searchfield")
    private WebElement taskSearchFieldDropdown;

    @FindBy(name = "search_text")
    private WebElement taskSearchTextField;

    @FindBy(name = "submit")
    private WebElement taskSearchButton;

    public VT_ProjectTasks_Pom_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Read TC1 test data from Excel
    public String[] readProjectTaskDataFromExcel() throws Exception {
        VTExcelUtilities excel = new VTExcelUtilities();

        String taskName = excel.readDataFromExcel("Project_Tasks_Test_Data", 1, 0);
        String priority = excel.readDataFromExcel("Project_Tasks_Test_Data", 1, 1);
        String progress = excel.readDataFromExcel("Project_Tasks_Test_Data", 1, 2);
        String startDate = excel.readDataFromExcel("Project_Tasks_Test_Data", 1, 3);
        String type = excel.readDataFromExcel("Project_Tasks_Test_Data", 1, 4);
        String relatedProject = excel.readDataFromExcel("Project_Tasks_Test_Data", 1, 5);
        String workedHours = excel.readDataFromExcel("Project_Tasks_Test_Data", 1, 6);
        String endDate = excel.readDataFromExcel("Project_Tasks_Test_Data", 1, 7);
        return new String[] {
            taskName, priority, progress, startDate,
            type, relatedProject, workedHours, endDate
        };
    }

    // Navigate to Project Tasks
    public void navigateToProjectTasks() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
        wait.until(ExpectedConditions.elementToBeClickable(projectTasksLink)).click();
    }

    // Open the New Project Task form
    public void clickAddTask() {
        wait.until(ExpectedConditions.elementToBeClickable(addTaskButton)).click();
    }

    // Enter Task Name
    public void enterTaskName(String taskName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(taskNameField));
        field.clear();
        field.sendKeys(taskName);
    }

    // Select Priority
    public void selectTaskPriority(String priority) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(taskPriorityDropdown));
        new Select(dropdown).selectByVisibleText(priority);
    }

    // Select Progress
    public void selectTaskProgress(String progress) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(taskProgressDropdown));
        new Select(dropdown).selectByVisibleText(progress);
    }

    // Enter Start Date
    public void enterTaskStartDate(String startDate) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(taskStartDateField));
        field.clear();
        field.sendKeys(formatDate(startDate));
    }

    // Select Task Type
    public void selectTaskType(String type) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOf(taskTypeDropdown));
        new Select(dropdown).selectByVisibleText(type);
    }

    // Enter Worked Hours
    public void enterWorkedHours(String workedHours) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(workedHoursField));
        field.clear();
        field.sendKeys(workedHours);
    }

    // Enter End Date
    public void enterTaskEndDate(String endDate) {
        WebElement field = wait.until(ExpectedConditions.visibilityOf(taskEndDateField));
        field.clear();
        field.sendKeys(formatDate(endDate));
    }

    // Convert the Excel date into the date format used by the form
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
                return LocalDate.parse(date.trim(),DateTimeFormatter.ofPattern(pattern)).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            } catch (DateTimeParseException e) {
                // Try the next supported Excel date format.
            }
        }

        throw new IllegalArgumentException("Unsupported Project Task date format: " + date);
    }

    // Click the Related Project lookup button
    public void clickRelatedProjectLookupButton() {
        wait.until(ExpectedConditions.elementToBeClickable(relatedProjectLookupButton)).click();
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
            List<WebElement> searchButtons =
                driver.findElements(searchButtonLocator);

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

    // Save Project Task
    public void saveProjectTask() {
        wait.until(ExpectedConditions.elementToBeClickable(saveTaskButton)).click();
    }

    // Complete the Project Task creation flow
    public String createProjectTaskFromExcel() throws Exception {
        String[] data = readProjectTaskDataFromExcel();

        navigateToProjectTasks();
        clickAddTask();

        enterTaskName(data[0]);
        selectTaskPriority(data[1]);
        selectTaskProgress(data[2]);
        enterTaskStartDate(data[3]);
        selectTaskType(data[4]);
        selectRelatedProject(data[5]);
        enterWorkedHours(data[6]);
        enterTaskEndDate(data[7]);

        saveProjectTask();

        return data[0];
    }

    // Search Project Tasks by Task Name
    public void searchProjectTaskByName(String taskName) {
        wait.until(
            ExpectedConditions.visibilityOf(taskSearchFieldDropdown)
        );

        new Select(taskSearchFieldDropdown).selectByVisibleText("Task Name");

        WebElement searchField = wait.until(ExpectedConditions.visibilityOf(taskSearchTextField));

        searchField.clear();
        searchField.sendKeys(taskName);

        wait.until(ExpectedConditions.elementToBeClickable(taskSearchButton)).click();
    }

    // Verify Task Name on the detail page
    public boolean isProjectTaskDisplayed(String taskName) {
        By taskNameLocator = By.xpath("//span[@id='dtlview_Task Name' and normalize-space()='" +taskName + "']");

        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(taskNameLocator)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    @FindBy(xpath = "(//input[@name='Delete' or @title='Delete'])[1]")
    private WebElement deleteButton;

    public void clickDeleteButton() {
        wait.until(
            ExpectedConditions.elementToBeClickable(deleteButton)
        ).click();
    }
    
    // Verify Task Name is absent from search results
    public boolean isProjectTaskSearchResultAbsent(String taskName) {
        By taskResult = By.xpath("//span[@vtmodule='ProjectTask' " +"and normalize-space(.)='" + taskName + "']");
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(taskResult));
    }

    
    // Confirm deletion if a browser alert appears
    public void confirmDeleteIfPrompted() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();

        } catch (NoAlertPresentException | TimeoutException e) {
            // No browser alert appeared.
        }
    }
    
    
    public void verifyProjectTaskInProjectRelatedList() throws Exception {

        String[] data = readProjectTaskDataFromExcel();
        String projectName = data[5];

        // Open Projects
        By moreMenu = By.xpath("//a[normalize-space()='More']");
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();

        By projectsLink = By.xpath(
                "//div[@id='allMenu']//a[normalize-space()='Projects']");
        wait.until(ExpectedConditions.elementToBeClickable(projectsLink)).click();

        // Filter the project by name
        By searchDropdown = By.id("bas_searchfield");
        wait.until(ExpectedConditions.visibilityOfElementLocated(searchDropdown));

        Select projectFilter = new Select(driver.findElement(searchDropdown));
        projectFilter.selectByVisibleText("Project Name");

        By searchText = By.name("search_text");
        WebElement searchInput =
                wait.until(ExpectedConditions.visibilityOfElementLocated(searchText));

        searchInput.clear();
        searchInput.sendKeys(projectName);

        By searchButton = By.name("submit");
        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        // Find and click the filtered project
        By projectLink = By.xpath(
                "//a[contains(@href,'module=Project') " +
                "and normalize-space()='" + projectName + "']");

        WebElement visibleProject = wait.until(driver ->
                driver.findElements(projectLink).stream()
                        .filter(element -> element.isDisplayed() && element.isEnabled())
                        .findFirst()
                        .orElse(null));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", visibleProject);
        visibleProject.click();

        // Open More Information tab
        By moreInformationTab = By.xpath(
                "//a[normalize-space()='More Information']");
        wait.until(ExpectedConditions.elementToBeClickable(moreInformationTab)).click();

     // Expand the Project Tasks related list
        By projectTasksHeader = By.xpath("//a[contains(@href,\"tbl_Project_ProjectTasks\") " +"and contains(@href,\"Project_ProjectTasks\")]");

        WebElement projectTasks = wait.until(ExpectedConditions.presenceOfElementLocated(projectTasksHeader));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", projectTasks);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", projectTasks);

        // Wait for the Project Tasks related-list section
        By projectTasksSection = By.id("tbl_Project_ProjectTasks");

        wait.until(ExpectedConditions.visibilityOfElementLocated(projectTasksSection));
    }
    
    public boolean isProjectTaskInRelatedList(String taskName) {

        By taskNameLocator = By.xpath("//*[normalize-space(text())='" + taskName + "']");
        try {
            return wait.until(driver ->
                    driver.findElements(taskNameLocator).stream()
                            .anyMatch(WebElement::isDisplayed));
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    public boolean areProjectTaskFieldsDisplayed() {

        return wait.until(
                ExpectedConditions.visibilityOf(taskNameField)
        ).isDisplayed()
                && taskPriorityDropdown.isDisplayed()
                && taskProgressDropdown.isDisplayed()
                && taskStartDateField.isDisplayed()
                && taskTypeDropdown.isDisplayed()
                && relatedProjectLookupButton.isDisplayed()
                && workedHoursField.isDisplayed()
                && taskEndDateField.isDisplayed();
    }
    
    
    public void enterProjectTaskDetailsExceptTaskNameAndRelatedTo(String[] taskData) {
        new Select(taskPriorityDropdown).selectByVisibleText(taskData[1]);
        new Select(taskProgressDropdown).selectByVisibleText(taskData[2]);

        taskStartDateField.clear();
        taskStartDateField.sendKeys(taskData[3]);

        new Select(taskTypeDropdown).selectByVisibleText(taskData[4]);

        workedHoursField.clear();
        workedHoursField.sendKeys(taskData[6]);

        taskEndDateField.clear();
        taskEndDateField.sendKeys(taskData[7]);
    }
    
    public boolean verifyTaskNameEmptyPopup() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());

            String alertMessage = alert.getText();
            System.out.println("Popup message: " + alertMessage);

            boolean isExpectedMessage =alertMessage.equals("Task Name cannot be empty");
            alert.accept();
            return isExpectedMessage;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    // Click the filtered Project Task
    public void clickFilteredProjectTask(String taskName) {

        By projectTaskLink = By.xpath(
                "//a[contains(@href,'module=ProjectTask') " +
                "and contains(@href,'action=DetailView') " +
                "and normalize-space(.)='" + taskName + "']");

        WebElement filteredTask = wait.until(ExpectedConditions.elementToBeClickable(projectTaskLink));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", filteredTask);

        filteredTask.click();

        System.out.println("Filtered Project Task clicked successfully: " + taskName);
    }
    
    
    public void searchProjectTaskByPriority(String taskName) {
        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("search_text")));
        searchField.clear();
        searchField.sendKeys(taskName);

        WebElement searchFilter = wait.until(ExpectedConditions.elementToBeClickable(By.id("bas_searchfield")));
        new Select(searchFilter).selectByValue("projecttaskpriority");
        WebElement searchButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value=' Search Now ']")));
        searchButton.click();
    }
    
    public boolean isProjectTasksPageDisplayed() {
        return wait.until( ExpectedConditions.visibilityOfElementLocated(By.linkText("Project Tasks"))).isDisplayed();
    }
    
    public boolean isCreateProjectTaskPageDisplayed() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@class='lvtHeaderText' and text()='Creating New Project Task']"))).isDisplayed();
    }
    
    public void enterProjectTaskDetailsWithoutSaving() throws Exception {
        String[] taskData = readProjectTaskDataFromExcel();

        taskNameField.sendKeys(taskData[0]);

        new Select(taskPriorityDropdown).selectByVisibleText(taskData[1]);
        new Select(taskProgressDropdown).selectByVisibleText(taskData[2]);

        taskStartDateField.clear();
        taskStartDateField.sendKeys(taskData[3]);

        new Select(taskTypeDropdown).selectByVisibleText(taskData[4]);

        clickRelatedProjectLookupButton();
        selectRelatedProject(taskData[5]);

        workedHoursField.clear();
        workedHoursField.sendKeys(taskData[6]);

        taskEndDateField.clear();
        taskEndDateField.sendKeys(taskData[7]);
    }
    
    
}
