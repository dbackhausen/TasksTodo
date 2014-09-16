package org.taskstodo.service;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);
  
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
  public void deleteTasksByGoal(ObjectId goalId) {
    List<Task> tasks = getTasks(goalId);
    
    for (Task task : tasks) {
      deleteTask(task.getId(), true);
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#reorganizeByLevel(java.util.List, org.taskstodo.model.Task, int)
   */
  public void reorganizeByLevel(Task task, int prevLevel) throws ServiceException {
    List<Task> tasks = getTasksOrderedBy(task.getGoalId(), "position", Direction.ASC);
    
    if (task != null && task.getPosition() > 1) {
      boolean indent = task.getLevel() > prevLevel;
      
      for (int i = task.getPosition() - 2; i >= 0; i--) {
        Task t = tasks.get(i);
        
        if (indent) {
          LOGGER.info("Indent task " +  task.getTitle() + " from level " + prevLevel + " to " + task.getLevel());
                    
          if (t.getLevel() < task.getLevel()) {
            if (task.getLevel() - t.getLevel() == 1) {
              // Set this task as parent
              task.setParentId(t.getId());
              updateTask(task);
              
              // Check if current task has subtasks
              List<Task> subtasks = getSubTasks(task.getId());
              
              // Indent the subtasks
              for (Task subtask : subtasks) {
                subtask.setLevel(subtask.getLevel() + 1);
                updateTask(subtask);
              }

              return;
            }
          }
        } else {
          LOGGER.info("Indent task from level " + prevLevel + " to " + task.getLevel());

          if (task.getLevel() == 0) {
            // Reset parent, because level is 0
            task.setParentId(null);
            updateTask(task);
            
            // Check if current task has subtasks
            List<Task> subtasks = getSubTasks(task.getId());
            
            // Outdent the subtasks
            for (Task subtask : subtasks) {
              subtask.setLevel(subtask.getLevel() - 1);
              updateTask(subtask);
            }
            
            return;
          } else {
            if (t.getLevel() < task.getLevel()) {
              LOGGER.info("Task " + t.getTitle() + " will be the new parent of " + task.getLevel());

              // Set this task as parent
              task.setParentId(t.getId());
              updateTask(task);
              
              // Check if current task has subtasks
              List<Task> subtasks = getSubTasks(task.getId());
              
              // Outdent the subtasks
              for (Task subtask : subtasks) {
                subtask.setLevel(subtask.getLevel() - 1);
                updateTask(subtask);
              }
              
              return;
            }
          }  
        }
      }
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#reorganizeByPosition(java.util.List, org.taskstodo.model.Task, int)
   */
  public void reorganizeByPosition(Task task, int prevPosition) throws ServiceException {
    if (task != null && task.getPosition() != prevPosition) {
      // Load all tasks
      List<Task> tasks = getTasksOrderedBy(task.getGoalId(), "position", Direction.ASC);

      reposition(tasks, prevPosition, task.getPosition());
      
//      for (Task t : tasks) {
//        if (StringUtils.equals(t.getIdAsString(), task.getIdAsString())) {
//          reposition(tasks, prevPosition, task.getPosition());
//        } else if (StringUtils.equals(t.getParentIdAsString(), task.getIdAsString())) {
//          reposition(tasks, t.getPosition(), task.getPosition() + 1);
//        }
//      }
      
      // Check for list modifications
      for (int i = 0; i < tasks.size(); i++) {
        Task t = tasks.get(i);
        
//        if (StringUtils.equals(t.getIdAsString(), task.getIdAsString())) {
//          tasks = reposition(tasks, prevPosition, task.getPosition());
//        } else if (StringUtils.equals(t.getParentIdAsString(), task.getIdAsString())) {
//          tasks = reposition(tasks, t.getPosition(), task.getPosition() + 1);
//        }
        
        if (StringUtils.equals(t.getId().toString(), task.getId().toString())) {
          if (task.getPosition() > 1) {
          Task predecessor = tasks.get(i - 1);
          
            // Check if predecessor has subtasks
            if (getSubTaskCount(predecessor.getId()) > 0) {
              LOGGER.info("Make " + predecessor.getTitle() + " as parent");
              // Make predecessor as parent of current task
              task.setParentId(predecessor.getId());
              task.setLevel(predecessor.getLevel() + 1);
            } else {
              // Check if predecessor has a parent
              if (predecessor.getParentId() != null) {
                LOGGER.info("Takeover parent and level from " + predecessor.getTitle());
                // Takeover the parent from predecessor
                task.setParentId(predecessor.getParentId());
                task.setLevel(predecessor.getLevel());
              } else {
                LOGGER.info("Reset parent and task level to 0 for task " + task.getTitle());
                // If task has been moved to the first position,
                // make sure it has no parent task or level > 0.
                task.setParentId(null);
                task.setLevel(0);
              }
            }
          } else {
            LOGGER.info("Reset parent and task level to 0 for task " + task.getTitle());
            // If task has been moved to the first position,
            // make sure it has no parent task or level > 0.
            task.setParentId(null);
            task.setLevel(0);
          }
          
          // Save task
          updateTask(task);        
        } else {
          if (i != t.getPosition() - 1) {
            // Set the correct position
            t.setPosition(i + 1);
            // Save task
            updateTask(t);
          }
        }
      }
      
//      // Check if the task has subtasks
//      List<Task> subtasks = getSubTasks(task.getId());
//      
//      for (Task subtask : subtasks) {
//        prevPosition = subtask.getPosition();
//        subtask.setPosition(task.getPosition() + 1);
//        reorganizeByPosition(subtask, prevPosition);
//      }
    }
      
    
//    // First save changes to the current task
//    updateTask(task);
//    
//    // Load all tasks
//    List<Task> tasks = getTasksOrderedBy(task.getGoalId(), "position", Direction.ASC);
//    
//    int position = task.getPosition();
//    int level = task.getLevel();
//    
//    for (Task t : tasks) {
//      if (!t.getId().toString().equals(task.getId().toString())) {
//        if (task.getPosition() < prevPosition) { // task has been moved to a higher position
//          if (t.getPosition() >= task.getPosition()) {
//            position++;
//            t.setPosition(position);
//            updateTask(t);
//          }
//        } else { // task has been moved to a lower position
//          if (t.getPosition() > prevPosition && t.getPosition() <= task.getPosition()) {
//            t.setPosition(t.getPosition() - 1);
//            updateTask(t);
//          }
//        }
//      }
//    }
//
//    // Reload tasks after repositioning
//    tasks = getTasksOrderedBy(task.getGoalId(), "position", Direction.ASC);
//        
//    if (task.getPosition() > 1) {
//      Task predecessor = tasks.get(task.getPosition() - 2);
//      
//      // Check if predecessor has subtasks
//      if (getSubTaskCount(predecessor.getId()) > 0) {
//        LOGGER.info("Make " + predecessor.getTitle() + " as parent");
//        // Make predecessor as parent of current task
//        task.setParentId(predecessor.getId());
//        task.setLevel(predecessor.getLevel() + 1);
//      } else {
//        // Check if predecessor has a parent
//        if (predecessor.getParentId() != null) {
//          LOGGER.info("Takeover parent and level from " + predecessor.getTitle());
//          // Takeover the parent from predecessor
//          task.setParentId(predecessor.getParentId());
//          task.setLevel(predecessor.getLevel());
//        } else {
//          LOGGER.info("Reset parent and task level to 0 for task " + task.getTitle());
//          // If task has been moved to the first position,
//          // make sure it has no parent task or level > 0.
//          task.setParentId(null);
//          task.setLevel(0);
//        }
//      }
//    } else {
//      LOGGER.info("Reset parent and task level to 0 for task " + task.getTitle());
//      // If task has been moved to the first position,
//      // make sure it has no parent task or level > 0.
//      task.setParentId(null);
//      task.setLevel(0);
//    }
//
//    // Save the task again
//    updateTask(task);
//    
//    // Reorganize subtasks
//    reorganizeSubtasks(task);
  }
  
  private List<Task> reposition(List<Task> tasks, int from, int to) {
    if (from > 0 && from <= tasks.size()) {
      // Get task from list      
      Task task = tasks.get(from - 1);
      
      if (task != null) {
        // Remove current task
        tasks.remove(from - 1);
        // Add task to the right position
        tasks.add(to - 1, task);
        
        LOGGER.info("Moved task " + task.getTitle() + " from " + from + " to " + to);
      }
    }
    
    return tasks;
  }
  
  private void reorganizeSubtasks(Task task) throws ServiceException {
    // Check if the task has subtasks
    List<Task> subtasks = getSubTasks(task.getId());
    
    // Reorganize the sub tasks also
    int position = task.getPosition() + 1;
    int level = task.getLevel() + 1;
    
    for (Task subtask : subtasks) {
      subtask.setPosition(position++);
      subtask.setLevel(level);
      updateTask(subtask);
      reorganizeSubtasks(subtask);
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
