
package ecom.VTiger;

import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.Test;

import VTigerBaseClass.VTBaseClass;
import VTigerPomPages.VT_ProjectTasks_Pom_Page;

public class VT_ProjectTasks_Page extends VTBaseClass {
	// TC-VT-PT-01: Verify that a new Project Task is created successfully.
    @Test(priority = 1, groups = {"functional"})
    public void verifyProjectTaskCreation() throws Exception {

        VT_ProjectTasks_Pom_Page taskPage =new VT_ProjectTasks_Pom_Page(driver);
        String[] data = taskPage.readProjectTaskDataFromExcel();
        String taskName = taskPage.createProjectTaskFromExcel();
        Assert.assertTrue(taskPage.isProjectTaskDisplayed(taskName),"Project Task was not created successfully: " + taskName);
        Reporter.log("----------TC-VT-PT-01 passed: Project Task created and verified successfully.----------",true);
    }
    
    // TC-VT-PT-02: Verify that the Project Task is displayed in the Project related list.
    @Test(priority = 2, groups = {"functional"})
    public void verifyProjectTaskInProjectRelatedList() throws Exception {

        VT_ProjectTasks_Pom_Page taskPage =new VT_ProjectTasks_Pom_Page(driver);

        String[] data = taskPage.readProjectTaskDataFromExcel();
        String taskName = data[0];

        taskPage.verifyProjectTaskInProjectRelatedList();

        boolean isTaskDisplayed =taskPage.isProjectTaskInRelatedList(taskName);
        Assert.assertTrue(isTaskDisplayed,"Project Task was not displayed in the Project Tasks related list: "+ taskName);

        Reporter.log("----------TC-VT-PT-02 passed: Project Task verified in the Project related list: "+ taskName + "----------",true);
    }
    
