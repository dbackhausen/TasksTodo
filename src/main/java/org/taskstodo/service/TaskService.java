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
   * Adds a sub task to a parent task.
   * 
   * @param parentId - the parent ID.
   * @param subTask - the subtask.
   * 
   * @return the ID of the new subtask.
   */
  public ObjectId addSubTask(ObjectId parentId, Task subTask);
  
  /**
   * Adds a sub task to a parent task.
   * 
   * @param parentId - the parent ID.
   * @param subTask - the subtask.
   * 
   * @return the ID of the new subtask.
   */
  public ObjectId addSubTask(String parentId, Task subTask);
  
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
   * Returns a given task by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the task.
   */
  public Task getTask(String id);
  
  /**
   * Returns all tasks.
   * 
   * @return the tasks.
   */
  public List<Task> getTasks();
  
  /**
   * Returns all tasks by its goal.
   * 
   * @param goalId - the goal ID.
   * @return the tasks.
   */
  public List<Task> getTasksByGoal(ObjectId goalId);
  
  /**
   * Returns all tasks by its goal.
   * 
   * @param goalId - the goal ID.
   * @return the tasks.
   */
  public List<Task> getTasksByGoal(String goalId);
  
  /**
   * Returns all tasks.
   * 
   * @return the tasks.
   */
  public List<Task> getTasksAscOrderBy(String field);
  
  /**
   * Returns all sub tasks of a given task.
   * 
   * @param parentId - the parent ID.
   * @return the tasks.
   */
  public List<Task> getSubTasks(ObjectId parentId);
  
  /**
   * Returns all sub tasks of a given task.
   * 
   * @param parentId - the parent ID.
   * @return the tasks.
   */
  public List<Task> getSubTasks(String parentId);
  
  /**
   * Return the amount of existing subtasks.
   * 
   * @param parentId - the parent ID.
   * @return the number of subtasks.
   */
  public int getSubTaskCount(ObjectId parentId);
  
  /**
   * Return the amount of existing subtasks.
   * 
   * @param parentId - the parent ID.
   * @return the number of subtasks.
   */
  public int getSubTaskCount(String parentId);
  
  /**
   * Deletes a given task by its identifier.
   * 
   * @param id - the identifier.
   * @param cascade - cascade delete all subtasks
   */
  public void deleteTask(ObjectId id, boolean cascade);
  
  /**
   * Deletes a given task by its identifier.
   * 
   * @param id - the identifier.
   * @param cascade - cascade delete all subtasks
   */
  public void deleteTask(String id, boolean cascade);
  
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
