package org.taskstodo.controller;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.taskstodo.exception.InvalidParameterException;
import org.taskstodo.model.Query;
import org.taskstodo.service.TaskService;

@Controller
@RequestMapping(value = "/queries")
public class QueryController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(QueryController.class);
  
  @Autowired
  private TaskService taskService;
  
  // --

  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Query create(@RequestBody Query entry) {
    if (entry != null) {
      try {
        ObjectId id = taskService.addQuery(entry);
        return taskService.getQuery(id);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }
 
  @RequestMapping(value = "/api/delete/{queryId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable ObjectId queryId) {
    taskService.deleteQuery(queryId);
  }
  
  @RequestMapping(value = "/api/delete/{queryId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteByPost(@PathVariable ObjectId queryId) {
    taskService.deleteQuery(queryId);
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody List<Query> list(@PathVariable("taskId") ObjectId taskId) {
    List<Query> queries = new ArrayList<Query>();
    
    if (taskId != null) {
      queries = taskService.getQueriesByTask(taskId);
    } else {
      throw new InvalidParameterException();
    }
    
    return queries;
  }
  
  @RequestMapping(value = "/api/list/{taskId}/{engine}", method = RequestMethod.GET)
  public @ResponseBody List<Query> list(@PathVariable("taskId") ObjectId taskId, @PathVariable("engine") String engine) {
    List<Query> queries = new ArrayList<Query>();
    
    if (taskId != null && engine != null) {
      queries = taskService.getQueriesByTaskAndEngine(taskId, engine);
    } else {
      throw new InvalidParameterException();
    }
    
    return queries;
  }
}