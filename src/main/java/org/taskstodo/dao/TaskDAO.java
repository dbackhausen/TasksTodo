package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.taskstodo.model.Task;

public interface TaskDAO extends GenericDao<Task> {
  /**
   * Returns all sub tasks of a given parent.
   * 
   * @param parentId - the ID of the parent task.
   * @return the sub tasks.
   */
  List<Task> findSubTasks(final ObjectId parentId);

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
}
