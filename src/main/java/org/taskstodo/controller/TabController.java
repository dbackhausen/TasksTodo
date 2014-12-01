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
import org.taskstodo.model.Tab;
import org.taskstodo.service.TaskService;

@Controller
@RequestMapping(value = "/tabs")
public class TabController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TabController.class);
  
  @Autowired
  private TaskService taskService;
  
  // --

  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Tab create(@RequestBody Tab tab) {
    if (tab != null) {
      try {
        ObjectId id = taskService.addTab(tab);
        return taskService.getTab(id);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/update/", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Tab update(@RequestBody Tab tab) {
    if (tab != null) {
      try {
        Tab t = taskService.getTab(tab.getId());
        t.setId(tab.getId());
        t.setTitle(tab.getTitle());
        t.setUrl(tab.getUrl());
        t.setThumbnail(tab.getThumbnail());
        t.setDeleted(tab.isDeleted());
        taskService.updateTab(t);
        
        return taskService.getTab(tab.getId());
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }

  @RequestMapping(value = "/api/delete/", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable Tab tab) {
    if (tab != null) {
      LOGGER.debug("Delete tab: " + tab.toString());
//      taskService.deleteTab(tab);
    }
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody List<Tab> read(@PathVariable("taskId") ObjectId taskId) {
    List<Tab> bookmarks = new ArrayList<Tab>();
    
    if (taskId != null) {
      bookmarks = taskService.getTabs(taskId);
    }
    
    return bookmarks;
  }
}