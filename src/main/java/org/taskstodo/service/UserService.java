package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.exception.ServiceException;
import org.taskstodo.model.User;

public interface UserService {
  /**
   * Adds a new user.
   * 
   * @param user - the user.
   * 
   * @return the identifier.
   */
  public ObjectId addUser(User user) throws ServiceException;
  
  /**
   * Updates an existing user.
   * 
   * @param user - the user.
   */
  public void updateUser(User user) throws ServiceException;
  
  /**
   * Returns a given user by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the user.
   */
  public User getUser(ObjectId id);
  
  /**
   * Returns a given user by its username.
   * 
   * @param username - the username.
   * 
   * @return the user.
   */
  public User getUserByUsername(String username);
  
  /**
   * Returns all users.
   * 
   * @return the users.
   */
  public List<User> getUsers();
  
  /**
   * Deletes a given user by its identifier.
   * 
   * @param id - the identifier.
   */
  public void deleteUser(ObjectId id);
  
  /**
   * Verifies the login using the users credentials.
   * 
   * @param username - the username.
   * @param password - the password.
   * 
   * @return true, if authentication was successful.
   */
  public boolean verifyLogin(String username, String password);
}
