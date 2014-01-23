package org.taskstodo.test;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class UserTest {

  @Resource
  private TaskService taskService;
  
  @Before
  public void setup() {
  }

  @Test
  public void testAddTask() {
    Task task = new Task();
    task.setTitle("Test task");
    task.setDescription("Test description");
    
    taskService.addTask(task);
  }
} 