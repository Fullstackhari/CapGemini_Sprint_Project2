package ecom.VTiger;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import VTigerBaseClass.VTBaseClass;
import VTigerPomPages.VT_ProjectMileStone_Pom_Page;

public class VT_ProjectMileStone_Page extends VTBaseClass {
	// TC-VT-PM-01: Verify that Project Status, Priority, and Progress are updated successfully.
	@Test(priority = 1, groups = {"functional"})
	public void verifyProjectStatusPriorityAndProgressUpdate() throws Exception {

	    VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	    String[] data = milestonePage.readProjectMilestoneDataFromExcel();
	    String expectedProgress = data[3];
	    milestonePage.updateProjectDetailsFromExcel();
	    milestonePage.saveProjectChanges();
	    String actualProgress = milestonePage.getDisplayedProgress();
	    Thread.sleep(1000);
	    Assert.assertEquals(actualProgress, expectedProgress,"Project Progress was not updated correctly.");
	    Reporter.log("----------TC-VT-PM-01 passed: Project Status, Priority, and Progress updated successfully. Progress: "+ actualProgress + "----------",true);
	}
	
	 // TC-VT-PM-02: Verify that Project Task Priority and Progress are updated successfully.
	 @Test(priority = 2, groups = {"functional"})
	 public void verifyProjectTaskPriorityAndProgressUpdate() throws Exception {

	      VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	      String expectedProgress =milestonePage.readExpectedProjectTaskProgressFromExcel();
	      milestonePage.updateProjectTaskPriorityAndProgressFromExcel();
	      milestonePage.saveProjectChanges();
	      String actualProgress = milestonePage.getDisplayedProgress();
	      Thread.sleep(1000);
	      Assert.assertEquals(actualProgress,expectedProgress,"Project Task Progress was not updated correctly.");
	      Reporter.log("----------TC-VT-PM-02 passed: Project Task Priority and Progress updated successfully. Progress: "+ actualProgress + "----------",true);
	 }
	 
	 // TC-VT-PM-03: Verify that a new Project Milestone is created successfully.
	 @Test(priority = 3, groups = {"functional"})
	 public void verifyProjectMilestoneCreation() throws Exception {

	     VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	     String[] data = milestonePage.readMilestoneDataFromExcel();
	     String expectedMilestoneName = data[0];
	     milestonePage.navigateToProjectMilestones();
	     milestonePage.clickAddMilestone();
	     milestonePage.enterMilestoneDetailsFromExcel();
	     milestonePage.saveMilestone();
	     String actualMilestoneName = milestonePage.getDisplayedMilestoneName();
	     Thread.sleep(1000);
	     Assert.assertEquals(actualMilestoneName,expectedMilestoneName,"Project Milestone name does not match.");
	     Reporter.log("----------TC-VT-PM-03 passed: Project Milestone created successfully: "+ actualMilestoneName + "----------",true);
	 }
	 
	// TC-VT-PM-04: Verify that the Project Milestone appears in the Project related list.
	 @Test(priority = 4, groups = {"functional"})
	 public void verifyProjectMilestoneInProjectRelatedList() throws Exception {

	     VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	     String[] data = milestonePage.readMilestoneDataFromExcel();
	     String expectedMilestoneName = data[0];
	     milestonePage.navigateToProjectMilestoneRelatedList();
	     boolean isMilestoneDisplayed =milestonePage.isMilestoneInProjectRelatedList(expectedMilestoneName);
	     Thread.sleep(1000);
	     Assert.assertTrue(isMilestoneDisplayed,"Project Milestone was not found in the Project's related list: "+ expectedMilestoneName);
	     Reporter.log("----------TC-VT-PM-04 passed: Milestone found in Project related list: "+ expectedMilestoneName + "----------",true);
	 }
	 
	 
	// TC-VT-PM-05: Verify that the Project Milestone is deleted successfully.
	 @Test(priority = 5, groups = {"functional"})
	 public void verifyProjectMilestoneDeletion() throws Exception {

	     VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	     String[] data = milestonePage.readMilestoneDataFromExcel();
	     String milestoneName = data[0];
	     milestonePage.searchProjectMilestoneByName(milestoneName);
	     milestonePage.clickFilteredMilestone(milestoneName);
	     milestonePage.clickDeleteButton();
	     milestonePage.confirmDeleteIfPrompted();

	     milestonePage.searchProjectMilestoneByName(milestoneName);
	     boolean isMilestoneAbsent =milestonePage.isProjectMilestoneSearchResultAbsent(milestoneName);
	     Thread.sleep(1000);
	     Assert.assertTrue(isMilestoneAbsent,"Deleted Project Milestone is still displayed: " + milestoneName);
	     Reporter.log("----------TC-VT-PM-05 passed: Project Milestone deleted successfully: "+ milestoneName + "----------",true);
	 }
	 
	// TC-VT-PM-06: Verify that all Project Milestone form fields are visible.
	@Test(priority = 6, groups = {"ui"})
	public void verifyAllProjectMilestoneFieldsAreVisible() throws Exception {
	
	    VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	    milestonePage.navigateToProjectMilestones();
	    milestonePage.clickAddMilestone();
	    boolean allFieldsDisplayed =milestonePage.areAllMilestoneFieldsDisplayed();
	    Thread.sleep(1000);
	    Assert.assertTrue(allFieldsDisplayed,"One or more fields are not visible on the Create Project Milestone form.");
	    Reporter.log("----------TC-VT-PM-06 passed: All Project Milestone form fields are visible----------",true);
	}
	
