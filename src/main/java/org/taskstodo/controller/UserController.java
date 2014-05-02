package org.taskstodo.controller;

import java.util.List;

import org.bson.types.ObjectId;
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
import org.taskstodo.model.User;
import org.taskstodo.service.UserService;

@Controller
@RequestMapping(value = "/notes")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private MessageSource messageSource;
  
  // --

  @RequestMapping(value = "/api/create/", method = RequestMethod.POST, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody User create(@RequestBody User user) {
    ObjectId id = userService.addUser(user);
    return userService.getUser(id);
  }
  
  @RequestMapping(value = "/api/update/{userId}", method = RequestMethod.PUT, 
      produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public @ResponseBody User update(@PathVariable ObjectId userId, @RequestBody User user) {
    User u = userService.getUser(userId);
    u.setUsername(user.getUsername());
    u.setPassword(user.getPassword());
    userService.updateUser(u);
    return userService.getUser(userId);
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
}