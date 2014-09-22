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
    
  /* (non-Javadoc)
   * @see org.taskstodo.dao.GoalDAO#findAll(org.bson.types.ObjectId)
   */
  @Override
  public List<Goal> findAll(ObjectId userId) {
    Criteria c = Criteria.where("userId").is(userId);
    c.andOperator(Criteria.where("deleted").is(false).andOperator(Criteria.where("completed").is(false)));
    
    Query query = Query.query(c);
    query.with(new Sort(Sort.Direction.ASC, "position"));
    
    List<Goal> goals = mongoTemplate.find(query, Goal.class);
    LOGGER.debug("Found " + (goals != null ? goals.size() : "0") + " active goals for user " + userId.toString());
    
    return goals;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.GoalDAO#findAll(org.bson.types.ObjectId, org.springframework.data.domain.Sort)
   */
  @Override
  public List<Goal> findAll(ObjectId userId, Sort sort) {
    Criteria c = Criteria.where("userId").is(userId);
    c.andOperator(Criteria.where("deleted").is(false).andOperator(Criteria.where("completed").is(false)));
    
    Query query = Query.query(c);
    query.with(sort);
    
    List<Goal> goals = mongoTemplate.find(query, Goal.class);
    LOGGER.info("Found " + (goals != null ? goals.size() : "0") + " active goals for user " + userId.toString());
    
    return goals;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.GoalDAO#findAllCompleted(org.bson.types.ObjectId)
   */
  @Override
  public List<Goal> findAllCompleted(final ObjectId userId) {
    Criteria c = Criteria.where("userId").is(userId);
    c.andOperator(Criteria.where("deleted").is(false).andOperator(Criteria.where("completed").is(true)));
    
    Query query = Query.query(c);
    query.with(new Sort(Sort.Direction.DESC, "completedDate"));
    
    List<Goal> goals = mongoTemplate.find(query, Goal.class);
    LOGGER.debug("Found " + (goals != null ? goals.size() : "0") + " completed goals for user " + userId.toString());
    
    return goals;
  }
}
