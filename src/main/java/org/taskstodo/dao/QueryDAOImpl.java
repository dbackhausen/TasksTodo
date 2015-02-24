package org.taskstodo.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
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

@Repository
public class QueryDAOImpl extends GenericDaoImpl<org.taskstodo.model.Query> implements QueryDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(QueryDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public QueryDAOImpl() {
    super(org.taskstodo.model.Query.class);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.QueryDAO#findByTask(org.bson.types.ObjectId)
   */
  @Override
  public List<org.taskstodo.model.Query> findByTask(ObjectId taskId) {
    Criteria c = Criteria.where("taskId").is(taskId);
    
    Query query = Query.query(c);
    query.with(new Sort(Direction.DESC, "created"));
    List<org.taskstodo.model.Query> queries = mongoTemplate.find(query, org.taskstodo.model.Query.class);
    
    LOGGER.debug("Found " +  queries.size() + " search queries for task " + taskId.toString());
    
    return queries;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.QueryDAO#findByTaskAndEngine(org.bson.types.ObjectId, java.lang.String)
   */
  @Override
  public List<org.taskstodo.model.Query> findByTaskAndEngine(ObjectId taskId, String engine) {
    Criteria c = Criteria.where("taskId").is(taskId);
    c.andOperator(Criteria.where("engine").is(engine));
    
    Query query = Query.query(c);
    query.limit(5); // only get 5 results
    query.with(new Sort(Direction.DESC, "created"));

    List<org.taskstodo.model.Query> queries = mongoTemplate.find(query, org.taskstodo.model.Query.class);
    List<org.taskstodo.model.Query> filteredQueries = new ArrayList<org.taskstodo.model.Query>();
    HashSet<String> queryStrings = new HashSet<String>();
    
    LOGGER.debug(">>> " +  queries.size());
    
    
    for (org.taskstodo.model.Query q : queries) {
      if (!queryStrings.contains(q.getQueryString())) {
        filteredQueries.add(q);
        queryStrings.add(q.getQueryString());
      }
    }
    
    LOGGER.debug("Found " +  filteredQueries.size() + " " + engine + " search queries for task " + taskId.toString());
    
    return filteredQueries;
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.QueryDAO#deleteAllByTask(org.bson.types.ObjectId)
   */
  @Override
  public void deleteAllByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    mongoTemplate.remove(query, org.taskstodo.model.Query.class);
  }
}
