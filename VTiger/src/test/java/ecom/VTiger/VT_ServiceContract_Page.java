
package ecom.VTiger;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import VTigerBaseClass.VTBaseClass;
import VTigerPomPages.VT_ServiceContract_Pom_Page;

public class VT_ServiceContract_Page extends VTBaseClass {


	// TC-VT-SC-01: Verify creating and saving a Service Contract
	@Test(priority = 1, groups = {"functional"})
	public void verifyServiceContractCreationFormNavigation() throws Exception {
	    VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
	    String subject = serviceContractPage.createContractFromExcel("TC01");
	    String actualSubject = serviceContractPage.getDisplayedSubject();
	    Thread.sleep(1000);
	    Assert.assertEquals(actualSubject,subject,"Saved Service Contract name does not match.");
	    Reporter.log("----------TC-VT-SC-01: Service Contract created, saved, and verified successfully.----------",true);
	}


    
    // TC-VT-SC-02: Verify searching and viewing a Service Contract
    @Test(priority=2,groups = {"functional"})
    public void verifySearchAndViewServiceContract() throws Exception {
        VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
        String[] data = serviceContractPage.readServiceContractDataForVerification();
        String subject = data[0];
        serviceContractPage.openServiceContractList();
        serviceContractPage.searchContractBySubject(subject);
        serviceContractPage.clickFilteredContractSubject(subject);

        String actualSubject = serviceContractPage.getDisplayedSubject();
        Thread.sleep(1000);
        Assert.assertTrue(actualSubject.contains(subject),"Service Contract name does not match. Expected: "+ subject + ", Actual: " + actualSubject);
        Reporter.log("----------TC-VT-SC-02: Existing Service Contract searched, opened, and verified successfully.----------",true);
    }

    // TC-VT-SC-03: Verify deleting a newly created Service Contract.
    @Test(priority = 3)
    public void verifyEditAndDeleteServiceContract() throws Exception {

        VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
        String subject = serviceContractPage.createContractFromExcel("TC03");

        serviceContractPage.openServiceContractList();
        serviceContractPage.searchContractBySubject(subject);
        serviceContractPage.clickFilteredContractSubject(subject);
        serviceContractPage.clickDeleteButton();
        serviceContractPage.confirmDeleteIfPrompted();

        serviceContractPage.openServiceContractList();
        serviceContractPage.searchContractBySubject(subject);
        Thread.sleep(1000);
        Assert.assertTrue(serviceContractPage.isRecordAbsentFromCurrentPage(subject),"Service Contract is still displayed after deletion: " + subject);

        Reporter.log("----------TC-VT-SC-03 passed: Newly created Service Contract deleted and verified absent: "+ subject + "----------",true);
    }



    // TC-VT-SC-04: Verify Service Contract form fields visibility
    @Test(priority=4,groups = {"ui"})
    public void verifyServiceContractFormFieldsVisibility() throws InterruptedException {
        VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);

