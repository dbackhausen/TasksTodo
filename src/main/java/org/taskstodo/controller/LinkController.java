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
import org.taskstodo.model.Link;
import org.taskstodo.service.TaskService;

@Controller
@RequestMapping(value = "/links")
public class LinkController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(LinkController.class);

  @Autowired
  private TaskService taskService;
  
  // --

  @RequestMapping(value = "/api/create/{taskId}", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Link add(@PathVariable ObjectId taskId, @RequestBody Link link) {
    ObjectId id = taskService.addLink(taskId, link);
    return taskService.getLink(id);
  }
  
  @RequestMapping(value = "/api/update/{linkId}", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Link update(@PathVariable ObjectId linkId, @RequestBody Link link) {
    Link l = taskService.getLink(linkId);
    l.setUrl(link.getUrl());
    l.setTitle(link.getTitle());
    l.setDescription(link.getDescription());
    taskService.updateLink(l);
    return taskService.getLink(linkId);
  }

  @RequestMapping(value = "/api/delete/{linkId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable ObjectId linkId) {
    taskService.deleteLink(linkId);
  }
  
  @RequestMapping(value = "/api/delete/{linkId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteByPost(@PathVariable ObjectId linkId) {
    taskService.deleteLink(linkId);
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody List<Link> list(@PathVariable("taskId") ObjectId taskId) {
    List<Link> links = new ArrayList<Link>();
    
    if (taskId != null) {
      links = taskService.getLinks(taskId);
    }
    
    return links;
  }
}