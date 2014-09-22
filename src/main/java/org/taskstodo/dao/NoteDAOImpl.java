package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.taskstodo.model.Note;

@Repository
public class NoteDAOImpl extends GenericDaoImpl<Note> implements NoteDAO {
  @Autowired
  private MongoTemplate mongoTemplate;

  public NoteDAOImpl() {
    super(Note.class);
  }
  
  /*
   * (non-Javadoc)
   * 
   * @see org.taskstodo.dao.NoteDAO#findByTask(org.bson.types.ObjectId)
   */
  @Override
  public List<Note> findByTask(ObjectId taskId) {
    Criteria c = Criteria.where("taskId").is(taskId);
    c.andOperator(Criteria.where("deleted").is(false));
    
    Query query = Query.query(c);
    query.with(new Sort(Direction.DESC, "created"));
    return mongoTemplate.find(query, Note.class);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.NoteDAO#deleteAllByTask(org.bson.types.ObjectId)
   */
  @Override
  public void deleteAllByTask(ObjectId taskId) {
    Query query = new Query();
    query.addCriteria(Criteria.where("taskId").is(taskId));
    mongoTemplate.remove(query, Note.class);
  }
}