        serviceContractPage.openNewServiceContractForm();
        Assert.assertTrue(serviceContractPage.isSubjectFieldDisplayed(),"Subject field is not displayed.");
        Thread.sleep(1000);
        Assert.assertTrue(serviceContractPage.isSaveButtonDisplayed(),"Save button is not displayed.");
        Reporter.log("----------TC-VT-SC-04: Service Contract form field visibility verified.----------",true);
    }

    // TC-VT-SC-05: Verify Service Contracts menu visibility
    @Test(priority=5,groups = {"ui"})
    public void verifyServiceContractsMenuVisibility() throws InterruptedException {
        VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
        serviceContractPage.clickMoreMenu();
        serviceContractPage.clickSupportMenu();
        Thread.sleep(1000);
        Assert.assertTrue(serviceContractPage.isServiceContractsLinkDisplayed(),"Service Contracts link is not displayed under Support.");
        Reporter.log("----------TC-VT-SC-05: Service Contracts menu visibility verified.----------",true);
    }

    // TC-VT-SC-06: Verify validation when submitting an empty form
    @Test(priority=6,groups = {"negative"})
    public void verifyEmptyServiceContractFormValidation() {
        VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
        serviceContractPage.openNewServiceContractForm();
        serviceContractPage.clickSaveButton();
        String alertText = serviceContractPage.acceptAlertAndGetText();
        Assert.assertEquals(alertText, "Subject cannot be empty","Expected validation alert was not displayed.");
        Assert.assertTrue(serviceContractPage.isNewContractFormDisplayed(),"The form closed after submitting an empty form.");
        Reporter.log("----------TC-VT-SC-06: Empty Service Contract form validation verified.----------",true);
    }

    // TC-VT-SC-07: Verify search with a non matching subject
    @Test(priority=7,groups = {"negative"})
    public void verifyServiceContractSearchWithDifferentSubject()throws Exception {
        VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
        String subject =serviceContractPage.createContractFromExcel("SearchDifferent");
        serviceContractPage.openServiceContractList();
        String nonmatchingSubject = subject + "_NOT_FOUND";
        serviceContractPage.searchContractBySubject(nonmatchingSubject);
        Assert.assertTrue(serviceContractPage.isRecordAbsentFromCurrentPage(nonmatchingSubject),"A contract was unexpectedly found for a nonmatching subject.");
        Reporter.log("----------TC-VT-SC-07: Nonmatching Service Contract search verified.----------",  true);
    }

    // TC-VT-SC-08: Verify navigation to the Service Contracts list
    @Test(priority=8,groups = {"integration"})
    public void verifyServiceContractListNavigation() {
        VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
        serviceContractPage.openServiceContractList();
        Assert.assertTrue(serviceContractPage.isAddButtonDisplayed(),"Service Contracts list or Add button is not displayed.");
        Reporter.log("----------TC-VT-SC-08: Service Contract list navigation verified.----------",true);
    }

    // TC-VT-SC-09: Verify Add button opens the new Service Contract form
    @Test(priority=9,groups = {"integration"})
    public void verifyAddButtonOpensNewContractForm() {
        VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
        serviceContractPage.openServiceContractList();
        serviceContractPage.clickAddButton();
        Assert.assertTrue(serviceContractPage.isNewContractFormDisplayed(),"Add button did not open the new Service Contract form.");
        Reporter.log("----------TC-VT-SC-09: Add button navigation verified.----------",true);
    }

    // TC-VT-SC-10: Verify the existing Service Contract subject.
    @Test(priority=10,groups = {"smoke"})
    public void verifyBasicServiceContractCreation() throws Exception {

        VT_ServiceContract_Pom_Page serviceContractPage =
                new VT_ServiceContract_Pom_Page(driver);

        String[] data = serviceContractPage.readServiceContractDataForVerification();
        String subject = data[0];

        serviceContractPage.openServiceContractList();
        serviceContractPage.searchContractBySubject(subject);
        serviceContractPage.clickFilteredContractSubject(subject);

        Assert.assertTrue(serviceContractPage.getDisplayedSubject().startsWith(subject),"Existing Service Contract subject does not match.");        
        Reporter.log("----------TC-VT-SC-10: Existing Service Contract subject verified.----------",true);
    }

    
    // TC-VT-SC-11: Verify end-to-end Service Contract creation and details
	@Test(priority=11,groups = {"system"})
	public void verifyServiceContractEndToEnd() throws Exception {
		VT_ServiceContract_Pom_Page serviceContractPage =new VT_ServiceContract_Pom_Page(driver);
	
	    String subject =serviceContractPage.createContractFromExcel("BasicCreation");
	
	    serviceContractPage.openServiceContractList();
	    serviceContractPage.searchContractBySubject(subject);
	    serviceContractPage.clickFilteredContractSubject(subject);
	
	    Assert.assertEquals(serviceContractPage.getDisplayedSubject(),subject,"Service Contract subject does not match.");
	
	    serviceContractPage.openContact(subject);
	    
	    Reporter.log("----------TC-VT-SC-11: End-to-end Service Contract workflow verified.----------",true);
	}

	
	
	// TC-VT-SC-12: Verify the existing Service Contract in the related Contact.
	@Test(priority = 12, groups = {"functional"})
	public void verifyServiceContractInRelatedContact() throws Exception {

	    VT_ServiceContract_Pom_Page serviceContractPage =
	            new VT_ServiceContract_Pom_Page(driver);

	    String[] data = serviceContractPage.readServiceContractDataForVerification();
	    String subject = data[0];

	    serviceContractPage.openContact(subject);

	    Assert.assertTrue(serviceContractPage.isRelatedContractListed(subject),"Service Contract was not found in the related Contact.");
	    Reporter.log("----------TC-VT-SC-12: Existing Service Contract verified in the related Contact.----------",true);
	}
}
