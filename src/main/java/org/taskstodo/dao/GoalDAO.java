package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.taskstodo.model.Goal;

public interface GoalDAO extends GenericDao<Goal> {
  /**
   * Returns all goals of a certain user.
   * 
   * @param userId - the user identifier.
   * 
   * @return all goals.
   */
  List<Goal> findAll(ObjectId userId);

  /**
   * Returns all goals of a certain user sorted by a given option.
   * 
   * @param userId - the user identifier.
   * @param sort - the sort order.
   * 
   * @return all goals.
   */
  List<Goal> findAll(ObjectId userId, Sort sort);
  
  /**
   * Returns all completed goals of a given user sorted by completion date.
   * 
   * @param userId - the user identifier.
   * @return the goals.
   */
  List<Goal> findAllCompleted(final ObjectId userId);
}