	// TC-VT-PM-07: Verify that validation appears when the Milestone Name is empty.
	@Test(priority = 7, groups = {"negative"})
	public void verifyMilestoneCreationWithoutName() throws Exception {
	    VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	    milestonePage.navigateToProjectMilestones();
	    milestonePage.clickAddMilestone();
	    milestonePage.enterMilestoneDetailsWithoutName();
	    milestonePage.clickSaveMilestone();
	    String actualMessage = milestonePage.getMilestoneValidationMessage();
	    String expectedMessage = "Milestone Name cannot be empty";
	    Thread.sleep(1000);
	    Assert.assertEquals(actualMessage,expectedMessage,"Incorrect validation message for an empty Milestone Name.");
	    milestonePage.acceptMilestoneValidationAlert();
	    Reporter.log("----------TC-VT-PM-07 passed: Empty Milestone Name validation displayed successfully----------",true);
	}
	
	// TC-VT-PM-10: Verify that the Create Project Milestone heading is displayed.
	@Test(priority = 8, groups = {"integration"})
	public void verifyCreateProjectMilestoneFormHeading() throws Exception {

	    VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	    milestonePage.navigateToProjectMilestones();
	    milestonePage.clickAddMilestone();
	    boolean isHeadingDisplayed =milestonePage.isCreateMilestoneHeadingDisplayed();
	    Thread.sleep(1000);
	    Assert.assertTrue(isHeadingDisplayed,"Creating New Project Milestone heading is not displayed.");
	    Reporter.log("----------TC-VT-PM-10 passed: Creating New Project Milestone heading displayed successfully----------",true);
	}
	
	// TC-VT-PM-11: Verify that milestone details can be entered without saving.
	@Test(priority = 9, groups = {"smoke"})
	public void verifyProjectMilestoneDetailsCanBeEnteredWithoutSaving() throws Exception {

	    VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	    String[] data = milestonePage.readMilestoneDataFromExcel();

	    milestonePage.navigateToProjectMilestones();
	    milestonePage.clickAddMilestone();
	    milestonePage.enterAllMilestoneDetailsWithoutSaving();
	    Thread.sleep(1000);
	    Assert.assertTrue(milestonePage.areAllMilestoneFieldsDisplayed(),"Project Milestone form fields are not displayed.");

	    Reporter.log("----------TC-VT-PM-11 passed: Milestone details entered without saving----------",true);
	}
	
	// TC-VT-PM-12: Verify the complete Project Milestone end-to-end workflow.
	@Test(priority = 10, groups = {"system"})
	public void verifyProjectMilestoneEndToEndWorkflow() throws Exception {
	
	    VT_ProjectMileStone_Pom_Page milestonePage =new VT_ProjectMileStone_Pom_Page(driver);
	    // TC1: Update Project status, priority, and progress
	    String[] projectData = milestonePage.readProjectMilestoneDataFromExcel();
	    String expectedProjectProgress = projectData[3];
	
	    milestonePage.updateProjectDetailsFromExcel();
	    milestonePage.saveProjectChanges();
	
	    String actualProjectProgress = milestonePage.getDisplayedProgress();
	    Thread.sleep(1000);
	    Assert.assertEquals(actualProjectProgress,expectedProjectProgress,"Project Progress was not updated correctly.");
	    Reporter.log(" Project details updated successfully.",true);
	    
	    // TC2: Update Project Task priority and progress
	    String expectedTaskProgress =milestonePage.readExpectedProjectTaskProgressFromExcel();
	    milestonePage.updateProjectTaskPriorityAndProgressFromExcel();
	    milestonePage.saveProjectChanges();
	
	    String actualTaskProgress = milestonePage.getDisplayedProgress();
	    Thread.sleep(1000);
	    Assert.assertEquals(actualTaskProgress,expectedTaskProgress,"Project Task Progress was not updated correctly.");
	    Reporter.log("TC2 passed: Project Task details updated successfully.",true);
	
	    // TC3: Create Project Milestone
	    String[] milestoneData = milestonePage.readMilestoneDataFromExcel();
	    String expectedMilestoneName = milestoneData[0];
	
	    milestonePage.navigateToProjectMilestones();
	    milestonePage.clickAddMilestone();
	    milestonePage.enterMilestoneDetailsFromExcel();
	    milestonePage.saveMilestone();
	
	    String actualMilestoneName = milestonePage.getDisplayedMilestoneName();
	    Thread.sleep(1000);
	    Assert.assertEquals(actualMilestoneName,expectedMilestoneName,"Project Milestone was not created correctly.");
	    Reporter.log("TC3 passed: Project Milestone created successfully.",true);
	
	    // TC4: Verify milestone in the Project related list
	    milestonePage.navigateToProjectMilestoneRelatedList();
	    boolean isMilestoneDisplayed =milestonePage.isMilestoneInProjectRelatedList(expectedMilestoneName);
	    Thread.sleep(1000);
	    Assert.assertTrue(isMilestoneDisplayed,"Project Milestone was not found in the Project related list: "+ expectedMilestoneName);
	    Reporter.log("----------TC-VT-PM-12 passed: Complete Project Milestone workflow verified----------",true);
	}

	 
}


