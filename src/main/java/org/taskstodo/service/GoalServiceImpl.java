package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.taskstodo.dao.GoalDAO;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.Goal;

@Service
public class GoalServiceImpl implements GoalService {
  @Autowired
  private GoalDAO goalDAO;
  
  @Autowired
  private TaskService taskService;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#addGoal(org.taskstodo.model.Goal)
   */
  public ObjectId addGoal(Goal goal) throws ServiceException {
    if (goal != null && goal.getUserId() != null) {
      return goalDAO.create(goal);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent object!");
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#updateGoal(org.taskstodo.model.Goal)
   */
  public void updateGoal(Goal goal) throws ServiceException {
    if (goal != null && goal.getUserId() != null) {
      goalDAO.update(goal);
    } else {
      throw new ServiceException("Invalid object! Object is null or has no reference to parent object!");
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#getGoal(org.bson.types.ObjectId)
   */
  public Goal getGoal(ObjectId id) {
    return goalDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#getGoal(java.lang.String)
   */
  @Override
  public Goal getGoal(String id) {
    return getGoal(new ObjectId(id));
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#getGoals()
   */
  public List<Goal> getGoals(ObjectId userId) {
    return goalDAO.findAll(userId);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#getGoalsOrderedBy(java.lang.String, java.lang.String, org.springframework.data.domain.Sort.Direction)
   */
  @Override
  public List<Goal> getGoalsOrderedBy(ObjectId userId, String field, Direction direction) {
    return goalDAO.findAll(userId, new Sort(new Order(direction, field)));
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#getCompletedGoals(org.bson.types.ObjectId)
   */
  public List<Goal> getCompletedGoals(ObjectId userId) {
    return goalDAO.findAllCompleted(userId);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#deleteGoal(org.bson.types.ObjectId, boolean)
   */
  public void deleteGoal(ObjectId id, boolean cascade) {
    if (id != null) {
      // Remove all tasks and their content
      taskService.deleteTasksByGoal(id);
      
      // Finally remove the goal
      goalDAO.delete(id);
    }
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#deleteGoal(java.lang.String, boolean)
   */
  @Override
  public void deleteGoal(String id, boolean cascade) {
    deleteGoal(new ObjectId(id), cascade);
  }
  
  // --

  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#reorganize(org.taskstodo.model.Goal, int, org.bson.types.ObjectId)
   */
  public void reorganize(Goal goal, int prevPos, ObjectId userId) throws ServiceException {
    List<Goal> goals = getGoalsOrderedBy(userId, "position", Direction.ASC);
    
    int position = goal.getPosition();
    
    for (Goal g : goals) {
      if (!g.getId().toString().equals(goal.getId().toString())) {
        if (goal.getPosition() < prevPos) { // goal has been moved to a higher position
          if (g.getPosition() >= goal.getPosition()) {
            position++;
            g.setPosition(position);
            updateGoal(g);
          }
        } else { // goal has been moved to a lower position
          if (g.getPosition() > prevPos && g.getPosition() <= goal.getPosition()) {
            g.setPosition(g.getPosition() - 1);
            updateGoal(g);
          }
        }
      }
    }
  }
}
