package org.taskstodo.test;

import java.util.List;

import javax.annotation.Resource;

import org.bson.types.ObjectId;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.taskstodo.model.User;
import org.taskstodo.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"/application-context.xml"})
public class UserTest {

  @Resource
  private UserService userService;
  
  private static ObjectId userId;
  
  @Before
  public void prepare() {
    create();
  }

  @After
  public void cleanup() {
    delete();
  }

  @Test
  public void getUser() {
    // Load the user
    User user = userService.getUser(userId);
    Assert.assertNotNull(user);
    Assert.assertEquals("daniel.backhausen@eightbit.de", user.getUsername());
    Assert.assertEquals("test123", user.getPassword());
  }
  
  @Test
  public void getAllUsers() {
    List<User> users = userService.getUsers();
    Assert.assertNotNull(users);
    Assert.assertTrue(users.size() > 0);
  }
  
  @Test
  public void updateUser() {
    // Load the user
    User user = userService.getUser(userId);
    Assert.assertNotNull(user);
    Assert.assertEquals("daniel.backhausen@eightbit.de", user.getUsername());
    Assert.assertEquals("test123", user.getPassword());
    
    // Update the user
    user.setUsername("daniel.backhausen@eightbit.eu");
    user.setPassword("123test456");
    userService.updateUser(user);
    
    // Load the user
    user = null;
    user = userService.getUser(userId);
    Assert.assertNotNull(user);
    Assert.assertEquals("daniel.backhausen@eightbit.eu", user.getUsername());
    Assert.assertEquals("123test456", user.getPassword());
  }
  
  // --
  
  private void create() {
    User user = new User();
    user.setUsername("daniel.backhausen@eightbit.de");
    user.setPassword("test123");
    userId = userService.addUser(user);
  }
  
  private void delete() {
    userService.deleteUser(userId);
  }
} 