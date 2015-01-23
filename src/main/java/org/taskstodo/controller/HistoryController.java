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
import org.taskstodo.model.HistoryEntry;
import org.taskstodo.model.Query;
import org.taskstodo.service.TaskService;
import org.taskstodo.util.UrlUtils;

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
  public @ResponseBody HistoryEntry create(@RequestBody HistoryEntry entry) {
    if (entry != null) {
      try {
        ObjectId id = taskService.addHistoryEntry(entry);
        
        Query query = UrlUtils.getQueryFromUrlString(entry.getUrl());
        
        if (query != null) {
          query.setTaskId(entry.getTaskId());
          LOGGER.info("Found search query: " + query.toString());
          taskService.addQuery(query);
        }
                
        return taskService.getHistoryEntry(id);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/update/", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody HistoryEntry update(@RequestBody HistoryEntry entry) {
    if (entry != null) {
      try {
        HistoryEntry h = taskService.getHistoryEntry(entry.getId());
        h.setUrl(entry.getUrl());
        h.setTitle(entry.getTitle());
        h.setDescription(entry.getDescription());
        h.setThumbnail(entry.getThumbnail());
        h.setRelevance(entry.getRelevance());
        h.setDeleted(entry.isDeleted());
        taskService.updateHistoryEntry(h);
        
        return taskService.getHistoryEntry(entry.getId());
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }

  @RequestMapping(value = "/api/delete/{entryId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable ObjectId entryId) {
    taskService.deleteHistoryEntry(entryId);
  }
  
  @RequestMapping(value = "/api/delete/{entryId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteByPost(@PathVariable ObjectId entryId) {
    taskService.deleteHistoryEntry(entryId);
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody List<HistoryEntry> list(@PathVariable("taskId") ObjectId taskId) {
    List<HistoryEntry> history = new ArrayList<HistoryEntry>();
    
    if (taskId != null) {
      history = taskService.getHistory(taskId);
    }
    
    return history;
  }
}