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
import org.taskstodo.model.Task;

@Repository
public class TaskDAOImpl extends GenericDaoImpl<Task> implements TaskDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;

  public TaskDAOImpl() {
    super(Task.class);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.TaskDAO#findAll(org.bson.types.ObjectId)
   */
  @Override
  public List<Task> findAll(ObjectId goalId) {
    Criteria c = Criteria.where("goalId").is(goalId);
    c.andOperator(Criteria.where("deleted").is(false).andOperator(Criteria.where("completed").is(false)));
    
    Query query = Query.query(c);
    query.with(new Sort(Sort.Direction.ASC, "position"));
    
    List<Task> tasks = mongoTemplate.find(query, Task.class);
    LOGGER.debug("Found " + (tasks != null ? tasks.size() : "0") + " active tasks for goal " + goalId.toString());
    
    return tasks;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.TaskDAO#findAll(org.bson.types.ObjectId, org.springframework.data.domain.Sort)
   */
  @Override
  public List<Task> findAll(ObjectId goalId, Sort sort) {
    Criteria c = Criteria.where("goalId").is(goalId);
    c.andOperator(Criteria.where("deleted").is(false).andOperator(Criteria.where("completed").is(false)));
    
    Query query = Query.query(c);
    query.with(sort);
    
    List<Task> tasks = mongoTemplate.find(query, Task.class);
    LOGGER.debug("Found " + (tasks != null ? tasks.size() : "0") + " active tasks for goal " + goalId.toString());
    
    return tasks;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.TaskDAO#findAllCompleted(org.bson.types.ObjectId)
   */
  @Override
  public List<Task> findAllCompleted(final ObjectId goalId) {
    Criteria c = Criteria.where("goalId").is(goalId);
    c.andOperator(Criteria.where("deleted").is(false).andOperator(Criteria.where("completed").is(true)));
    
    Query query = Query.query(c);
    query.with(new Sort(Sort.Direction.DESC, "completedDate"));
    
    List<Task> tasks = mongoTemplate.find(query, Task.class);
    LOGGER.debug("Found " + (tasks != null ? tasks.size() : "0") + " completed tasks for goal " + goalId.toString());
    
    return tasks;
  }
}
