package org.taskstodo.controller;

import java.util.List;

import org.bson.types.ObjectId;
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
import org.taskstodo.model.Goal;
import org.taskstodo.service.GoalService;

@Controller
@RequestMapping(value = "/goals")
public class GoalController {
  @Autowired
  private GoalService goalService;

  // --
  
  @RequestMapping(value = "/api/create", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Goal addGoal(@PathVariable ObjectId goalId, @RequestBody Goal goal) {
    ObjectId id = goalService.addGoal(goal);
    return goalService.getGoal(id);
  }
  
  @RequestMapping(value = "/api/update/{goalId}", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody Goal updateGoal(@PathVariable ObjectId goalId, @RequestBody Goal goal) {
    Goal g = goalService.getGoal(goalId);
    g.setTitle(goal.getTitle());
    g.setDescription(goal.getDescription());
    goalService.updateGoal(g);
    
    return goalService.getGoal(goalId);
  }

  @RequestMapping(value = "/api/delete/{goalId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteGoal(@PathVariable ObjectId goalId) {
    goalService.deleteGoal(goalId, true);
  }
  
  
  @RequestMapping(value = "/api/read/{goalId}", method = RequestMethod.GET)
  public @ResponseBody Goal getGoal(@PathVariable("goalId") ObjectId goalId) {
    return goalService.getGoal(goalId);
  }
  
  @RequestMapping(value = "/api/list/", method = RequestMethod.GET)
  public @ResponseBody List<Goal> getAllGoals() {
    return goalService.getGoalsOrderedBy("modified", Direction.DESC);
  }
}