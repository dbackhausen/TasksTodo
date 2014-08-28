package org.taskstodo.test;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.Goal;
import org.taskstodo.service.GoalService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class GoalTest {
  @Resource
  private GoalService goalService;
  
  private static ObjectId goalId;

  @Before
  public void prepare() {
    createGoal();
  }
  
  @After
  public void cleanup() {
    deleteGoal();
  }
  
  @Test
  public void update() {
    // Check if the goal ID has been set before
    Assert.assertNotNull(goalId);
    
    // Load existing goal
    Goal goal = goalService.getGoal(goalId);
    Assert.assertNotNull(goal);
    
    // Modify some values
    goal.setDescription("Goal has been modified");
    
    try {
      // Update goal
      goalService.updateGoal(goal);
    } catch (ServiceException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
    
    // Load updated goal
    goal = null;
    goal = goalService.getGoal(goalId);
    Assert.assertNotNull(goal);
    Assert.assertEquals(goal.getTitle(), "JUnit Goal");
    Assert.assertEquals(goal.getDescription(), "Goal has been modified");
  }
  
  // --
  
  private void createGoal() {
    Goal goal = new Goal();
    goal.setTitle("JUnit Goal");
    goal.setDescription("Some test description ...");
    goal.setUserId(new ObjectId("53e29f78300462259c28fe84"));
    
    // Add the new goal
    try {
      goalId = goalService.addGoal(goal);
      Assert.assertNotNull(goalId);
    } catch (ServiceException e) {
      e.printStackTrace();
      Assert.assertTrue(false);
    }
    
    // Load created goal
    goal = null;
    goal = goalService.getGoal(goalId);
    Assert.assertNotNull(goal);
    Assert.assertEquals(goal.getTitle(), "JUnit Goal");
    Assert.assertEquals(goal.getDescription(), "Some test description ...");
  }
  
  private void deleteGoal() {
    // Check if the goal ID has been set before
    Assert.assertNotNull(goalId);
    
    // Delete the test goal
    goalService.deleteGoal(goalId, true);
    
    // Check if goal does not longer exist
    Goal goal = goalService.getGoal(goalId);
    Assert.assertNull(goal);
  }
} 