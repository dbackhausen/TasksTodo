package org.taskstodo.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.taskstodo.model.User;

@Repository
public class UserDAOImpl extends GenericDaoImpl<User> implements UserDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(UserDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public UserDAOImpl() {
    super(User.class);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.UserDAO#findByUsername(java.lang.String)
   */
  public User findByUsername(final String username) {
    if (username != null) {
      LOGGER.debug("Looking for entity with username " + username + ".");
      Query query = new Query();
      query.addCriteria(Criteria.where("username").is(username));
      return mongoTemplate.findOne(query, User.class);
    }
    
    return null;
  }
}