    // TC-VT-PT-03: Verify that the Project Task is deleted successfully.
    @Test(priority = 3, groups = {"functional"})
    public void verifyProjectTaskDeletion() throws Exception {

        VT_ProjectTasks_Pom_Page taskPage =new VT_ProjectTasks_Pom_Page(driver);
        String[] data = taskPage.readProjectTaskDataFromExcel();
        String taskName = data[0];
        taskPage.navigateToProjectTasks();
        taskPage.searchProjectTaskByName(taskName);
        taskPage.clickFilteredProjectTask(taskName);
        taskPage.clickDeleteButton();
        taskPage.confirmDeleteIfPrompted();
        taskPage.navigateToProjectTasks();
        taskPage.searchProjectTaskByName(taskName);
        boolean isTaskAbsent =taskPage.isProjectTaskSearchResultAbsent(taskName);
        Assert.assertTrue(isTaskAbsent,"Project Task is still displayed after deletion: " + taskName);

        Reporter.log("----------TC-VT-PT-03 passed: Project Task deleted and verified absent: "+ taskName + "----------",true);
    }
    
    
    // TC-VT-PT-05: Verify that all Project Task form fields are displayed.
    @Test(priority = 4, groups = {"ui"})
    public void verifyProjectTaskFieldsDisplayed() throws Exception {

        VT_ProjectTasks_Pom_Page taskPage =new VT_ProjectTasks_Pom_Page(driver);

        taskPage.navigateToProjectTasks();
        taskPage.clickAddTask();

        Assert.assertTrue(taskPage.areProjectTaskFieldsDisplayed(),"One or more Project Task fields are not displayed.");

        Reporter.log("----------TC-VT-PT-05 passed: All Project Task fields are displayed.----------",true);
    }
    
    
    // TC-VT-PT-07: Verify that validation appears when the Task Name is empty.
    @Test(priority = 5, groups = {"negative"})
    public void verifyProjectTaskCreationWithoutTaskName() throws Exception {
        VT_ProjectTasks_Pom_Page taskPage = new VT_ProjectTasks_Pom_Page(driver);

        taskPage.navigateToProjectTasks();
        taskPage.clickAddTask();

        String[] taskData = taskPage.readProjectTaskDataFromExcel();

        taskPage.enterProjectTaskDetailsExceptTaskNameAndRelatedTo(taskData);
        taskPage.saveProjectTask();

        Assert.assertTrue(taskPage.verifyTaskNameEmptyPopup(),"Expected popup 'Task Name cannot be empty' was not displayed.");
        Reporter.log("----------TC-VT-PT-07 passed: Validation popup displayed when Task Name was empty.----------",true);
    }
    
    
    // TC-VT-PT-08: Verify that no matching Project Task is found when using the Priority filter incorrectly.
    @Test(priority = 6, groups = {"negative"})
    public void verifyProjectTaskSearchByPriority() throws Exception {
        VT_ProjectTasks_Pom_Page taskPage = new VT_ProjectTasks_Pom_Page(driver);

        String[] taskData = taskPage.readProjectTaskDataFromExcel();
        String taskName = taskData[0];

        taskPage.navigateToProjectTasks();
        taskPage.searchProjectTaskByPriority(taskName);
        Assert.assertTrue(taskPage.isProjectTaskSearchResultAbsent(taskName),"Project Task was found when searching by Priority using the task name." );
        Reporter.log("----------TC-VT-PT-08 passed: No matching Project Task found using the Priority filter.----------",true);
    }
    
    
    // TC-VT-PT-09: Verify that navigation to the Project Tasks page works successfully.
    @Test(priority = 7, groups = {"integration"})
    public void verifyProjectTasksPageNavigation() throws Exception {
        VT_ProjectTasks_Pom_Page taskPage = new VT_ProjectTasks_Pom_Page(driver);
        taskPage.navigateToProjectTasks();
        Assert.assertTrue(taskPage.isProjectTasksPageDisplayed(),"Project Tasks page is not displayed.");
        Reporter.log("----------TC-VT-PT-09 passed: Project Tasks page navigation verified successfully.----------",true);
    }
    
    
    // TC-VT-PT-10: Verify that the Create Project Task page is displayed successfully.
    @Test(priority = 8, groups = {"integration"})
    public void verifyCreateProjectTaskPageNavigation() throws Exception {
        VT_ProjectTasks_Pom_Page taskPage = new VT_ProjectTasks_Pom_Page(driver);
        taskPage.navigateToProjectTasks();
        taskPage.clickAddTask();
        Assert.assertTrue(taskPage.isCreateProjectTaskPageDisplayed(),"Creating New Project Task page is not displayed.");
        Reporter.log("----------TC-VT-PT-10 passed: Creating New Project Task page displayed successfully.----------",true);
    }
    
    
    // TC-VT-PT-11: Verify that Project Task details can be entered without saving.
    @Test(priority = 9, groups = {"smoke"})
    public void verifyProjectTaskDetailsCanBeEntered() throws Exception {
        VT_ProjectTasks_Pom_Page taskPage = new VT_ProjectTasks_Pom_Page(driver);
        taskPage.navigateToProjectTasks();
        taskPage.clickAddTask();
        taskPage.enterProjectTaskDetailsWithoutSaving();
        Reporter.log("----------TC-VT-PT-11 passed: All Project Task details entered without saving.----------",true);
    }
    
    
    // TC-VT-PT-12: Verify that the Project Task is created and displayed in the Project related list.
    @Test(priority = 10, groups = {"system"})
    public void verifyProjectTaskCreationAndRelatedListIntegration() throws Exception {

        VT_ProjectTasks_Pom_Page taskPage =new VT_ProjectTasks_Pom_Page(driver);
        String[] data = taskPage.readProjectTaskDataFromExcel();
        String taskName = taskPage.createProjectTaskFromExcel();

        Assert.assertTrue(taskPage.isProjectTaskDisplayed(taskName),"Project Task was not created successfully: " + taskName);
        taskPage.verifyProjectTaskInProjectRelatedList();
        boolean isTaskDisplayed =taskPage.isProjectTaskInRelatedList(taskName);
        Assert.assertTrue(isTaskDisplayed,"Project Task was not displayed in the Project Tasks related list: " + taskName);

        Reporter.log("----------TC-VT-PT-12 passed: Project Task created and verified in the Project related list: "+ taskName + "----------",true);
    }
    
}
