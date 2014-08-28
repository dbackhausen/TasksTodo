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
import org.taskstodo.model.History;
import org.taskstodo.service.TaskService;

@Controller
@RequestMapping(value = "/history")
public class HistoryController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryController.class);
  
  @Autowired
  private TaskService taskService;
  
  // --

  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody History create(@RequestBody History history) {
    if (history != null) {
      try {
        ObjectId id = taskService.addHistory(history);
        return taskService.getHistory(id);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/update/", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody History update(@RequestBody History history) {
    if (history != null) {
      try {
        History h = taskService.getHistory(history.getId());
        h.setUrl(history.getUrl());
        h.setTitle(history.getTitle());
        h.setDescription(history.getDescription());
        h.setThumbnail(history.getThumbnail());
        h.setRelevance(history.getRelevance());
        taskService.updateHistory(h);
        
        return taskService.getHistory(history.getId());
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }

  @RequestMapping(value = "/api/delete/{historyId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable ObjectId historyId) {
    taskService.deleteHistory(historyId);
  }
  
  @RequestMapping(value = "/api/delete/{historyId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteByPost(@PathVariable ObjectId historyId) {
    taskService.deleteHistory(historyId);
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody List<History> list(@PathVariable("taskId") ObjectId taskId) {
    List<History> histories = new ArrayList<History>();
    
    if (taskId != null) {
      histories = taskService.getHistories(taskId);
    }
    
    return histories;
  }
}