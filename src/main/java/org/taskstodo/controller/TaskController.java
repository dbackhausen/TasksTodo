package org.taskstodo.controller;

import java.util.List;

import org.bson.types.ObjectId;
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
import org.taskstodo.model.Task;
import org.taskstodo.service.TaskService;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
  @Autowired
  private TaskService taskService;

  // --
  
  @RequestMapping(value = "/api/create/{goalId}", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Task addTask(@PathVariable ObjectId goalId, @RequestBody Task task) {
    task.setGoal(goalId);
    ObjectId id = taskService.addTask(task);
    return taskService.getTask(id);
  }
  
  @RequestMapping(value = "/api/create/subtask/{parentId}", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Task addSubtask(@PathVariable ObjectId parentId, @RequestBody Task task) {
    Task parent = taskService.getTask(parentId);
    
    if (parent != null) {
      int counter = taskService.getSubTaskCount(parentId) + 1;
      task.setParentTask(parentId);
      task.setPosition(counter);
      ObjectId id = taskService.addTask(task);
      return taskService.getTask(id);
    }

    return null;
  }
  
  @RequestMapping(value = "/api/update/{taskId}", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Task updateTask(@PathVariable ObjectId taskId, @RequestBody Task task) {
    Task t = taskService.getTask(taskId);
    t.setTitle(task.getTitle());
    t.setDescription(task.getDescription());
    t.setGoal(task.getGoal());
    t.setParentTask(task.getParentTask());
    t.setDueDate(task.getDueDate());
    t.setCompletedDate(task.getCompletedDate());
    t.setReminderDate(task.getReminderDate());
    t.setUrgency(task.getUrgency());
    t.setPriority(task.getPriority());
    t.setPosition(task.getPosition());
    taskService.updateTask(t);
    
    return taskService.getTask(taskId);
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
  
  @RequestMapping(value = "/api/read/{parentId}/subtasks", method = RequestMethod.GET)
  public @ResponseBody List<Task> getSubTasks(@PathVariable("parentId") ObjectId parentId) {
    return taskService.getSubTasks(parentId);
  }
  
  @RequestMapping(value = "/api/list/", method = RequestMethod.GET)
  public @ResponseBody List<Task> getAllTasks() {
    return taskService.getTasksOrderedBy("modified", Direction.DESC);
  }
  
  @RequestMapping(value = "/api/list/{goalId}", method = RequestMethod.GET)
  public @ResponseBody List<Task> getAllTasksByGoal(@PathVariable("goalId") ObjectId goalId) {
    return taskService.getTasksByGoal(goalId);
  }
}