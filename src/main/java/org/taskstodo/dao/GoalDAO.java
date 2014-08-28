package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.taskstodo.model.Goal;

public interface GoalDAO extends GenericDao<Goal> {
  /**
   * Returns all entities.
   * 
   * @param userId - the user identifier.
   * 
   * @return all goals.
   */
  public List<Goal> findAll(ObjectId userId);

  /**
   * Returns all entities sorted by a given option.
   * 
   * @param userId - the user identifier.
   * @param sort - the sort order.
   * 
   * @return all goals.
   */
  public List<Goal> findAll(ObjectId userId, Sort sort);
}
