package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.model.Bookmark;

public interface BookmarkDAO extends GenericDao<Bookmark> {

  /**
   * Returns the bookmarks for a given task.
   * 
   * @param taskId - the task identifier.
   * 
   * @return the bookmarks.
   */
  List<Bookmark> findByTask(ObjectId taskId);

  /**
   * Deletes all bookmarks by a given task.
   * 
   * @param taskId - the task identifier.
   */
  void deleteAllByTask(ObjectId taskId);
}
