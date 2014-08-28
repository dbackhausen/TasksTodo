package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.taskstodo.model.Goal;

@Repository
public class GoalDAOImpl extends GenericDaoImpl<Goal> implements GoalDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(GoalDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;

  public GoalDAOImpl() {
    super(Goal.class);
  }
    
  /*
   * (non-Javadoc)
   * 
   * @see org.taskstodo.dao.GenericDao#findAll()
   */
  @Override
  public List<Goal> findAll(ObjectId userId) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    List<Goal> goals = mongoTemplate.find(query, Goal.class);
    LOGGER.debug("Found " + (goals != null ? goals.size() : "0") + " goals from user " + userId.toString());
    return goals;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.GenericDao#findAll(org.springframework.data.domain.Sort)
   */
  @Override
  public List<Goal> findAll(ObjectId userId, Sort sort) {
    Query query = Query.query(Criteria.where("userId").is(userId));
    query.with(sort);
    List<Goal> goals = mongoTemplate.find(query, Goal.class);
    LOGGER.debug("Found " + (goals != null ? goals.size() : "0") + " goals from user " + userId.toString());
    return goals;
  }
}
