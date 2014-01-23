package org.taskstodo.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.taskstodo.model.Link;
import org.taskstodo.model.Note;
import org.taskstodo.model.Task;
import org.taskstodo.service.FileService;
import org.taskstodo.service.TaskService;
import org.taskstodo.validator.TaskValidator;

@Controller
@RequestMapping(value = "/tasks")
public class TaskController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

  @Autowired
  private TaskService taskService;
  
//  @Autowired
//  private FileService fileService;
//  
//  @Autowired
//  private TaskValidator taskValidator;
  
//  // --
//  
//  @ModelAttribute("newTask")
//  public Task getNewTask() {
//    return new Task();
//  }
//  
//  @ModelAttribute("newNote")
//  public Note getNewNote() {
//    return new Note();
//  }
//  
//  @ModelAttribute("newLink")
//  public Link getNewLink() {
//    return new Link();
//  }
//  
//  @ModelAttribute("tasks")
//  public List<Task> getTasks() {
//    return taskService.getTasks();
//  }
//  
//  // --
//
//  @RequestMapping(value = "/add", method = RequestMethod.POST)
//  public String createTask(@ModelAttribute Task newTask, BindingResult result, ModelMap model) {
//    if (result.hasErrors()) {
//      LOGGER.error(result.hasFieldErrors() ? result.getFieldError().toString() : (result.hasGlobalErrors() ? result.getGlobalError().toString() : " No error?"));
//      return "redirect:/";
//    }
//    
//    ObjectId id = taskService.addTask(newTask);
//    model.addAttribute("currentTask", taskService.getTask(id));
//
//    return "redirect:/tasks/" + id;
//  }
//  
//  @RequestMapping(value = "/{id}", method = RequestMethod.GET)
//  public String findTask(@PathVariable ObjectId id, ModelMap model) {
//    LOGGER.debug("Load task with identifier " + id);
//    model.addAttribute("currentTask", taskService.getTask(id));
////    model.addAttribute("notes", taskService.getNotes(id));
////    model.addAttribute("links", taskService.getLinks(id));
////    model.addAttribute("files", fileService.getFiles(id));
//    
//    return "index";
//  }
//
//  @RequestMapping(value = "/list", method = RequestMethod.GET)
//  public String findAllTasks(ModelMap model) {
//    model.addAttribute("currentTask", new Task());
//    return "index";
//  }
//  
//  @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
//  public String createTask(@PathVariable ObjectId id, @ModelAttribute Task currentTask, BindingResult result, ModelMap model) {
//    if (result.hasErrors()) {
//      LOGGER.error(result.hasFieldErrors() ? result.getFieldError().toString() : (result.hasGlobalErrors() ? result.getGlobalError().toString() : " No error?"));
//      return "redirect:/tasks/{id}";
//    }
//    
//    currentTask.setId(id);
//    taskService.updateTask(currentTask);
//
//    return "redirect:/tasks/{id}";
//  }
//  
//  @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
//  public String deleteTask(@PathVariable ObjectId id) {
//    LOGGER.debug("Remove task resource with identifier " + id.toString());
//    taskService.deleteTask(id);
//    
//    return "redirect:/";
//  }
  
  // --- API ------------------------------------------------------------------

  @RequestMapping(value = "/api/create/{parentId}", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Task add(@PathVariable ObjectId parentId, @RequestBody Task task) {
//    task.setParent(parentId);
    ObjectId id = taskService.addTask(task);
    return taskService.getTask(id);
  }
  
  @RequestMapping(value = "/api/update/{taskId}", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Task update(@PathVariable ObjectId taskId, @RequestBody Task task) {
    Task t = taskService.getTask(taskId);
//    l.setUrl(link.getUrl());
//    l.setTitle(link.getTitle());
//    l.setDescription(link.getDescription());
    taskService.updateTask(t);
    return taskService.getTask(taskId);
  }

  @RequestMapping(value = "/api/delete/{taskId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable ObjectId taskId) {
    taskService.deleteTask(taskId);
  }
  
  @RequestMapping(value = "/api/list/", method = RequestMethod.GET)
  public @ResponseBody List<Task> list(/*@PathVariable("taskId") ObjectId taskId*/) {
    return taskService.getTasks();
  }
  
//  // --
//  
//  @InitBinder
//  public void initBinder(WebDataBinder webDataBinder) {
//    SimpleDateFormat dateFormat = new SimpleDateFormat();
//    dateFormat.setLenient(false);
//    webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
//  }
}