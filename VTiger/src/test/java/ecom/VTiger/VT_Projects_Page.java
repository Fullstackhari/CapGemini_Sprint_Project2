package ecom.VTiger;


import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import VTigerBaseClass.VTBaseClass;
import VTigerPomPages.VT_Projects_Pom_Page;

public class VT_Projects_Page extends VTBaseClass {

    // TC-VT-PROJ-01: Create a project using valid details from Excel.
    @Test(priority = 1,groups = {"functional"})
    public void verifyProjectCreation() throws Exception {

        VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);
        String projectName = projectPage.createProjectFromExcel();
        Thread.sleep(1000);
        Assert.assertTrue(projectPage.isProjectDisplayed(projectName),"Project creation failed. Project was not displayed: " + projectName);
        Reporter.log("----------TC-VT-PROJ-01: Project created and verified successfully.----------",true);
    }
    
    

    // TC-VT-PROJ-02: Verify project search functionality by filtering with Project Name
   @Test(priority = 2,groups = {"functional"})
   public void verifyProjectSearchByName() throws Exception {

       VT_Projects_Pom_Page projectPage = new VT_Projects_Pom_Page(driver);

       String[] data = projectPage.readProjectDataFromExcel();
       String projectName = data[0];

       projectPage.navigateToProjectsList();
       projectPage.searchProjectByName(projectName);
       projectPage.clickFilteredProduct();
       Thread.sleep(1000);
       Assert.assertEquals(projectPage.isProjectSearchResultDisplayed(),projectName,"Project details do not match the expected question.");

       Reporter.log("----------TC-VT-PROJ-02: Project search verified successfully.----------",true);
   }
   

   	// TC-VT-PROJ-03: Verify project in the contact's related list
	  @Test(priority = 3, groups = {"functional"})
	  public void verifyProjectInContactRelatedList() throws Exception {
	
	      VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);
	      projectPage.verifyProjectInContactRelatedList();
	      Reporter.log("----------TC-VT-PROJ-03 passed: Project verified in Contact's related list.----------",true);
	  }
	  
	// TC-VT-PROJ-04: Verify project progress can be updated to 40%
	  @Test(priority = 4, groups = {"functional"})
	  public void verifyProjectProgressUpdate() throws Exception {

	      VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);

	      String[] data = projectPage.readProjectDataFromExcel();
	      String projectName = data[0];

	      projectPage.navigateToProjectsList();
	      projectPage.searchProjectByName(projectName);
	      projectPage.clickFilteredProduct();
	      projectPage.updateProjectProgress("40%");
	      String actualProgress = projectPage.getDisplayedProgress();
	      Thread.sleep(1000);
	      Assert.assertEquals(actualProgress,"40%","Project progress was not updated to 40%.");

	      Reporter.log("----------TC-VT-PROJ-04 passed: Project progress updated to 40%.----------",true);
	  }
	  
	// TC-VT-PROJ-05: Delete project and verify deletion
	  @Test(priority = 5, groups = {"functional"})
	  public void verifyProjectDeletion() throws Exception {

	      VT_Projects_Pom_Page projectPage = new VT_Projects_Pom_Page(driver);
	      String[] data = projectPage.readProjectDataFromExcel();
	      String projectName = data[0];

	      projectPage.navigateToProjectsList();
	      projectPage.searchProjectByName(projectName);
	      projectPage.clickFilteredProduct();

	      projectPage.clickDeleteButton();
	      projectPage.confirmDeleteIfPrompted();

	      projectPage.navigateToProjectsList();
	      projectPage.searchProjectByName(projectName);
	      
	      Thread.sleep(1000);
	      Assert.assertTrue(projectPage.isProjectSearchResultAbsent(projectName),"Project was not deleted successfully: " + projectName);

	      Reporter.log( "----------TC-VT-PROJ-05 passed: Project deleted and deletion verified.----------",true);
	  }
	  

	 // TC-VT-PROJ-06: Verify all project fields are displayed
	 @Test(priority = 6, groups = {"ui"})
	 public void verifyProjectCreationFieldsDisplayed() throws Exception {

	     VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);

	     projectPage.navigateToProjectsList();
	     projectPage.clickAddProject();
	     Thread.sleep(1000);
	     Assert.assertTrue(projectPage.areProjectFieldsDisplayed(),"One or more project creation fields are not displayed.");

	     Reporter.log("----------TC-VT-PROJ-06 passed: All project fields are displayed.----------",true);
	 }
	 

	 // TC-VT-PROJ-07: Verify validation when saving a blank project form
	@Test(priority = 7, groups = {"negative"})
	public void verifyProjectCreationWithBlankFields() throws Exception {

		
	    VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);

	    projectPage.navigateToProjectsList();
	    projectPage.clickAddProject();
	    projectPage.saveProject();
	    String popupMessage =projectPage.getProjectValidationPopupMessage();
	    Thread.sleep(1000);
	    Assert.assertFalse(popupMessage.trim().isEmpty(),"Validation popup did not display a message.");
	    Reporter.log("----------TC-VT-PROJ-07 passed: Blank project form validation verified.----------",true);
	}
	
	
	// TC-VT-PROJ-08: Verify Projects page navigation
	@Test(priority = 8, groups = {"integration"})
	public void verifyProjectsPageNavigation() throws Exception {

	    VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);

	    projectPage.navigateToProjectsList();
	    Thread.sleep(1000);
	    Assert.assertTrue(projectPage.isProjectsPageDisplayed(),"Projects page was not displayed.");
	    Reporter.log("----------TC-VT-PROJ-08 passed: Projects page navigation verified.----------",true);
	}
	

	 // TC-VT-PROJ-09: Verify New Project form navigation
	@Test(priority = 9, groups = {"integration"})
	public void verifyCreatingNewProjectPage() throws Exception {

	    VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);

	    projectPage.navigateToProjectsList();
	    projectPage.clickAddProject();
	    Thread.sleep(1000);
	    Assert.assertTrue(projectPage.isCreatingNewProjectDisplayed(),"Creating New Project page was not displayed.");

	    Reporter.log("----------TC-VT-PROJ-09 passed: Creating New Project page verified.----------",true);
	}
	
	
	

	 // TC-VT-PROJ-10: Enter project details without saving
	@Test(priority = 10, groups = {"smoke"})
	public void verifyProjectDetailsEntryWithoutSaving() throws Exception {

	    VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);

	    projectPage.navigateToProjectsList();
	    projectPage.clickAddProject();
	    projectPage.enterProjectDetailsWithoutSaving();

	    Reporter.log("----------TC-VT-PROJ-10 passed: Project details entered without saving.----------",true);
	}
	

	 // TC-VT-PROJ-11: System test 
	@Test(priority = 11, groups = {"system"})
	public void verifyProjectCreationSearchAndContactIntegration() throws Exception {

	    VT_Projects_Pom_Page projectPage =new VT_Projects_Pom_Page(driver);
	    String projectName = projectPage.createProjectFromExcel();

	    Assert.assertTrue(projectPage.isProjectDisplayed(projectName),"TC1 failed: Project was not created: " + projectName);

	    Reporter.log("Project created successfully.",true);
	    projectPage.navigateToProjectsList();
	    projectPage.searchProjectByName(projectName);
	    projectPage.clickFilteredProduct();
	    Thread.sleep(1000);
	    Assert.assertEquals(projectPage.isProjectSearchResultDisplayed(),projectName,"TC2 failed: Project details do not match.");

	    Reporter.log("Project search and details verified.",true);

	    projectPage.verifyProjectInContactRelatedList();

	    Reporter.log("Project verified in the contact's related list.",true);

	    Reporter.log("----------TC-VT-PROJ-11 passed: Project creation, search and contact integration verified.----------",true);
	}






}
