package org.taskstodo.service;

import java.util.List;

import org.taskstodo.model.Task;

public interface TaskService {
  public void addTask(Task task);
  public void updateTask(Task task);
  public Task getTask(int id);
  public void deleteTask(int id);
  public List<Task> getTasks();
}
