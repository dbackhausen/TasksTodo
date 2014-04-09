package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    List<Task> subtasks = mongoTemplate.find(new Query(Criteria.where("parentTask").is(parentId)), Task.class);
    LOGGER.debug("Found " + (subtasks != null ? subtasks.size() : "0") + " subtasks for task " + parentId);
    return subtasks;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.TaskDAO#findAllByGoal(org.bson.types.ObjectId)
   */
  @Override
  public List<Task> findAllByGoal(ObjectId goalId) {
    List<Task> subtasks = mongoTemplate.find(new Query(Criteria.where("goal").is(goalId)), Task.class);
    LOGGER.debug("Found " + (subtasks != null ? subtasks.size() : "0") + " tasks for goal " + goalId);
    return subtasks;
  }
}
