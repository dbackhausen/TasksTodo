package org.taskstodo.controller;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.taskstodo.exception.GoalNotFoundException;
import org.taskstodo.model.Goal;
import org.taskstodo.service.GoalService;

@Controller
@RequestMapping(value = "/goals")
public class GoalController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(GoalController.class);
  
  @Autowired
  private GoalService goalService;

  // --
  
  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Goal addGoal(@RequestBody Goal goal) {
    if (goal != null) {
      try {
        ObjectId id = goalService.addGoal(goal); 
        return goalService.getGoal(id);
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/update/", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Goal updateGoal(@RequestBody Goal goal) {
    if (goal != null) {
      try {
        Goal g = goalService.getGoal(goal.getId());
        
        if (g != null) {
          g.setTitle(goal.getTitle());
          g.setDescription(goal.getDescription());
          g.setUserId(goal.getUserId());
          g.setUrgency(goal.getUrgency());
          g.setPriority(goal.getPriority());
          g.setLevel(goal.getLevel());
          g.setPosition(goal.getPosition());
          g.setCompleted(goal.isCompleted());
          g.setDeleted(goal.isDeleted());
          
          goalService.updateGoal(g);
  
          return goalService.getGoal(goal.getId());
        } else {
          throw new GoalNotFoundException();
        }
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    }
    
    return null;
  }

  @RequestMapping(value = "/api/delete/{goalId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteGoal(@PathVariable ObjectId goalId) {
    goalService.deleteGoal(goalId, true);
  }

  @RequestMapping(value = "/api/delete/{goalId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteGoalByPost(@PathVariable ObjectId goalId) {
    goalService.deleteGoal(goalId, true);
  }
  
  @RequestMapping(value = "/api/read/{goalId}", method = RequestMethod.GET)
  public @ResponseBody Goal getGoal(@PathVariable("goalId") ObjectId goalId) {
    return goalService.getGoal(goalId);
  }
  
  @RequestMapping(value = "/api/list/{userId}", method = RequestMethod.GET)
  public @ResponseBody List<Goal> getAllGoals(@PathVariable("userId") ObjectId userId) {
    return goalService.getGoalsOrderedBy(userId, "position", Direction.ASC);
  }
  
  @RequestMapping(value = "/api/list/completed/{userId}", method = RequestMethod.GET)
  public @ResponseBody List<Goal> getAllCompletedGoals(@PathVariable("userId") ObjectId userId) {
    return goalService.getCompletedGoals(userId);
  }
}