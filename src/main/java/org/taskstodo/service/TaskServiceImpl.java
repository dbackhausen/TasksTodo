package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
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
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#getTasks()
   */
  public List<Task> getTasks() {
    return taskDAO.findAll();
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.TaskService#deleteTask(org.bson.types.ObjectId)
   */
  public void deleteTask(ObjectId id) {
    noteDAO.deleteAllByTask(id);
    linkDAO.deleteAllByTask(id);
    taskDAO.delete(id);
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
