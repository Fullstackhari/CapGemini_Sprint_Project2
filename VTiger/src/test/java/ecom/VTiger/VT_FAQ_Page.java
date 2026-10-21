package ecom.VTiger;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import VTigerBaseClass.VTBaseClass;
import VTigerPomPages.VT_FAQ_Pom_Page;

public class VT_FAQ_Page extends VTBaseClass {
	// TC-VT-FAQ-01: Verify FAQ creation using valid details from Excel.
	@Test(priority=1,groups = {"functional"})
	public void verifyFAQCreation() throws Exception {

	    VT_FAQ_Pom_Page faqPage = new VT_FAQ_Pom_Page(driver);
	    String question = faqPage.createFAQFromExcel();
	    Thread.sleep(1000);
	    Assert.assertTrue(faqPage.isFAQQuestionDisplayed(question),"FAQ creation failed. Question was not displayed: " + question);

	    Reporter.log("----------TC-VT-FAQ-01: FAQ created and verified successfully.----------",true);
	}
	 

	 // TC-VT-FAQ-03: Verify deletion of an existing FAQ record.
	@Test(priority=2,groups = {"functional"})
	public void verifyFAQDeletion() throws Exception {

	    VT_FAQ_Pom_Page faqPage = new VT_FAQ_Pom_Page(driver);

	    String[] data = faqPage.readFAQDataFromExcel();
	    String question = data[2];

	    // Navigate to FAQ and filter the record.
	    faqPage.navigateToFAQList();
	    faqPage.searchFAQByQuestion(question);
	    faqPage.clickFilteredFAQQuestion();

	    // Delete the selected FAQ record.s
	    faqPage.clickDeleteButton();
	    faqPage.confirmDeleteIfPrompted();

	    // Return to FAQ and search for the deleted record.
	    faqPage.navigateToFAQList();
	    faqPage.searchFAQByQuestion(question);
	    Thread.sleep(1000);
	    Assert.assertTrue(faqPage.isFAQSearchResultAbsent(question),"FAQ record was not deleted: " + question);
	    Reporter.log("----------TC-VT-FAQ-03 passed: FAQ deletion verified successfully.----------",true);
	}
	

	// TC-VT-FAQ-04: Verify FAQ creation form fields are visible.
	@Test(priority=3,groups = {"ui"})
	public void verifyFAQFormFieldsVisibility() throws InterruptedException {

	    VT_FAQ_Pom_Page faqPage = new VT_FAQ_Pom_Page(driver);

	    faqPage.openFAQModule();
	    faqPage.clickAddFAQ();
	    Thread.sleep(1000);
	    Assert.assertTrue(faqPage.areFAQFormFieldsDisplayed(),"One or more FAQ form fields are not visible.");
	    Reporter.log("----------TC-VT-FAQ-04 passed: FAQ form fields are visible.----------",true);
	}
	

	// TC-VT-FAQ-05: Verify FAQ option visibility under More.
	@Test(priority=4,groups = {"ui"})
	public void verifyFAQOptionVisibilityUnderMore() throws InterruptedException {

	    VT_FAQ_Pom_Page faqPage = new VT_FAQ_Pom_Page(driver);
	    Thread.sleep(1000);
	    Assert.assertTrue(faqPage.isFAQOptionDisplayedUnderMore(),"FAQ option is not visible under the More menu.");

	    Reporter.log("----------TC-VT-FAQ-05 passed: FAQ option is visible under More.----------",true);
	}
	
	

	// TC-VT-FAQ-06: Verify mandatory-field validation when creating an FAQ.
	@Test(priority=5,groups = {"functional"})
	public void verifyFAQMandatoryFieldValidation() throws InterruptedException {

	    VT_FAQ_Pom_Page faqPage = new VT_FAQ_Pom_Page(driver);

	    faqPage.openFAQModule();
	    faqPage.clickAddFAQ();
	    String[] data;
	    try {
	        data = faqPage.readFAQDataFromExcel();
	    } catch (Exception e) {
	        throw new RuntimeException("Unable to read FAQ test data.", e);
	    }
	    faqPage.selectProduct(data[0]);
	    faqPage.saveFAQWithMandatoryFieldsBlank();
	    String alertMessage = faqPage.getFAQValidationAlertText();
	    Thread.sleep(1000);
	    Assert.assertTrue(alertMessage.toLowerCase().contains("question")|| alertMessage.toLowerCase().contains("mandatory")|| alertMessage.toLowerCase().contains("required"),"Expected mandatory-field validation, but received: " + alertMessage);

	    Reporter.log("----------TC-VT-FAQ-06 passed: Mandatory-field validation displayed.----------",true);
	}
	

	// TC-VT-FAQ-08: Verify FAQ page navigation from the More menu.
	@Test(priority=6,groups = {"integration"})
	public void verifyFAQPageNavigation() throws InterruptedException {

	    VT_FAQ_Pom_Page faqPage = new VT_FAQ_Pom_Page(driver);

	    faqPage.openFAQModule();
	    Thread.sleep(1000);
	    Assert.assertTrue(faqPage.isFAQPageDisplayed(),"FAQ page was not displayed after clicking FAQ under More.");

	    Reporter.log("----------TC-VT-FAQ-08 passed: FAQ page navigation verified.----------",true);
	}
	

	// TC-VT-FAQ-09: Verify the + button opens the Creating New FAQ page.
	@Test(priority=7,groups = {"integration"})
	public void verifyAddButtonOpensCreatingNewFAQPage() throws InterruptedException {

	    VT_FAQ_Pom_Page faqPage = new VT_FAQ_Pom_Page(driver);

	    faqPage.openFAQModule();
	    faqPage.clickAddFAQ();
	    Thread.sleep(1000);
	    Assert.assertTrue(faqPage.isCreatingNewFAQPageDisplayed(),"Creating New Faq page was not displayed after clicking the + button.");

	    Reporter.log("----------TC-VT-FAQ-09 passed: + button opens the Creating New FAQ page.----------",true);
	}
	
	

	// TC-VT-FAQ-11: System test for FAQ creation, search, and details verification.
	@Test(priority=8,groups = {"system"})
	public void verifyFAQCreationSearchAndDetails() throws Exception {

	    VT_FAQ_Pom_Page faqPage = new VT_FAQ_Pom_Page(driver);

	    String question = faqPage.createFAQFromExcel();

	    faqPage.navigateToFAQList();
	    faqPage.searchFAQByQuestion(question);
	    faqPage.clickFilteredFAQQuestion();
	    Thread.sleep(1000);
	    Assert.assertEquals(faqPage.getDisplayedQuestion(),question,"FAQ details do not match the question that was created.");

	    Reporter.log("----------TC-VT-FAQ-11 passed: FAQ creation, search, and details verification completed.----------",true);
	}


}