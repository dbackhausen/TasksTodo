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
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class FileTest {
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
  public void manageFile() {
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

//  @Test
//  public void testAddFile() {
//    try {
//    java.io.File f = new java.io.File("/Users/dbackhausen/test.h2.db");
//    byte[] fbytes = IOUtils.toByteArray(new FileInputStream(f));
//    
//    File file = new File();
//    file.setTaskId(new ObjectId("52950ebe3004676c2b42a108"));
//    file.setTitle("JUnit Test File");
//    file.setDescription("This is a test file");
//    file.setBytes(fbytes);
//    
//    ObjectId id = fileService.saveFile(file);
//    Assert.assertNotNull(id);
//    } catch (IOException e) {
//      // TODO Auto-generated catch block
//      e.printStackTrace();
//    }
//  }
} 