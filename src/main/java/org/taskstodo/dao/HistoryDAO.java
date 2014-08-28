package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.model.History;

public interface HistoryDAO extends GenericDao<History> {

  /**
   * Returns the histories for a given task.
   * 
   * @param taskId - the task identifier.
   * 
   * @return the histories.
   */
  List<History> findByTask(ObjectId taskId);

  /**
   * Deletes all histories by a given task.
   * 
   * @param taskId - the task identifier.
   */
  void deleteAllByTask(ObjectId taskId);
}
