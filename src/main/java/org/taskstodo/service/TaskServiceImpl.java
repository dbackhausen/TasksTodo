package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.taskstodo.dao.BookmarkDAO;
import org.taskstodo.dao.HistoryDAO;
import org.taskstodo.dao.NoteDAO;
import org.taskstodo.dao.TabDAO;
import org.taskstodo.dao.TaskDAO;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.Bookmark;
import org.taskstodo.model.History;
import org.taskstodo.model.Note;
import org.taskstodo.model.Tab;
import org.taskstodo.model.Task;

@Service
public class TaskServiceImpl implements TaskService {
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
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateTask(org.taskstodo.model.Task)
   */
  public void updateTask(Task task) throws ServiceException {
    if (task != null && task.getGoalId() != null) {
      taskDAO.update(task);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTask(org.bson.types.ObjectId)
   */
  public Task getTask(ObjectId id) {
    return taskDAO.findById(id);
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
  public List<Task> getTasksByGoal(ObjectId goalId) {
    return taskDAO.findAllByGoal(goalId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTasksByGoal(java.lang.String)
   */
  public List<Task> getTasksByGoal(String goalId) {
    return getTasksByGoal(new ObjectId(goalId));
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTasksOrderedBy(java.lang.String, org.springframework.data.domain.Sort.Direction)
   */
  public List<Task> getTasksOrderedBy(String field, Direction direction) {
    return taskDAO.findAll(new Sort(new Order(direction, "field")));
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getSubTasks(org.bson.types.ObjectId)
   */
  public List<Task> getSubTasks(ObjectId parentId) {
    return taskDAO.findSubTasks(parentId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getSubTasks(java.lang.String)
   */
  @Override
  public List<Task> getSubTasks(String parentId) {
    return getSubTasks(new ObjectId(parentId));
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getSubTaskCount(org.bson.types.ObjectId)
   */
  public int getSubTaskCount(ObjectId parentId) {
    List<Task> subTasks = getSubTasks(parentId);
    return subTasks != null ? subTasks.size() : 0;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getSubTaskCount(java.lang.String)
   */
  @Override
  public int getSubTaskCount(String parentId) {
    return getSubTaskCount(new ObjectId(parentId));
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
      
      if (cascade) {
        List<Task> subtasks = getSubTasks(id);
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
  public void deleteTasksByGoal(ObjectId id) {
    List<Task> tasks = getTasksByGoal(id);
    
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
   * @see org.taskstodo.service.TaskService#addHistory(org.bson.types.ObjectId, org.taskstodo.model.History)
   */
  @Override
  public ObjectId addHistory(History history) throws ServiceException {
    if (history != null && history.getTaskId() != null) {
      return historyDAO.create(history);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateHistory(org.taskstodo.model.History)
   */
  @Override
  public void updateHistory(History history) throws ServiceException {
    if (history != null && history.getTaskId() != null) {
      historyDAO.update(history);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent task!");
    }
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getHistory(org.bson.types.ObjectId)
   */
  @Override
  public History getHistory(ObjectId id) {
    return historyDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getHistories(org.bson.types.ObjectId)
   */
  @Override
  public List<History> getHistories(ObjectId taskId) {
    return historyDAO.findByTask(taskId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteHistory(org.bson.types.ObjectId)
   */
  @Override
  public void deleteHistory(ObjectId id) {
    historyDAO.delete(id);
  }
}
