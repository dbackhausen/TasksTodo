package org.taskstodo.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.taskstodo.model.Task;

@Repository
public class TaskDAOImpl implements TaskDAO {
  @Autowired
  private SessionFactory sessionFactory;

  private Session getCurrentSession() {
    return sessionFactory.getCurrentSession();
  }

  public void addTask(Task task) {
    getCurrentSession().save(task);
  }

  public void updateTask(Task task) {
    Task taskToUpdate = getTask(task.getId());
    taskToUpdate.setTitle(task.getTitle());
    taskToUpdate.setDescription(task.getDescription());
    getCurrentSession().update(taskToUpdate);
  }

  public Task getTask(int id) {
    Task task = (Task) getCurrentSession().get(Task.class, id);
    return task;
  }

  @SuppressWarnings("unchecked")
  public List<Task> getTasks() {
    return getCurrentSession().createQuery("from Task").list();
  }

  public void deleteTask(int id) {
    Task task = getTask(id);
    
    if (task != null) {
      getCurrentSession().delete(task);
    }
  }
}
