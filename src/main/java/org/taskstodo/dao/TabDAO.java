package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.model.Tab;

public interface TabDAO extends GenericDao<Tab> {

  /**
   * Returns the tabs for a given task.
   * 
   * @param taskId - the task identifier.
   * 
   * @return the tabs.
   */
  List<Tab> findByTask(ObjectId taskId);

  /**
   * Deletes all tabs by a given task.
   * 
   * @param taskId - the task identifier.
   */
  void deleteAllByTask(ObjectId taskId);
}
