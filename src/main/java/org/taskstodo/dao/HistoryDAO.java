package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.model.HistoryEntry;

public interface HistoryDAO extends GenericDao<HistoryEntry> {

  /**
   * Returns the history for a given task.
   * 
   * @param taskId - the task identifier.
   * 
   * @return the history.
   */
  List<HistoryEntry> findByTask(ObjectId taskId);

  /**
   * Deletes the history by a given task.
   * 
   * @param taskId - the task identifier.
   */
  void deleteAllByTask(ObjectId taskId);
}
