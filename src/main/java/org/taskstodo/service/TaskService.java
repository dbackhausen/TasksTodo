package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.model.Link;
import org.taskstodo.model.Note;
import org.taskstodo.model.Task;

public interface TaskService {
  /////////////////////////////////////////////////////////////////////////////
  // TASK                                                                    //
  /////////////////////////////////////////////////////////////////////////////

  /**
   * Adds a new task.
   * 
   * @param task - the task.
   * 
   * @return the identifier.
   */
  public ObjectId addTask(Task task);
  
  /**
   * Updates an existing task.
   * 
   * @param task - the task.
   */
  public void updateTask(Task task);
  
  /**
   * Returns a given task by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the task.
   */
  public Task getTask(ObjectId id);
  
  /**
   * Returns all tasks.
   * 
   * @return the tasks.
   */
  public List<Task> getTasks();
  
  /**
   * Deletes a given task by its identifier.
   * 
   * @param id - the identifier.
   */
  public void deleteTask(ObjectId id);
  
  /////////////////////////////////////////////////////////////////////////////
  // NOTE                                                                    //
  /////////////////////////////////////////////////////////////////////////////

  /**
   * Adds a new note for a task.
   * 
   * @param taskId - the task identifier.
   * 
   * @param note - the note.
   * 
   * @return the identifier.
   */
  public ObjectId addNote(ObjectId taskId, Note note);
  
  /**
   * Updates an existing note.
   * 
   * @param note - the note.
   */
  public void updateNote(Note note);
  
  /**
   * Returns a given note by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the note.
   */
  public Note getNote(ObjectId id);
  
  /**
   * Returns all notes for a task.
   * 
   * @return the notes.
   */
  public List<Note> getNotes(ObjectId taskId);
  
  /**
   * Deletes a given note by its identifier.
   * 
   * @param id - the identifier.
   */
  public void deleteNote(ObjectId id);
  
  /////////////////////////////////////////////////////////////////////////////
  // LINK                                                                    //
  /////////////////////////////////////////////////////////////////////////////

  /**
   * Adds a new link for a task.
   * 
   * @param taskId - the task identifier.
   * 
   * @param link - the link.
   * 
   * @return the identifier.
   */
  public ObjectId addLink(ObjectId taskId, Link link);
  
  /**
   * Updates an existing link.
   * 
   * @param link - the link.
   */
  public void updateLink(Link link);
  
  /**
   * Returns a given link by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the link.
   */
  public Link getLink(ObjectId id);
  
  /**
   * Returns all links for a task.
   * 
   * @return the links.
   */
  public List<Link> getLinks(ObjectId taskId);
  
  /**
   * Deletes a given link by its identifier.
   * 
   * @param id - the identifier.
   */
  public void deleteLink(ObjectId id);
}
