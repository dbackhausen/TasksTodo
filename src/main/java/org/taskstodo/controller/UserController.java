package org.taskstodo.controller;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.taskstodo.exception.InvalidUserException;
import org.taskstodo.exception.UserAlreadyExistsException;
import org.taskstodo.exception.UserNotFoundException;
import org.taskstodo.model.User;
import org.taskstodo.service.UserService;

@Controller
@RequestMapping(value = "/users")
public class UserController {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
  
  @Autowired
  private UserService userService;

  @Autowired
  private MessageSource messageSource;
  
  // --

  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody User create(@RequestBody User user) {
    if (user != null) {
      // Check if user exists
      User u = userService.getUserByUsername(user.getUsername());
      
      if (u == null) {
        try {
          // Create user
          ObjectId id = userService.addUser(user);
          return userService.getUser(id);
        } catch (Exception e) {
          LOGGER.error(e.getMessage(), e);
        }        
      } else {
        LOGGER.error("User " + user.getUsername() + " already exists!");
        throw new UserAlreadyExistsException();
      }
    } else {
      LOGGER.error("Invalid user submitted!");
      throw new InvalidUserException();
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/update/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody User update(@RequestBody User user) {
    if (user != null) {
      try {
        User u = userService.getUser(user.getId());
        u.setUsername(user.getUsername());
        u.setPassword(user.getPassword());
        userService.updateUser(u);
        
        return userService.getUser(user.getId());
      } catch (Exception e) {
        LOGGER.error(e.getMessage(), e);
      }
    } else {
      throw new InvalidUserException();
    }
    
    return null;
  }
  
  @RequestMapping(value = "/api/reset/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody User reset(@RequestBody String username) {
    if (username != null) {
      User u = userService.getUserByUsername(username);
      LOGGER.info("TODO Sending new password to " + username);
    } else {
      throw new InvalidUserException();
    }
    
    return null;
  }

  @RequestMapping(value = "/api/delete/{userId}", method = RequestMethod.DELETE)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void delete(@PathVariable ObjectId userId) {
    userService.deleteUser(userId);
  }
  
  @RequestMapping(value = "/api/delete/{userId}", method = RequestMethod.POST)
  public @ResponseStatus(value = HttpStatus.NO_CONTENT) void deleteByPost(@PathVariable ObjectId userId) {
    userService.deleteUser(userId);
  }
  
  @RequestMapping(value = "/api/list/", method = RequestMethod.GET)
  public @ResponseBody List<User> list() {
    List<User> users = userService.getUsers();
    return users;
  }
  
  @RequestMapping(value = "/api/login/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody User login(@RequestBody User user) {
    if (user != null) {
      User u = userService.getUserByUsername(user.getUsername());
      
      if (u != null && StringUtils.equals(user.getPassword(), u.getPassword())) {
        return u;
      } else {
        throw new UserNotFoundException();
      }
    } else {
      throw new UserNotFoundException();
    }
  }
}