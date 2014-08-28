package org.taskstodo.test;

import java.util.Date;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.Bookmark;
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class BookmarkTest {
  @Resource
  private TaskService taskService;
  
  private static ObjectId taskId;

  @Before
  public void prepare() {
    createTask();
  }
  
  @After
  public void cleanup() {
    deleteTask();
  }
  
  @Test
  public void manageBookmark() {
    try {
      // Create bookmark and add to task
      Bookmark bookmark = new Bookmark();
      bookmark.setTaskId(taskId);
      bookmark.setTitle("Test Bookmark");
      bookmark.setUrl("http://www.test.de");
      
      ObjectId linkId = taskService.addBookmark(bookmark);
      Assert.assertNotNull(linkId);
      
      // Read bookmark and validate
      bookmark = null;
      bookmark = taskService.getBookmark(linkId);
      Assert.assertNotNull(bookmark);
      Assert.assertEquals(bookmark.getTitle(), "Test Bookmark");
      Assert.assertEquals(bookmark.getUrl(), "http://www.test.de");
      
      // Update bookmark
      bookmark.setTitle("Eightbit");
      bookmark.setUrl("http://www.eightbit.de");
      taskService.updateBookmark(bookmark);
      
      // Read bookmark and validate
      bookmark = null;
      bookmark = taskService.getBookmark(linkId);
      Assert.assertNotNull(bookmark);
      Assert.assertEquals(bookmark.getTitle(), "Eightbit");
      Assert.assertEquals(bookmark.getUrl(), "http://www.eightbit.de");
      
      // Delete bookmark and validate
      taskService.deleteBookmark(bookmark.getId());
      bookmark = null;
      bookmark = taskService.getBookmark(linkId);
      Assert.assertNull(bookmark);
    } catch (ServiceException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
  }
  
  // --
  
  private void createTask() {
    try {
      Date d = new Date();
      
      Task task = new Task();
      task.setTitle("JUnit Task");
      task.setDescription("Some test description ...");
      task.setDueDate(d);
      task.setPriority(3);
      task.setUrgency(2);
      
      // Add the new task
      taskId = taskService.addTask(task);
      Assert.assertNotNull(taskId);
      
      // Load created task
      task = null;
      task = taskService.getTask(taskId);
      Assert.assertNotNull(task);
      Assert.assertEquals(task.getTitle(), "JUnit Task");
      Assert.assertEquals(task.getDescription(), "Some test description ...");
      Assert.assertEquals(task.getDueDate(), d);
      Assert.assertEquals(task.getPriority(), 3);
      Assert.assertEquals(task.getUrgency(), 2);
    } catch (ServiceException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
  }
  
  private void deleteTask() {
    // Check if the task ID has been set before
    Assert.assertNotNull(taskId);
    
    // Delete the test task
    taskService.deleteTask(taskId, true);
    
    // Check if task does not longer exist
    Task task = taskService.getTask(taskId);
    Assert.assertNull(task);
  }
} 