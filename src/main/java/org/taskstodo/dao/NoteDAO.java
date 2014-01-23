package org.taskstodo.dao;

import java.util.List;

import org.bson.types.ObjectId;
import org.taskstodo.model.Note;

public interface NoteDAO extends GenericDao<Note> {

  /**
   * Returns the notes for a given task.
   * 
   * @param taskId - the task identifier.
   * 
   * @return the notes.
   */
  List<Note> findByTask(ObjectId taskId);

  /**
   * Delete all notes by a given task.
   * 
   * @param taskId - the task identifier.
   */
  void deleteAllByTask(ObjectId taskId);
}
