package org.taskstodo.dao;

import org.taskstodo.model.User;

public interface UserDAO extends GenericDao<User> {
  /**
   * Finds an existing user by its username.
   * 
   * @param username - the username.
   * 
   * @return the entity.
   */
  public User findByUsername(String username);
}
