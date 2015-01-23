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
import org.taskstodo.model.HistoryEntry;

@Repository
public class HistoryDAOImpl extends GenericDaoImpl<HistoryEntry> implements HistoryDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(HistoryDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public HistoryDAOImpl() {
    super(HistoryEntry.class);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.HistoryDAO#findByTask(org.bson.types.ObjectId)
   */
  @Override
  public List<HistoryEntry> findByTask(ObjectId taskId) {
    Criteria c = Criteria.where("taskId").is(taskId);
    c.andOperator(Criteria.where("deleted").is(false));
    
    Query query = Query.query(c);
    query.fields().exclude("content"); // exclude massive data load
    query.with(new Sort(Direction.DESC, "created"));
    List<HistoryEntry> histories = mongoTemplate.find(query, HistoryEntry.class);
    
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
    mongoTemplate.remove(query, HistoryEntry.class);
  }
}
