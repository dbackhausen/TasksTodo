package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort.Direction;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.Goal;

public interface GoalService {
  /**
   * Adds a new goal.
   * 
   * @param goal - the goal.
   * 
   * @return the identifier.
   */
  public ObjectId addGoal(Goal goal) throws ServiceException;
  
  /**
   * Updates an existing goal.
   * 
   * @param goal - the goal.
   */
  public void updateGoal(Goal goal) throws ServiceException;
  
  /**
   * Returns a given goal by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the goal.
   */
  public Goal getGoal(ObjectId id);
  
  /**
   * Returns a given goal by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the goal.
   */
  public Goal getGoal(String id);
  
  /**
   * Returns all goals.
   * 
   * @param userId - the user identifier.
   *
   * @return the goals.
   */
  public List<Goal> getGoals(ObjectId userId);
  
  /**
   * Returns all goals ordered by attribute (asc or desc).
   * 
   * @param userId - the user identifier.
   * @param field - the order field.
   * @param direction - the direction order.
   * @return the goals.
   */
  public List<Goal> getGoalsOrderedBy(ObjectId userId, String field, Direction direction);
  
  /**
   * Deletes a given goal by its identifier.
   * 
   * @param id - the identifier.
   * @param cascade - cascade delete all subgoals
   */
  public void deleteGoal(ObjectId id, boolean cascade);
  
  /**
   * Deletes a given goal by its identifier.
   * 
   * @param id - the identifier.
   * @param cascade - cascade delete all subgoals
   */
  public void deleteGoal(String id, boolean cascade);
  
  /**
   * Reorganizes all user goals when a position of one goal has been changed.
   * 
   * @param goal - the goal, which has been changed.
   * @param prevPos - the previous position of the goal.
   * @param userId - the id of the user.
   * @throws ServiceException - Exception if save operation throws errors
   */
  public void reorganize(Goal goal, int prevPos, ObjectId userId) throws ServiceException;
}
