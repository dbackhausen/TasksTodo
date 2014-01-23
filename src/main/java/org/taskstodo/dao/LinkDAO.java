package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.model.Link;

public interface LinkDAO extends GenericDao<Link> {

  /**
   * Returns the links for a given task.
   * 
   * @param taskId - the task identifier.
   * 
   * @return the links.
   */
  List<Link> findByTask(ObjectId taskId);

  /**
   * Deletes all links by a given task.
   * 
   * @param taskId - the task identifier.
   */
  void deleteAllByTask(ObjectId taskId);
}
