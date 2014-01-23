package org.taskstodo.dao;

import org.springframework.stereotype.Repository;
import org.taskstodo.model.User;

@Repository
public class UserDAOImpl extends GenericDaoImpl<User> implements UserDAO {
  public UserDAOImpl() {
    super(User.class);
  }
}
