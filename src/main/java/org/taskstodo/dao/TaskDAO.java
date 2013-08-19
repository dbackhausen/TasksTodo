package org.taskstodo.dao;

import java.util.List;

import org.taskstodo.model.Task;

public interface TaskDAO {
  public void addTask(Task task);
  public void updateTask(Task task);
  public Task getTask(int id);
  public List<Task> getTasks();
  public void deleteTask(int id);
}
