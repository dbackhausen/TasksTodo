package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;

public interface GenericDao<T> {
  /**
   * Persists a new entity.
   * 
   * @param t - the entity.
   * 
   * @return the identifier.
   */
  public ObjectId create(T t);

  /**
   * Finds an existing entity by its identifier.
   * 
   * @param id - the identifier.
   * 
   * @return the entity.
   */
  public T findById(ObjectId id);
  
  /**
   * Returns all entities.
   * 
   * @return all entities.
   */
  public List<T> findAll();

  /**
   * Returns all entities sorted by a given option.
   * 
   * @return all entities.
   */
  public List<T> findAll(Sort sort);
  
  /**
   * Updates an existing entity.
   * 
   * @param t - the entity.
   */
  public void update(T t);
  
  /**
   * Deletes an existing entity by its identifier.
   * 
   * @param id - the identifier.
   */
  public void delete(ObjectId id);
}