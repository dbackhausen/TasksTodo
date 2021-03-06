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
import org.taskstodo.model.Note;
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class NoteTest {
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
  public void manageNote() {
    try {
      // Create note and add to task
      Note note = new Note();
      note.setTaskId(taskId);
      note.setBody("Lorem ipsum dolores");
      ObjectId noteId = taskService.addNote(note);
      Assert.assertNotNull(noteId);
      
      // Read note and validate
      note = null;
      note = taskService.getNote(noteId);
      Assert.assertNotNull(note);
      Assert.assertEquals(note.getBody(), "Lorem ipsum dolores");
      
      // Update note
      note.setBody("Test Test Test");
      taskService.updateNote(note);
      
      // Read note and validate
      note = null;
      note = taskService.getNote(noteId);
      Assert.assertNotNull(note);
      Assert.assertEquals(note.getBody(), "Test Test Test");
      
      // Delete note and validate
      taskService.deleteNote(note.getId());
      note = null;
      note = taskService.getNote(noteId);
      Assert.assertNull(note);
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