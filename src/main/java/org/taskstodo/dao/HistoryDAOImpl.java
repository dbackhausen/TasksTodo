package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.taskstodo.model.History;

@Repository
public class HistoryDAOImpl extends GenericDaoImpl<History> implements HistoryDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public HistoryDAOImpl() {
    super(History.class);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.HistoryDAO#findByTask(org.bson.types.ObjectId)
   */
  @Override
  public List<History> findByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    query.with(new Sort(Direction.DESC, "created"));
    List<History> histories = mongoTemplate.find(query, History.class);
    
    LOGGER.debug("Found " +  histories.size() + " history entries for task " + taskId.toString());
    
    return histories;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.HistoryDAO#deleteAllByTask(org.bson.types.ObjectId)
   */
  @Override
  public void deleteAllByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    mongoTemplate.remove(query, History.class);
  }
}
