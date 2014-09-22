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
import org.taskstodo.model.Note;
import org.taskstodo.service.TaskService;

@Controller
@RequestMapping(value = "/notes")
public class NoteController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(NoteController.class);
  
  @Autowired
  private TaskService taskService;

  // --

  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Note create(@RequestBody Note note) {
    if (note != null) {
      try {
        ObjectId id = taskService.addNote(note);
        return taskService.getNote(id);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/update/", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Note update(@RequestBody Note note) {
    if (note != null) {
      try {
        Note n = taskService.getNote(note.getId());
        n.setBody(note.getBody());
        n.setDeleted(note.isDeleted());
        taskService.updateNote(n);
        
        return taskService.getNote(note.getId());
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }

  @RequestMapping(value = "/api/delete/{noteId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable ObjectId noteId) {
    taskService.deleteNote(noteId);
  }
  
  @RequestMapping(value = "/api/delete/{noteId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteByPost(@PathVariable ObjectId noteId) {
    taskService.deleteNote(noteId);
  }
  
  @RequestMapping(value = "/api/list/{taskId}", method = RequestMethod.GET)
  public @ResponseBody List<Note> list(@PathVariable("taskId") ObjectId taskId) {
    List<Note> notes = new ArrayList<Note>();

    if (taskId != null) {
      notes = taskService.getNotes(taskId);
    }

    return notes;
  }
}