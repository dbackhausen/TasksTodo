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
   * @see org.taskstodo.dao.TaskDAO#findSubTasks(org.bson.types.ObjectId)
   */
  @Override
  public List<Task> findSubTasks(final ObjectId parentId) {
    Query query = new Query(Criteria.where("parentId").is(parentId));
    query.with(new Sort(Sort.Direction.ASC, "position"));
    List<Task> subtasks = mongoTemplate.find(query, Task.class);
    LOGGER.debug("Found " + (subtasks != null ? subtasks.size() : "0") + " subtasks for task " + parentId);
    return subtasks;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.TaskDAO#findAll(org.bson.types.ObjectId)
   */
  @Override
  public List<Task> findAll(ObjectId goalId) {
    Query query = new Query(Criteria.where("goalId").is(goalId));
    query.with(new Sort(Sort.Direction.ASC, "position"));
    List<Task> subtasks = mongoTemplate.find(query, Task.class);
    LOGGER.debug("Found " + (subtasks != null ? subtasks.size() : "0") + " tasks for goal " + goalId);
    return subtasks;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.TaskDAO#findAll(org.bson.types.ObjectId, org.springframework.data.domain.Sort)
   */
  @Override
  public List<Task> findAll(ObjectId goalId, Sort sort) {
    Query query = Query.query(Criteria.where("goalId").is(goalId));
    query.with(sort);
    List<Task> goals = mongoTemplate.find(query, Task.class);
    LOGGER.debug("Found " + (goals != null ? goals.size() : "0") + " tasks for goal " + goalId.toString());
    return goals;
  }
}
