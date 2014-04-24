package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort.Direction;
import org.taskstodo.model.Goal;

public interface GoalService {
  /**
   * Adds a new goal.
   * 
   * @param goal - the goal.
   * 
   * @return the identifier.
   */
  public ObjectId addGoal(Goal goal);
  
  /**
   * Updates an existing goal.
   * 
   * @param goal - the goal.
   */
  public void updateGoal(Goal goal);
  
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
   * @return the goals.
   */
  public List<Goal> getGoals();
  
  /**
   * Returns all goals ordered by attribute (asc or desc).
   * 
   * @param field - the order field.
   * @param direction - the direction order.
   * @return the goals.
   */
  public List<Goal> getGoalsOrderedBy(String field, Direction direction);
  
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
}
