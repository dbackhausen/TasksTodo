package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.taskstodo.model.Task;

public interface TaskDAO extends GenericDao<Task> {
  /**
   * Returns all tasks by its goal.
   * 
   * @param goalId - the goal ID.
   * @return the tasks.
   */
  List<Task> findAll(final ObjectId goalId);
  
  /**
   * Returns all entities sorted by a given option.
   * 
   * @param goalId - the goal ID.
   * @param sort - the sort order.
   * 
   * @return all tasks.
   */
  List<Task> findAll(final ObjectId goalId, Sort sort);
  
  /**
   * Returns all completed tasks of a given goal sorted by completion date.
   * 
   * @param goalId - the ID of the goal.
   * @return the tasks.
   */
  List<Task> findAllCompleted(final ObjectId goalId);
}
