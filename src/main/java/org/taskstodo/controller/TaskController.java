package org.taskstodo.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.taskstodo.exception.TaskNotFoundException;
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);
  
  @Autowired
  private TaskService taskService;

  // --
  
  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Task addTask(@RequestBody Task task) {
    if (task != null) {
      try {
        ObjectId id = taskService.addTask(task); 
        return taskService.getTask(id);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/update/", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Task updateTask(@RequestBody Task task) {
    if (task != null) {
      try {
        Task t = taskService.getTask(task.getId());
        
        if (t != null) {
          t.setTitle(task.getTitle());
          t.setDescription(task.getDescription());
          t.setGoalId(task.getGoalId());
          t.setParentId(task.getParentId());
          t.setDueDate(task.getDueDate());
          t.setCompletedDate(task.getCompletedDate());
          t.setReminderDate(task.getReminderDate());
          t.setUrgency(task.getUrgency());
          t.setPriority(task.getPriority());
          t.setLevel(task.getLevel());
          t.setPosition(task.getPosition());
          t.setCompleted(task.isCompleted());
          t.setDeleted(task.isDeleted());
          
          taskService.updateTask(task);
          
          return taskService.getTask(task.getId());
        } else {
          throw new TaskNotFoundException();
        }
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }

  @RequestMapping(value = "/api/delete/{taskId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteTask(@PathVariable ObjectId taskId) {
    taskService.deleteTask(taskId, true);
  }
  
  @RequestMapping(value = "/api/delete/{taskId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteTaskByPost(@PathVariable ObjectId taskId) {
    taskService.deleteTask(taskId, true);
  }
  
  @RequestMapping(value = "/api/read/{taskId}", method = RequestMethod.GET)
  public @ResponseBody Task getTask(@PathVariable("taskId") ObjectId taskId) {
    return taskService.getTask(taskId);
  }
    
  @RequestMapping(value = "/api/list/{goalId}", method = RequestMethod.GET)
  public @ResponseBody List<Task> getAllTasksByGoal(@PathVariable("goalId") ObjectId goalId) {
    return taskService.getTasksOrderedBy(goalId, "position", Direction.ASC);
  }
  
  @RequestMapping(value = "/api/list/completed/{goalId}", method = RequestMethod.GET)
  public @ResponseBody List<Task> getAllCompletedTasksByGoal(@PathVariable("goalId") ObjectId goalId) {
    return taskService.getCompletedTasks(goalId);
  }
}