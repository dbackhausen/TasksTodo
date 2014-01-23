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
import org.taskstodo.model.Link;

@Repository
public class LinkDAOImpl extends GenericDaoImpl<Link> implements LinkDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(LinkDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public LinkDAOImpl() {
    super(Link.class);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.LinkDAO#findByTask(org.bson.types.ObjectId)
   */
  @Override
  public List<Link> findByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    query.with(new Sort(Direction.DESC, "created"));
    List<Link> links = mongoTemplate.find(query, Link.class);
    
    LOGGER.debug("Found " +  links.size() + " links for task " + taskId.toString());
    
    return links;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.LinkDAO#deleteAllByTask(org.bson.types.ObjectId)
   */
  @Override
  public void deleteAllByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    mongoTemplate.remove(query, Link.class);
  }
}
