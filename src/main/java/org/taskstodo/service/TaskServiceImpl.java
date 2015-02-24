package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.taskstodo.dao.BookmarkDAO;
import org.taskstodo.dao.FileDAO;
import org.taskstodo.dao.HistoryDAO;
import org.taskstodo.dao.NoteDAO;
import org.taskstodo.dao.QueryDAO;
import org.taskstodo.dao.TabDAO;
import org.taskstodo.dao.TaskDAO;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.Bookmark;
import org.taskstodo.model.HistoryEntry;
import org.taskstodo.model.Note;
import org.taskstodo.model.Query;
import org.taskstodo.model.Tab;
import org.taskstodo.model.Task;

@Service
public class TaskServiceImpl implements TaskService {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);
  
  @Autowired
  private FileDAO fileDAO;
  
  
  /////////////////////////////////////////////////////////////////////////////
  // TASK                                                                    //
  /////////////////////////////////////////////////////////////////////////////
  
  @Autowired
  private TaskDAO taskDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addTask(org.taskstodo.model.Task)
   */
  public ObjectId addTask(Task task) throws ServiceException {
    if (task != null && task.getGoalId() != null) {
      return taskDAO.create(task);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent object!");
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateTask(org.taskstodo.model.Task)
   */
  public void updateTask(Task task) throws ServiceException {
    if (task != null && task.getGoalId() != null) {
      taskDAO.update(task);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent object!");
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTask(org.bson.types.ObjectId)
   */
  public Task getTask(ObjectId id) {
    Task task = taskDAO.findById(id);
    
    if (task != null) {
      task.setNotes(getNotes(task.getId()));
      task.setBookmarks(getBookmarks(task.getId()));
      task.setQueries(getQueriesByTask(task.getId()));
      task.setHistory(getHistory(task.getId()));
    }
    
    return task;
  }
  
  @Override
  public Task getTask(String id) {
    return getTask(new ObjectId(id));
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTasks()
   */
  public List<Task> getTasks() {
    return taskDAO.findAll();
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTasksByGoal(org.bson.types.ObjectId)
   */
  @Override
  public List<Task> getTasks(ObjectId goalId) {
    return taskDAO.findAll(goalId);
  }
    
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTasksOrderedBy(java.lang.String, org.springframework.data.domain.Sort.Direction)
   */
  public List<Task> getTasksOrderedBy(ObjectId goalId, String field, Direction direction) {
    return taskDAO.findAll(goalId, new Sort(new Order(direction, field)));
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getCompletedTasks(org.bson.types.ObjectId)
   */
  public List<Task> getCompletedTasks(ObjectId goalId) {
    return taskDAO.findAllCompleted(goalId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteTask(org.bson.types.ObjectId, boolean)
   */
  public void deleteTask(ObjectId id, boolean cascade) {
    if (id != null) {
      // remove all task notes
      noteDAO.deleteAllByTask(id);
      // remove all task bookmarks
      bookmarkDAO.deleteAllByTask(id);
      
      // remove all task history
      historyDAO.deleteAllByTask(id);
      
      // remove all task attachments
      fileDAO.deleteAllByTask(id);
      
      // remove all task queries
      queryDAO.deleteAllByTask(id);        
      
      if (cascade) {
        List<Task> subtasks = getCompletedTasks(id);
        for (Task subtask : subtasks) {
          deleteTask(subtask.getId(), true);
        }
      }
      
      // finally remove task
      taskDAO.delete(id);
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteTask(java.lang.String, boolean)
   */
  @Override
  public void deleteTask(String id, boolean cascade) {
    deleteTask(new ObjectId(id), cascade);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteTasksByGoal(org.bson.types.ObjectId)
   */
  @Override
  public void deleteTasksByGoal(ObjectId goalId) {
    List<Task> tasks = getTasks(goalId);
    
    for (Task task : tasks) {
      deleteTask(task.getId(), true);
    }
  }
  
  /////////////////////////////////////////////////////////////////////////////
  // NOTE                                                                    //
  /////////////////////////////////////////////////////////////////////////////
  
  @Autowired
  private NoteDAO noteDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addNote(org.bson.types.ObjectId, org.taskstodo.model.Note)
   */
  @Override
  public ObjectId addNote(Note note) throws ServiceException {
    if (note != null && note.getTaskId() != null) {
      return noteDAO.create(note);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateNote(org.taskstodo.model.Note)
   */
  @Override
  public void updateNote(Note note) throws ServiceException {
    if (note != null && note.getTaskId() != null) {
      noteDAO.update(note);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getNote(org.bson.types.ObjectId)
   */
  @Override
  public Note getNote(ObjectId id) {
    return noteDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getNotes(org.bson.types.ObjectId)
   */
  @Override
  public List<Note> getNotes(ObjectId taskId) {
    return noteDAO.findByTask(taskId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteNote(org.bson.types.ObjectId)
   */
  @Override
  public void deleteNote(ObjectId id) {
    noteDAO.delete(id);
  }

  /////////////////////////////////////////////////////////////////////////////
  // BOOKMARKS                                                               //
  /////////////////////////////////////////////////////////////////////////////
  
  @Autowired
  private BookmarkDAO bookmarkDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addBookmark(org.bson.types.ObjectId, org.taskstodo.model.Bookmark)
   */
  @Override
  public ObjectId addBookmark(Bookmark bookmark) throws ServiceException {
    if (bookmark != null && bookmark.getTaskId() != null) {
      return bookmarkDAO.create(bookmark);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateBookmark(org.taskstodo.model.Bookmark)
   */
  @Override
  public void updateBookmark(Bookmark bookmark) throws ServiceException {
    if (bookmark != null && bookmark.getTaskId() != null) {
      bookmarkDAO.update(bookmark);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getBookmark(org.bson.types.ObjectId)
   */
  @Override
  public Bookmark getBookmark(ObjectId id) {
    return bookmarkDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getBookmarks(org.bson.types.ObjectId)
   */
  @Override
  public List<Bookmark> getBookmarks(ObjectId taskId) {
    return bookmarkDAO.findByTask(taskId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteBookmark(org.bson.types.ObjectId)
   */
  @Override
  public void deleteBookmark(ObjectId id) {
    bookmarkDAO.delete(id);
  }

  /////////////////////////////////////////////////////////////////////////////
  // TABS                                                                    //
  /////////////////////////////////////////////////////////////////////////////
  
  @Autowired
  private TabDAO tabDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addTab(org.bson.types.ObjectId, org.taskstodo.model.Tab)
   */
  @Override
  public ObjectId addTab(Tab tab) throws ServiceException {
    if (tab != null && tab.getTaskId() != null) {
      return tabDAO.create(tab);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateTab(org.taskstodo.model.Tab)
   */
  @Override
  public void updateTab(Tab tab) throws ServiceException {
    if (tab != null && tab.getTaskId() != null) {
      tabDAO.update(tab);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTab(org.bson.types.ObjectId)
   */
  @Override
  public Tab getTab(ObjectId id) {
    return tabDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTabs(org.bson.types.ObjectId)
   */
  @Override
  public List<Tab> getTabs(ObjectId taskId) {
    return tabDAO.findByTask(taskId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteTab(org.bson.types.ObjectId)
   */
  @Override
  public void deleteTab(ObjectId id) {
    tabDAO.delete(id);
  }

  /////////////////////////////////////////////////////////////////////////////
  // HISTORY                                                                 //
  /////////////////////////////////////////////////////////////////////////////
  
  @Autowired
  private HistoryDAO historyDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addHistoryEntry(org.taskstodo.model.HistoryEntry)
   */
  @Override
  public ObjectId addHistoryEntry(HistoryEntry history) throws ServiceException {
    if (history != null && history.getTaskId() != null) {
      return historyDAO.create(history);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateHistoryEntry(org.taskstodo.model.HistoryEntry)
   */
  @Override
  public void updateHistoryEntry(HistoryEntry history) throws ServiceException {
    if (history != null && history.getTaskId() != null) {
      historyDAO.update(history);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getHistoryEntry(org.bson.types.ObjectId)
   */
  @Override
  public HistoryEntry getHistoryEntry(ObjectId id) {
    return historyDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getHistory(org.bson.types.ObjectId)
   */
  @Override
  public List<HistoryEntry> getHistory(ObjectId taskId) {
    return historyDAO.findByTask(taskId);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteHistoryEntry(org.bson.types.ObjectId)
   */
  @Override
  public void deleteHistoryEntry(ObjectId id) {
    historyDAO.delete(id);
  }
  
  /////////////////////////////////////////////////////////////////////////////
  // QUERY                                                                   //
  /////////////////////////////////////////////////////////////////////////////
  
  @Autowired
  private QueryDAO queryDAO;

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addQuery(org.taskstodo.model.Query)
   */
  @Override
  public ObjectId addQuery(Query query) throws ServiceException {
    if (query != null && query.getTaskId() != null) {
      return queryDAO.create(query);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getQuery(org.bson.types.ObjectId)
   */
  @Override
  public Query getQuery(ObjectId id) {
    return queryDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getQueriesByTask(org.bson.types.ObjectId)
   */
  @Override
  public List<Query> getQueriesByTask(ObjectId taskId) {
    return queryDAO.findByTask(taskId);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getQueriesByTaskAndEngine(org.bson.types.ObjectId, java.lang.String)
   */
  @Override
  public List<Query> getQueriesByTaskAndEngine(ObjectId taskId, String engine) {
    return queryDAO.findByTaskAndEngine(taskId, engine);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteAllQueriesByTask(org.bson.types.ObjectId)
   */
  @Override
  public void deleteAllQueriesByTask(ObjectId taskId) {
    queryDAO.deleteAllByTask(taskId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteQuery(org.bson.types.ObjectId)
   */
  @Override
  public void deleteQuery(ObjectId id) {
    queryDAO.delete(id);
  }
}
