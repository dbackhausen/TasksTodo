package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort.Direction;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.Bookmark;
import org.taskstodo.model.HistoryEntry;
import org.taskstodo.model.Note;
import org.taskstodo.model.Query;
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
   * Returns all finished tasks.
   * 
   * @param goalId - the goal ID.
   * @return the tasks.
   */
  public List<Task> getCompletedTasks(ObjectId goalId);

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
   * Adds a new history entry.
   * 
   * @param taskId - the task identifier.
   * 
   * @param entry - the history entry.
   * 
   * @return the identifier.
   */
  public ObjectId addHistoryEntry(HistoryEntry entry) throws ServiceException;
  
  /**
   * Updates an existing history entry.
   * 
   * @param entry - the history entry.
   */
  public void updateHistoryEntry(HistoryEntry entry) throws ServiceException;
  
  /**
   * Returns a given history entry by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the history entry.
   */
  public HistoryEntry getHistoryEntry(ObjectId id);
  
  /**
   * Returns all history for a task.
   * 
   * @return the history.
   */
  public List<HistoryEntry> getHistory(ObjectId taskId);
  
  /**
   * Deletes a given history entry by its identifier.
   * 
   * @param id - the identifier.
   */
  public void deleteHistoryEntry(ObjectId id);

  /////////////////////////////////////////////////////////////////////////////
  // QUERY                                                                   //
  /////////////////////////////////////////////////////////////////////////////
  
  /**
   * Adds a search query.
   * 
   * @param query - the search query.
   * 
   * @return the object id.
   * 
   * @throws ServiceException - Exception during entity creation.
   */
  public ObjectId addQuery(Query query) throws ServiceException;

  /**
   * Returns a certain query.
   * 
   * @param id - the id.
   * 
   * @return the query.
   */
  public Query getQuery(ObjectId id);

  /**
   * Returns a list of queries given a task id.
   * 
   * @param taskId - the task id.
   * 
   * @return the queries.
   */
  public List<Query> getQueriesByTask(ObjectId taskId);
  
  /**
   * Returns a list of queries given a task id and search engine.
   * 
   * @param taskId - the task id.
   * @param engine - the engine.
   * 
   * @return the queries.
   */
  public List<Query> getQueriesByTaskAndEngine(ObjectId taskId, String engine);

  /**
   * Deletes all queries given a certain task.
   * 
   * @param taskId - the task id.
   */
  public void deleteAllQueriesByTask(ObjectId taskId);
  
  /**
   * Deletes a specific query entry.
   * 
   * @param id - the id.
   */
  public void deleteQuery(ObjectId id);
}
