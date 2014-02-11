package org.taskstodo.dao;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import org.taskstodo.model.BasicEntity;
import org.taskstodo.service.TaskServiceImpl;

@Repository
public class GenericDaoImpl<T> implements GenericDao<T> {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(TaskServiceImpl.class);
  
  @Autowired
  private MongoTemplate mongoTemplate;

  private Class<T> genericType;

  // --

  protected GenericDaoImpl() {
  }

  public GenericDaoImpl(Class<T> genericType) {
    this.genericType = genericType;
  }

  // --

  /* (non-Javadoc)
   * @see org.taskstodo.dao.GenericDao#create(java.lang.Object)
   */
  @Override
  public ObjectId create(final T t) {
    if (!mongoTemplate.collectionExists(genericType)) {
      mongoTemplate.createCollection(genericType);
      LOGGER.debug("Collection for " + genericType.getSimpleName() + " created");
    }
    
    ((BasicEntity) t).setCreated(new Date());
    mongoTemplate.save(t);
    LOGGER.debug("Entity " + t.getClass().getSimpleName() + " successfully saved!");
    
    return ((BasicEntity) t).getId();
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.GenericDao#findById(org.bson.types.ObjectId)
   */
  @Override
  public T findById(final ObjectId id) {
    return mongoTemplate.findById(id, genericType);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.GenericDao#findAll()
   */
  @Override
  public List<T> findAll() {
    return mongoTemplate.findAll(genericType);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.GenericDao#update(java.lang.Object)
   */
  @Override
  public void update(final T t) {
    ((BasicEntity) t).setModified(new Date());
    mongoTemplate.save(t);
    LOGGER.debug("Entity " + t.getClass().getSimpleName() + " successfully updated!");
  }

  /* (non-Javadoc)
   * @see org.taskstodo.dao.GenericDao#delete(org.bson.types.ObjectId)
   */
  @Override
  public void delete(final ObjectId id) {
    T t = findById(id);

    if (t != null) {
      mongoTemplate.remove(t);
      LOGGER.debug("Entity " + t.getClass().getSimpleName() + " successfully removed!");
    }
  }
}
