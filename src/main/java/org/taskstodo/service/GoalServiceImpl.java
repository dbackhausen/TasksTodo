package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.taskstodo.dao.GoalDAO;
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
  public ObjectId addGoal(Goal goal) {
    return goalDAO.create(goal);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#updateGoal(org.taskstodo.model.Goal)
   */
  public void updateGoal(Goal goal) {
    goalDAO.update(goal);
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
  public List<Goal> getGoals() {
    return goalDAO.findAll();
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.GoalService#getGoals(java.lang.String, org.springframework.data.domain.Sort.Direction)
   */
  @Override
  public List<Goal> getGoalsOrderedBy(String field, Direction direction) {
    return goalDAO.findAll(new Sort(new Order(direction, field)));
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
}
