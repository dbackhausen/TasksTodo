package org.taskstodo.test;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class TaskTest {

//  private static final Logger LOGGER = LoggerFactory.getLogger(TaskTest.class);

  @Resource
  private TaskService taskService;
  
  private static ObjectId taskId;

  @Test
  public void addTask() {
    Task task = new Task();
    task.setTitle("JUnit Task");
    task.setDescription("Some test description ...");
    task.setDueDate(new Date());
    task.setPriority(3);
    task.setUrgency(2);
    
    taskId = taskService.addTask(task);
  }
  
  @Test
  public void deleteTask() {
    // Delete the test task
    taskService.deleteTask(taskId);
    
    // Load all tasks
    List<Task> tasks = taskService.getTasks();
    
    Assert.assertNotNull(taskId);
    
    for (Task task : tasks) {
      Assert.assertNotNull(task.getId());
      Assert.assertNotSame(task.getId().toString(), taskId.toString());
    }
  }
} 