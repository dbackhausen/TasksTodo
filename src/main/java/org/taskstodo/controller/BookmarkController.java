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
import org.taskstodo.model.Bookmark;
import org.taskstodo.service.TaskService;

@Controller
@RequestMapping(value = "/bookmarks")
public class BookmarkController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkController.class);
  
  @Autowired
  private TaskService taskService;
  
  // --

  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Bookmark create(@RequestBody Bookmark bookmark) {
    if (bookmark != null) {
      try {
        ObjectId id = taskService.addBookmark(bookmark);
        return taskService.getBookmark(id);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }     
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/update/", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Bookmark update(@RequestBody Bookmark bookmark) {
    if (bookmark != null) {
      try {
        Bookmark b = taskService.getBookmark(bookmark.getId());
        b.setUrl(bookmark.getUrl());
        b.setTitle(bookmark.getTitle());
        b.setDescription(bookmark.getDescription());
        b.setThumbnail(bookmark.getThumbnail());
        b.setRelevance(bookmark.getRelevance());
        b.setDeleted(bookmark.isDeleted());
        taskService.updateBookmark(b);
        
        return taskService.getBookmark(bookmark.getId());
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }

  @RequestMapping(value = "/api/delete/{bookmarkId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable ObjectId bookmarkId) {
    taskService.deleteBookmark(bookmarkId);
  }
  
  @RequestMapping(value = "/api/delete/{bookmarkId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteByPost(@PathVariable ObjectId bookmarkId) {
    taskService.deleteBookmark(bookmarkId);
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody List<Bookmark> read(@PathVariable("taskId") ObjectId taskId) {
    List<Bookmark> bookmarks = new ArrayList<Bookmark>();
    
    if (taskId != null) {
      bookmarks = taskService.getBookmarks(taskId);
    }
    
    return bookmarks;
  }
}