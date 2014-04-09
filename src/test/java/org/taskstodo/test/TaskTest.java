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
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class TaskTest {
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
  public void update() {
    // Check if the task ID has been set before
    Assert.assertNotNull(taskId);
    
    // Load existing task
    Task task = taskService.getTask(taskId);
    Assert.assertNotNull(task);
    
    // Modify some values
    task.setDescription("Task has been modified");
    task.setPriority(4);
    task.setUrgency(1);
    
    // Update task
    taskService.updateTask(task);
    
    // Load updated task
    task = null;
    task = taskService.getTask(taskId);
    Assert.assertNotNull(task);
    Assert.assertEquals(task.getTitle(), "JUnit Task");
    Assert.assertEquals(task.getDescription(), "Task has been modified");
    Assert.assertEquals(task.getPriority(), 4);
    Assert.assertEquals(task.getUrgency(), 1);
  }
  
  @Test
  public void subtask() {
    // Check if the task ID has been set before
    Assert.assertNotNull(taskId);
    
    // Get all subtasks of parent
    List<Task> subtasks = taskService.getSubTasks(taskId);
    int counter = subtasks.size() + 1;
    
    // Create a new sub task manually
    Task subTask = new Task();
    subTask.setTitle("First subtask");
    subTask.setParentTask(taskId);
    subTask.setPosition(counter);
    taskService.addTask(subTask);
    
    // Create a new sub task with service method
    subTask = null;
    subTask = new Task();
    subTask.setTitle("Second subtask");
    taskService.addSubTask(taskId, subTask);
    
    // Find the newly created subtask
    subtasks = null;
    subtasks = taskService.getSubTasks(taskId);
    Assert.assertNotNull(subtasks);
    Assert.assertEquals(2, subtasks.size());
  }
  
  @Test
  public void createTestData() {
    // Create a new parent task
    Task parentTask = new Task();
    parentTask.setTitle("I am a parent task");
    parentTask.setGoal(new ObjectId("530375d1300445563c8fdc71"));
    parentTask.setDescription("I am just a test task");
    ObjectId parentId = taskService.addTask(parentTask);
    
    // Create a first test subtask
    Task subTask = new Task();
    subTask.setTitle("I am the first subtask");
    subTask.setDescription("I am just another test task");
    taskService.addSubTask(parentId, subTask);
    
    // Create a second test subtask
    subTask = new Task();
    subTask.setTitle("I am the second subtask");
    subTask.setDescription("I am just another test task");
    taskService.addSubTask(parentId, subTask);
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