package org.taskstodo.dao;

import org.springframework.stereotype.Repository;
import org.taskstodo.model.Task;

@Repository
public class TaskDAOImpl extends GenericDaoImpl<Task> implements TaskDAO {
  public TaskDAOImpl() {
    super(Task.class);
  }
}
