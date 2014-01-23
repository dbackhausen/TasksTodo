package org.taskstodo.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.taskstodo.model.Note;
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class NoteTest {

//  private static final Logger LOGGER = LoggerFactory.getLogger(TaskTest.class);

  @Resource
  private TaskService taskService;
  
  private static ObjectId taskId;
  
  @Before
  public void setup() {
    if (taskId == null) {
      Task task = new Task();
      task.setTitle("JUnit Task with a Note");
      task.setDescription("Some test description ...");
      task.setDueDate(new Date());
      task.setPriority(3);
      task.setUrgency(2);
      
      // Create the test task
      taskId = taskService.addTask(task);
      Assert.assertNotNull(taskId);
    }
  }

  @After
  public void cleanup() {
    // Delete the test task
//    taskService.deleteTask(taskId);
  }
  
  @Test
  public void addNote() {
    Note note = new Note(taskId);
    note.setBody("Lorem ipsum dolores");
    
    // Create the note
    Assert.assertNotNull(taskId);
    ObjectId noteId = taskService.addNote(taskId, note);
    Assert.assertNotNull(noteId);
  }
  
  @Test
  public void getNotes() {
    Assert.assertNotNull(taskId);
    List<Note> notes = taskService.getNotes(taskId);
    Assert.assertNotNull(notes);
    Assert.assertTrue(notes.size() == 1);
  }
} 