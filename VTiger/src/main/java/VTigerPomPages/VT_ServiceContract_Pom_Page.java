
package VTigerPomPages;

import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

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

public class VT_ServiceContract_Pom_Page {

    private WebDriver driver;
    private WebDriverWait wait;

    public VT_ServiceContract_Pom_Page(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        PageFactory.initElements(driver, this);
    }

    // Navigation
    @FindBy(xpath = "//table[@class='hdrTabBg']/descendant::a[text()='More']")
    private WebElement moreMenu;

    @FindBy(xpath = "//div[@id='allMenu']/descendant::a[text()=' Support']")
    private WebElement supportMenu;

    @FindBy(xpath = "//div[@id='allMenu']/descendant::a[@href='index.php?module=ServiceContracts&action=index']")
    private WebElement serviceContractsLink;

    @FindBy(xpath = "//a[normalize-space()='Contacts']")
    private WebElement contactsLink;

    // Service Contracts list
    @FindBy(xpath = "//table[@class='small']/descendant::img[@src='themes/softed/images/btnL3Add.gif']")
    private WebElement addButton;

    @FindBy(xpath = "//div[@id='basicsearchcolumns_real']/descendant::select[@id='bas_searchfield']")
    private WebElement searchFieldDropdown;

    @FindBy(xpath = "//input[@name='search_text']")
    private WebElement searchTextField;

    @FindBy(xpath = "//input[@name='submit']")
    private WebElement searchButton;

    // Contract form fields
    @FindBy(id = "subject")
    private WebElement subjectField;

    @FindBy(xpath = "//img[@src='themes/softed/images/select.gif']")
    private WebElement contactNameAddBtnField;
    
    @FindBy(xpath="//select[@id='sc_related_to_type']")
    private WebElement RelatedDropdown;

    @FindBy(name = "contract_type")
    private WebElement contractTypeDropdown;

    @FindBy(name = "start_date")
    private WebElement startDateField;

    @FindBy(name = "due_date")
    private WebElement dueDateField;

    @FindBy(name = "contract_status")
    private WebElement contractStatusDropdown;

    @FindBy(name = "assigned_user_id")
    private WebElement assignedToDropdown;

    @FindBy(name = "tracking_unit")
    private WebElement trackingUnitDropdown;

    @FindBy(name = "total_units")
    private WebElement totalUnitsField;

    @FindBy(name = "used_units")
    private WebElement usedUnitsField;

    @FindBy(name = "contract_priority")
    private WebElement priorityDropdown;

    @FindBy(xpath = "//div[@align='center']/descendant::input[@class='crmbutton small save']")
    private WebElement saveButton;

    @FindBy(xpath = "(//div[@align='center']/descendant::input[@title='Cancel [Alt+X]'])[2]")
    private WebElement cancelButton;

    @FindBy(xpath = "(//input[@name='Edit' or @title='Edit'])[2]")
    private WebElement editButton;

    @FindBy(xpath = "(//input[@name='Delete' or @title='Delete'])[2]")
    private WebElement deleteButton;

    // Contact details
    @FindBy(xpath = "//a[contains(.,'More Information')]")
    private WebElement moreInformationTab;
    
    

    public WebDriver getDriver() {
        return driver;
    }

