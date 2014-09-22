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
import org.taskstodo.model.Bookmark;
import org.taskstodo.model.Tab;

@Repository
public class TabDAOImpl extends GenericDaoImpl<Tab> implements TabDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TabDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public TabDAOImpl() {
    super(Tab.class);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.TabDAO#findByTask(org.bson.types.ObjectId)
   */
  @Override
  public List<Tab> findByTask(ObjectId taskId) {
    Criteria c = Criteria.where("taskId").is(taskId);
    c.andOperator(Criteria.where("deleted").is(false));
    
    Query query = Query.query(c);
    query.with(new Sort(Direction.ASC, "tabId"));
    List<Tab> tabs = mongoTemplate.find(query, Tab.class);
    
    LOGGER.debug("Found " +  tabs.size() + " tabs for task " + taskId.toString());
    
    return tabs;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.TabDAO#deleteAllByTask(org.bson.types.ObjectId)
   */
  @Override
  public void deleteAllByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    mongoTemplate.remove(query, Bookmark.class);
  }
}
