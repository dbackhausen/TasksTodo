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

@Repository
public class BookmarkDAOImpl extends GenericDaoImpl<Bookmark> implements BookmarkDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(BookmarkDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  public BookmarkDAOImpl() {
    super(Bookmark.class);
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.BookmarkDAO#findByTask(org.bson.types.ObjectId)
   */
  @Override
  public List<Bookmark> findByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    query.with(new Sort(Direction.DESC, "created"));
    List<Bookmark> bookmarks = mongoTemplate.find(query, Bookmark.class);
    
    LOGGER.debug("Found " +  bookmarks.size() + " bookmarks for task " + taskId.toString());
    
    return bookmarks;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.BookmarkDAO#deleteAllByTask(org.bson.types.ObjectId)
   */
  @Override
  public void deleteAllByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    mongoTemplate.remove(query, Bookmark.class);
  }
}
