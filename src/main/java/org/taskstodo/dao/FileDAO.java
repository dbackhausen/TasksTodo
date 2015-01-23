package org.taskstodo.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

public interface FileDAO {
  /**
   * Saves an attachment to the attachment database.
   * 
   * @param taskId - the identifier of the task.
   * @param attachment - the attachment.
   * 
   * @return the new attachment.
   * 
   * @throws IOException - Error storing file to database.
   */
  GridFSFile save(ObjectId taskId, MultipartFile attachment) throws IOException;
  
  /**
   * Saves a file as an attachment.
   * 
   * @param taskId - the identifier of the task.
   * @param is - the input stream.
   * @param filename - the file name.
   * @param contentType - the content type.
   * 
   * @return the new attachment.
   * 
   * @throws IOException - Error storing file to database.
   */
  GridFSFile save(ObjectId taskId, InputStream is, String filename, String contentType) throws IOException;
  
  /**
   * Loads an attachment from the attachment database.
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
   * Deletes an attachment.
   * 
   * @param attachmentId - the attachment identifier.
   */
  void delete(ObjectId attachmentId);

  /**
   * Deletes all task attachments.
   * 
   * @param taskId - the task id.
   */
  void deleteAllByTask(ObjectId taskId);
}
