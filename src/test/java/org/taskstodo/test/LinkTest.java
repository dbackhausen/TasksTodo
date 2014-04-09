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
import org.taskstodo.model.Link;
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class LinkTest {
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
  public void manageLink() {
    // Create link and add to task
    Link link = new Link();
    link.setTitle("Test Link");
    link.setUrl("http://www.test.de");
    ObjectId linkId = taskService.addLink(taskId, link);
    Assert.assertNotNull(linkId);
    
    // Read link and validate
    link = null;
    link = taskService.getLink(linkId);
    Assert.assertNotNull(link);
    Assert.assertEquals(link.getTitle(), "Test Link");
    Assert.assertEquals(link.getUrl(), "http://www.test.de");
    
    // Update link
    link.setTitle("Eightbit");
    link.setUrl("http://www.eightbit.de");
    taskService.updateLink(link);
    
    // Read link and validate
    link = null;
    link = taskService.getLink(linkId);
    Assert.assertNotNull(link);
    Assert.assertEquals(link.getTitle(), "Eightbit");
    Assert.assertEquals(link.getUrl(), "http://www.eightbit.de");
    
    // Delete link and validate
    taskService.deleteLink(link.getId());
    link = null;
    link = taskService.getLink(linkId);
    Assert.assertNull(link);
    
  }
  
  // --
  
  private void createTask() {
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