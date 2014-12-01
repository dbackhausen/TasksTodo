package org.taskstodo.dao;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public interface FileDAO {
  /**
   * Saves a attachment to the attachment database.
   * 
   * @param taskId - the identifier of the task.
   * @param attachment - the attachment.
   * 
   * @return the new attachment.
   * 
   * @throws IOException - Error storing attachment to attachment database.
   */
  GridFSFile save(ObjectId taskId, MultipartFile attachment) throws IOException;
  
  /**
   * Loads a attachment from the attachment database.
   * 
   * @param id - the identifier of the attachment.
   * 
   * @return the attachment.
   */
  GridFSDBFile load(ObjectId id);

  /**
   * Returns the list of attachments for a given task.
   * 
   * @param taskId - the identifier of the task.
   * 
   * @return the list.
   */
  List<GridFSDBFile> list(ObjectId taskId);
  
  /**
   * Deletes a attachment.
   * 
   * @param attachmentId - the attachment identifier.
   */
  void delete(ObjectId attachmentId);
}
