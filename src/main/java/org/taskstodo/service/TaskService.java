package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort.Direction;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.Bookmark;
import org.taskstodo.model.History;
import org.taskstodo.model.Note;
import org.taskstodo.model.Tab;
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
  public ObjectId addTask(Task task) throws ServiceException;
  
  /**
   * Updates an existing task.
   * 
   * @param task - the task.
   */
  public void updateTask(Task task) throws ServiceException;
  
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
  public List<Task> getTasks(ObjectId goalId);
  
  /**
   * Returns all tasks by its goal.
   * 
   * @param goalId - the goal id.
   * @param field - the order field.
   * @param direction - the direction order.
   * @return the tasks.
   */
  public List<Task> getTasksOrderedBy(ObjectId goalId, String field, Direction direction);
  
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
  
  /**
   * Deletes all tasks of a given goal.
   * 
   * @param goalId - the goal Id.
   */
  public void deleteTasksByGoal(ObjectId goalId);
  
  /**
   * Reorganizes all tasks if level has been changed.
   * 
   * @param task - the task, which has been changed.
   * @param prevLevel - the previous level of the goal.
   * @throws ServiceException - Exception if save operation throws errors
   */
  public void reorganizeByLevel(Task task, int prevLevel) throws ServiceException;
   
  /**
   * Reorganizes all tasks if position has been changed.
   * 
   * @param task - the task, which has been changed.
   * @param prevPosition - the previous position of the goal.
   * @throws ServiceException - Exception if save operation throws errors
   */
  public void reorganizeByPosition(Task task, int prevPosition) throws ServiceException;
  
  
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
  public ObjectId addNote(Note note) throws ServiceException;
  
  /**
   * Updates an existing note.
   * 
   * @param note - the note.
   */
  public void updateNote(Note note) throws ServiceException;
  
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
  // BOOKMARKS                                                               //
  /////////////////////////////////////////////////////////////////////////////

  /**
   * Adds a new bookmark for a task.
   * 
   * @param taskId - the task identifier.
   * 
   * @param bookmark - the bookmark.
   * 
   * @return the identifier.
   */
  public ObjectId addBookmark(Bookmark bookmark) throws ServiceException;
  
  /**
   * Updates an existing bookmark.
   * 
   * @param bookmark - the bookmark.
   */
  public void updateBookmark(Bookmark bookmark) throws ServiceException;
  
  /**
   * Returns a given bookmark by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the bookmark.
   */
  public Bookmark getBookmark(ObjectId id);
  
  /**
   * Returns all bookmarks for a task.
   * 
   * @return the bookmarks.
   */
  public List<Bookmark> getBookmarks(ObjectId taskId);
  
  /**
   * Deletes a given bookmark by its identifier.
   * 
   * @param id - the identifier.
   */
  public void deleteBookmark(ObjectId id);

  /////////////////////////////////////////////////////////////////////////////
  // TABS                                                                    //
  /////////////////////////////////////////////////////////////////////////////

  /**
   * Adds a new tab for a task.
   * 
   * @param taskId - the task identifier.
   * 
   * @param tab - the tab.
   * 
   * @return the identifier.
   */
  public ObjectId addTab(Tab tab) throws ServiceException;
  
  /**
   * Updates an existing tab.
   * 
   * @param bookmark - the tab.
   */
  public void updateTab(Tab tab) throws ServiceException;
  
  /**
   * Returns a given tab by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the tab.
   */
  public Tab getTab(ObjectId id);
  
  /**
   * Returns all tabs for a task.
   * 
   * @return the tabs.
   */
  public List<Tab> getTabs(ObjectId taskId);
  
  /**
   * Deletes a given tab by its identifier.
   * 
   * @param id - the identifier.
   */
  public void deleteTab(ObjectId id);
  
  /////////////////////////////////////////////////////////////////////////////
  // HISTORY                                                                 //
  /////////////////////////////////////////////////////////////////////////////

  /**
   * Adds a new history for a task.
   * 
   * @param taskId - the task identifier.
   * 
   * @param bookmark - the history.
   * 
   * @return the identifier.
   */
  public ObjectId addHistory(History history) throws ServiceException;
  
  /**
   * Updates an existing history.
   * 
   * @param history - the history.
   */
  public void updateHistory(History history) throws ServiceException;
  
  /**
   * Returns a given history by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the history.
   */
  public History getHistory(ObjectId id);
  
  /**
   * Returns all history for a task.
   * 
   * @return the history.
   */
  public List<History> getHistories(ObjectId taskId);
  
  /**
   * Deletes a given history by its identifier.
   * 
   * @param id - the identifier.
   */
  public void deleteHistory(ObjectId id);
}