    // Navigation methods
    public void clickMoreMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu)).click();
    }

    public void clickSupportMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(moreMenu));

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@id='allMenu']")
        ));

        WebElement support = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.xpath("//div[@id='allMenu']//a[normalize-space()='Support']")
                )
        );

        support.click();
    }

    public void openServiceContracts() {
        wait.until(ExpectedConditions.elementToBeClickable(
                serviceContractsLink)).click();
    }

    public void openContacts() {
        wait.until(ExpectedConditions.elementToBeClickable(
                contactsLink)).click();
    }

    public void clickAddButton() {
        wait.until(ExpectedConditions.elementToBeClickable(addButton)).click();
    }

    public void navigateToServiceContracts() {
        clickMoreMenu();
        clickSupportMenu();
        openServiceContracts();
    }

    // Visibility methods
    public boolean isServiceContractsLinkDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(
                serviceContractsLink)).isDisplayed();
    }

    public boolean isAddButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(
                addButton)).isDisplayed();
    }

    public boolean isNewContractFormDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(
                subjectField)).isDisplayed()
                && saveButton.isDisplayed();
    }

    public boolean isSubjectFieldDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(
                subjectField)).isDisplayed();
    }

    public boolean isSaveButtonDisplayed() {
        return wait.until(ExpectedConditions.visibilityOf(
                saveButton)).isDisplayed();
    }

    // Contract form methods
    public void enterSubject(String subject) {
        WebElement element = wait.until(
                ExpectedConditions.visibilityOf(subjectField));
        element.clear();
        element.sendKeys(subject);
    }

    public void enterStartDate(String date) {
        startDateField.clear();
        startDateField.sendKeys(date);
    }

    public void enterDueDate(String date) {
        dueDateField.clear();
        dueDateField.sendKeys(date);
    }

    public void enterTotalUnits(String units) {
        totalUnitsField.clear();
        totalUnitsField.sendKeys(units);
    }

    public void enterUsedUnits(String units) {
        usedUnitsField.clear();
        usedUnitsField.sendKeys(units);
    }

    public void selectContractType(String type) {
        selectDropdownOption(contractTypeDropdown, type);
    }

    public void selectContractStatus(String status) {
        selectDropdownOption(contractStatusDropdown, status);
    }

    public void selectPriority(String priority) {
        selectDropdownOption(priorityDropdown, priority);
    }

    public void selectTrackingUnit(String unit) {
        selectDropdownOption(trackingUnitDropdown, unit);
    }

    private void selectDropdownOption(WebElement dropdown, String value) {
        new Select(wait.until(ExpectedConditions.elementToBeClickable(dropdown))).selectByVisibleText(value);
    }

    public void clickSaveButton() {
        wait.until(ExpectedConditions.elementToBeClickable(saveButton)).click();
    }

    public void clickCancelButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                cancelButton)).click();
    }


    public void clickContactLookupButton() {
    		wait.until(ExpectedConditions.elementToBeClickable(contactNameAddBtnField)).click();
    }
    
    @FindBy(xpath = "//td[normalize-space()='Subject']/following-sibling::td[1]")
    private WebElement displayedSubject;

    public String getDisplayedSubject() {
        return wait.until(
                ExpectedConditions.visibilityOf(displayedSubject)
        ).getText().trim();
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
	        By searchFieldLocator = By.xpath("//input[@type='text' and not(@name='search_url')]");
	        WebElement searchField = wait.until(ExpectedConditions.visibilityOfElementLocated(searchFieldLocator));
	
	        searchField.clear();
	        searchField.sendKeys(contactName);
	        By searchButtonLocator = By.xpath("//*[self::input or self::button or self::a]" +"[@value='Search Now' or normalize-space(.)='Search Now']");
	        List<WebElement> searchButtons =driver.findElements(searchButtonLocator);
	        if (!searchButtons.isEmpty()&& searchButtons.get(0).isDisplayed()&& searchButtons.get(0).isEnabled()) {searchButtons.get(0).click();
	        } else {
	            searchField.sendKeys(Keys.ENTER);
	        }

	        By contactLinkLocator = By.xpath("//a[normalize-space()='" + contactName + "']");
	        wait.until(ExpectedConditions.elementToBeClickable(contactLinkLocator)).click();
	    } catch (TimeoutException e) {
	        throw new RuntimeException("Unable to search for or select contact: " + contactName,e);
	    } finally {
	        if (driver.getWindowHandles().contains(parentWindow)) {
	            driver.switchTo().window(parentWindow);
	        }
	    }
    }





    // Search methods
	public void searchContractBySubject(String subject) {	
		wait.until(ExpectedConditions.visibilityOf(searchFieldDropdown));
	
	    new Select(searchFieldDropdown).selectByVisibleText("Subject");
	
	    wait.until(ExpectedConditions.visibilityOf(searchTextField));
	
	    searchTextField.clear();
	    searchTextField.sendKeys(subject);
	
	    wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
	    wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
	    try {
	        Thread.sleep(1500);
	    } catch (InterruptedException e) {
	        Thread.currentThread().interrupt();
	        throw new RuntimeException(e);
	    }
	
	    System.out.println("Service Contract search completed.");
	   
	}


	public void openContact(String subject) throws Exception {

	    String[] data = readServiceContractData();
	    String contactName = data[5].trim();

	    // Open Contacts
	    By contactsLink = By.xpath("//a[normalize-space()='Contacts']");
	    wait.until(ExpectedConditions.elementToBeClickable(contactsLink)).click();

	    // Search Contacts using Last Name
	    By searchDropdown = By.id("bas_searchfield");
	    WebElement dropdown = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(searchDropdown));

	    new Select(dropdown).selectByVisibleText("Last Name");

	    By searchInput = By.name("search_text");
	    WebElement searchBox = wait.until(
	            ExpectedConditions.visibilityOfElementLocated(searchInput));

	    searchBox.clear();
	    searchBox.sendKeys(contactName);

	    // Click Search Now
	    By searchButton = By.xpath(
	            "//input[@name='submit' or @value='Search Now']");

	    wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

	    // Find and click the matching Contact
	    By contactLink = By.xpath(
	            "//a[contains(@href,'module=Contacts')" +
	            " and contains(@href,'action=DetailView')" +
	            " and normalize-space(.)='" + contactName + "']");

	    WebElement visibleContact = wait.until(
	            ExpectedConditions.elementToBeClickable(contactLink));

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});", visibleContact);

	    visibleContact.click();

	    // Open More Information tab
	    By moreInformationTab = By.xpath(
	            "//a[normalize-space()='More Information']");

	    wait.until(ExpectedConditions.elementToBeClickable(moreInformationTab)).click();

	    // Expand the Service Contracts related list
	    By serviceContractsLink = By.xpath(
	            "//a[contains(@href,'tbl_Contacts_ServiceContracts') " +
	            "and contains(@href,'Contacts_ServiceContracts')]");

	    WebElement serviceContractsHeader = wait.until(
	            ExpectedConditions.presenceOfElementLocated(serviceContractsLink));

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].scrollIntoView({block:'center'});",
	            serviceContractsHeader);

	    ((JavascriptExecutor) driver).executeScript(
	            "arguments[0].click();", serviceContractsHeader);

	    // Wait until the related-list section is available
	    By relatedContractsSection = By.id("tbl_Contacts_ServiceContracts");

	    wait.until(ExpectedConditions.visibilityOfElementLocated(
	            relatedContractsSection));

	    // Verify the Service Contract in the Contact's related list
	    By contractLink = By.xpath(
	            "//a[contains(@href,'module=ServiceContracts')" +
	            " and contains(@href,'action=DetailView')" +
	            " and contains(normalize-space(.),'Customer Portal Support')]");

	    List<WebElement> contractLinks = wait.until(driver -> {
	        List<WebElement> links = driver.findElements(contractLink);
	        return links.stream().anyMatch(WebElement::isDisplayed) ? links : null;
	    });

	    boolean isVerified = contractLinks.stream().anyMatch(
	            link -> link.isDisplayed()
	                    && link.getText().contains("Customer Portal Support"));

	    Assert.assertTrue(
	            isVerified,
	            "Service Contract was not found in the Contact's related list.");

	    System.out.println(
	            "Service Contract verified in Contact's related list.");
	}


    // Edit and delete
    public void clickEditButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                editButton)).click();
    }

    public void clickDeleteButton() {
        wait.until(ExpectedConditions.elementToBeClickable(
                deleteButton)).click();
    }

    public void confirmDeleteIfPrompted() {
        try {
            Alert alert = wait.until(ExpectedConditions.alertIsPresent());
            alert.accept();
        } catch (NoAlertPresentException | org.openqa.selenium.TimeoutException e) {
            // No browser alert appeared.
        }
    }


	public boolean isRecordAbsentFromCurrentPage(String subject) {
	    String subjectPrefix = subject.length() > 25 ? subject.substring(0, 25): subject;
	
	    By matchingRows = By.xpath("//tr[td[2][contains(normalize-space(.),'"+ subjectPrefix + "')]]");
	    List<WebElement> rows = driver.findElements(matchingRows);
	    for (WebElement row : rows) {
	        if (row.isDisplayed()) {
	            return false;
	        }
	    }
	
	    return true;
	}
	
	
	    // Contact relationship verification
	    public void clickMoreInformationTab() {
	        wait.until(ExpectedConditions.elementToBeClickable(
	                moreInformationTab)).click();
	    }



	public void openServiceContractsRelatedList() {
	    By serviceContractsLocator = By.xpath(
	            "//table[@class='small lvt']" +
	            "/descendant::a[@href=\"javascript:hideRelatedListBlock('tbl_Contacts_ServiceContracts','Contacts_ServiceContracts');\"]");
	
	    WebElement serviceContractsLink = wait.until(ExpectedConditions.presenceOfElementLocated(serviceContractsLocator));
	
	    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",serviceContractsLink);
	
	    ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();",serviceContractsLink);
	}





	public boolean isRelatedContractListed(String subject) {
	    By contractLink = By.xpath(
	            "//div[@id='tbl_Contacts_ServiceContracts']" +
	            "//a[contains(@href,'module=ServiceContracts')" +
	            " and contains(@href,'action=DetailView')" +
	            " and contains(normalize-space(.),'" + subject + "')]");
	
	    List<WebElement> links = driver.findElements(contractLink);
	
	    for (WebElement link : links) {
	        if (link.isDisplayed()) {
	            return true;
	        }
	    }
	
	    return false;
	}


	
    // General helpers
    public boolean isElementDisplayed(By locator) {
        List<WebElement> elements = driver.findElements(locator);
        return !elements.isEmpty() && elements.get(0).isDisplayed();
    }

    public String getCurrentPageTitle() {
        return driver.getTitle();
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    

    public String convertDate(String date) {
        java.time.format.DateTimeFormatter inputFormat =java.time.format.DateTimeFormatter.ofPattern("M/d/yy");

        java.time.format.DateTimeFormatter outputFormat =java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd");

        java.time.LocalDate localDate =java.time.LocalDate.parse(date, inputFormat);

        return localDate.format(outputFormat);
    }

    public String createUniqueSubject(String subject, String scenario) {
    		return subject + " - " + scenario + " - "+ System.currentTimeMillis();
    }

    public void openServiceContractList() {
    		navigateToServiceContracts();
    }

    public void openNewServiceContractForm() {
    		openServiceContractList();
    		clickAddButton();

    		if (!isNewContractFormDisplayed()) {
    			throw new RuntimeException("New Service Contract form did not open.");
    		}
    }

    public void fillContractForm(
        String contractSubject,
        String contactName,
        String contractType,
        String startDate,
        String endDate,
        String contractStatus,
        String totalUnits,
        String unitsUsed,
        String contractPriority) {

	    	enterSubject(contractSubject);
	    selectContact(contactName);
	    selectContractType(contractType);
	    enterStartDate(convertDate(startDate));
	    enterDueDate(convertDate(endDate));
	    selectContractStatus(contractStatus);
	    enterTotalUnits(totalUnits);
	    enterUsedUnits(unitsUsed);;
	    selectPriority(contractPriority);
    }

    public void createContract(
        String contractSubject,
        String contactName,
        String contractType,
        String startDate,
        String endDate,
        String contractStatus,
        String totalUnits,
        String unitsUsed,
        String contractPriority) {

    		openNewServiceContractForm();

    		fillContractForm(
            contractSubject,
            contactName,
            contractType,
            startDate,
            endDate,
            contractStatus,
            totalUnits,
            unitsUsed,
            contractPriority);

    		clickSaveButton();
    }
    
    

    private static final String SHEET_NAME = "ServiceContract_Test Data";

    private String[] readServiceContractData() throws Exception {
    		VTExcelUtilities excel = new VTExcelUtilities();

    		String[] data = new String[9];

    		for (int column = 0; column < data.length; column++) {
    			data[column] = excel.readDataFromExcel(SHEET_NAME, 1, column).trim();
    		}

    		return data;
    }


    public String createContractFromExcel(String scenario) throws Exception {
    		String[] data = readServiceContractData();

    		String testSubject = createUniqueSubject(data[0], scenario);

    		createContract(
            testSubject,
            data[5],
            data[6],
            data[1],
            data[2],
            data[7],
            data[3],
            data[4],
            data[8]
    		);

    		return testSubject;
    }
    
    
    public String fillContractFormFromExcel(String scenario) throws Exception {
        String[] data = readServiceContractData();

        String testSubject = createUniqueSubject(data[0], scenario);

        openNewServiceContractForm();

        fillContractForm(
                testSubject,
                data[5],
                data[6],
                data[1],
                data[2],
                data[7],
                data[3],
                data[4],
                data[8]
        );

        return testSubject;
    }
    
    public String acceptAlertAndGetText() {
        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        String alertText = alert.getText();
        alert.accept();
        return alertText;
    }
    
    public void clickFilteredContractSubject(String subject) {
        By subjectLink = By.xpath(
                "//a[contains(@href,'module=ServiceContracts') " +
                "and contains(@href,'action=DetailView') " +
                "and contains(normalize-space(.),'Customer Portal Support Aggrement')]"
        );

        wait.until(
                ExpectedConditions.elementToBeClickable(subjectLink)
        ).click();
    }
    
    public String[] readServiceContractDataForVerification() throws Exception {
        return readServiceContractData();
    }
    
    public void searchContactByLastName() throws Exception {

        String[] data = readServiceContractData();
        String contactName = data[5].trim();

        // Open Contacts
        By contactsLink = By.xpath("//a[normalize-space()='Contacts']");
        wait.until(ExpectedConditions.elementToBeClickable(contactsLink)).click();

        // Search Contacts using Last Name
        By searchDropdown = By.id("bas_searchfield");
        WebElement dropdown = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchDropdown));

        new Select(dropdown).selectByVisibleText("Last Name");

        By searchInput = By.name("search_text");
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput));

        searchBox.clear();
        searchBox.sendKeys(contactName);

        // Click Search Now
        By searchButton = By.xpath(
                "//input[@name='submit' or @value='Search Now']");

        wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();

        System.out.println("Contacts filtered by Last Name: " + contactName);
    }
    
    public void clickFilteredContactAndVerifyServiceContract() throws Exception {

        String[] data = readServiceContractData();
        String contactName = data[5].trim();

        // Find and click the filtered Contact
        By contactLink = By.xpath(
                "//a[contains(@href,'module=Contacts')" +
                " and contains(@href,'action=DetailView')" +
                " and normalize-space(.)='" + contactName + "']");

        WebElement visibleContact = wait.until(driver ->
                driver.findElements(contactLink).stream()
                        .filter(element -> element.isDisplayed() && element.isEnabled())
                        .findFirst()
                        .orElse(null));

        if (visibleContact == null) {
            throw new NoSuchElementException(
                    "Filtered contact not found: " + contactName);
        }

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", visibleContact);

        wait.until(ExpectedConditions.elementToBeClickable(visibleContact)).click();

        // Open More Information tab
        By moreInformationTab = By.xpath(
                "//a[normalize-space()='More Information']");

        wait.until(ExpectedConditions.elementToBeClickable(moreInformationTab)).click();

        // Expand the Service Contracts related list
        By serviceContractsLink = By.xpath(
                "//a[contains(@href,'tbl_Contacts_ServiceContracts') " +
                "and contains(@href,'Contacts_ServiceContracts')]");

        WebElement serviceContractsHeader = wait.until(
                ExpectedConditions.presenceOfElementLocated(serviceContractsLink));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                serviceContractsHeader);

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].click();", serviceContractsHeader);

        // Wait for the related-list section
        By relatedContractsSection = By.id("tbl_Contacts_ServiceContracts");

        wait.until(ExpectedConditions.visibilityOfElementLocated(
                relatedContractsSection));

        // Verify the Service Contract
        By contractLink = By.xpath(
                "//a[contains(@href,'module=ServiceContracts')" +
                " and contains(@href,'action=DetailView')" +
                " and contains(normalize-space(.),'Customer Portal Support')]");

        List<WebElement> contractLinks = wait.until(driver -> {
            List<WebElement> links = driver.findElements(contractLink);
            return links.stream().anyMatch(WebElement::isDisplayed) ? links : null;
        });

        boolean isVerified = contractLinks.stream().anyMatch(
                link -> link.isDisplayed()
                        && link.getText().contains("Customer Portal Support"));

        Assert.assertTrue(
                isVerified,
                "Service Contract was not found in the Contact's related list.");

        System.out.println(
                "Service Contract verified in Contact's related list.");
    }


}
