package org.taskstodo.service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.taskstodo.dao.UserDAO;
import org.taskstodo.model.User;

@Service
public class UserServiceImpl implements UserService {
  @Autowired
  private UserDAO userDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.UserService#addUser(org.taskstodo.model.User)
   */
  public ObjectId addUser(User user) {
    return userDAO.create(user);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.UserService#updateUser(org.taskstodo.model.User)
   */
  public void updateUser(User user) {
    userDAO.update(user);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.service.UserService#getUser(java.lang.Integer)
   */
  public User getUser(ObjectId id) {
    return userDAO.findById(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.UserService#getUsers()
   */
  public List<User> getUsers() {
    return userDAO.findAll();
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.UserService#deleteUser(java.lang.Integer)
   */
  public void deleteUser(ObjectId id) {
    userDAO.delete(id);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.UserService#verifyLogin(java.lang.String, java.lang.String)
   */
  public boolean verifyLogin(String username, String password) {
    return true;
  }
}
