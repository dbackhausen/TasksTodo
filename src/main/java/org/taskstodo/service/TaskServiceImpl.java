package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.taskstodo.dao.LinkDAO;
import org.taskstodo.dao.NoteDAO;
import org.taskstodo.dao.TaskDAO;
import org.taskstodo.model.Link;
import org.taskstodo.model.Note;
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
  public ObjectId addTask(Task task) {
    return taskDAO.create(task);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addSubTask(org.bson.types.ObjectId, org.taskstodo.model.Task)
   */
  public ObjectId addSubTask(ObjectId parentId, Task subTask) {
    Task parent = taskDAO.findById(parentId);
    if (parent != null) {
      int counter = getSubTaskCount(parentId) + 1;
      subTask.setGoal(parent.getGoal());
      subTask.setParentTask(parentId);
      subTask.setPosition(counter);
      return addTask(subTask);
    }
    
    return null;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addSubTask(java.lang.String, org.taskstodo.model.Task)
   */
  public ObjectId addSubTask(String parentId, Task subTask) {
    return addSubTask(new ObjectId(parentId), subTask); 
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateTask(org.taskstodo.model.Task)
   */
  public void updateTask(Task task) {
    taskDAO.update(task);
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
   * @see org.taskstodo.service.TaskService#getTasksAscOrderBy(java.lang.String)
   */
  public List<Task> getTasksAscOrderBy(String field) {
    return taskDAO.findAll(new Sort(new Order(Sort.Direction.ASC, "field")));
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
      // remove all task links
      linkDAO.deleteAllByTask(id);
      
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
  
  /////////////////////////////////////////////////////////////////////////////
  // NOTE                                                                    //
  /////////////////////////////////////////////////////////////////////////////
  
  @Autowired
  private NoteDAO noteDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addNote(org.bson.types.ObjectId, org.taskstodo.model.Note)
   */
  @Override
  public ObjectId addNote(ObjectId taskId, Note note) {
    note.setTaskId(taskId);
    return noteDAO.create(note);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateNote(org.taskstodo.model.Note)
   */
  @Override
  public void updateNote(Note note) {
    noteDAO.update(note);
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
  // LINK                                                                    //
  /////////////////////////////////////////////////////////////////////////////
  
  @Autowired
  private LinkDAO linkDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#addLink(org.bson.types.ObjectId, org.taskstodo.model.Link)
   */
  @Override
  public ObjectId addLink(ObjectId taskId, Link link) {
    link.setTaskId(taskId);
    return linkDAO.create(link);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#updateLink(org.taskstodo.model.Link)
   */
  @Override
  public void updateLink(Link link) {
    linkDAO.update(link);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getLink(org.bson.types.ObjectId)
   */
  @Override
  public Link getLink(ObjectId id) {
    return linkDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getLinks(org.bson.types.ObjectId)
   */
  @Override
  public List<Link> getLinks(ObjectId taskId) {
    return linkDAO.findByTask(taskId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteLink(org.bson.types.ObjectId)
   */
  @Override
  public void deleteLink(ObjectId id) {
    linkDAO.delete(id);
  }
}
