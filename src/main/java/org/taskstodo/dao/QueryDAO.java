package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.model.Query;

public interface QueryDAO extends GenericDao<Query> {

  /**
   * Returns the queries for a given task.
   * 
   * @param taskId - the task identifier.
   * 
   * @return the queries.
   */
  List<Query> findByTask(ObjectId taskId);

  /**
   * Returns the queries for a given task and search engine.
   * 
   * @param taskId - the task identifier.
   * 
   * @return the queries.
   */
  List<Query> findByTaskAndEngine(ObjectId taskId, String engine);

  /**
   * Deletes the queries by a given task.
   * 
   * @param taskId - the task identifier.
   */
  void deleteAllByTask(ObjectId taskId);
}
