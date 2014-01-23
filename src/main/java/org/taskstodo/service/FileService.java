package org.taskstodo.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;


public interface FileService {
  /**
   * Saves a file to the file database.
   * 
   * @param taskId - the identifier of the task.
   * @param mfile - the file.
   * 
   * @return the new file.
   * 
   * @throws IOException - Error storing file to file database.
   */
  GridFSFile saveFile(ObjectId taskId, MultipartFile mfile) throws IOException;
  
  /**
   * Loads a file from the file database.
   * 
   * @param id - the identifier of the file.
   * 
   * @return the file.
   */
  GridFSDBFile loadFile(ObjectId id);

  /**
   * Returns the list of files for a given task.
   * 
   * @param taskId - the identifier of the task.
   * 
   * @return the list.
   */
  List<GridFSDBFile> getFiles(ObjectId taskId);

  /**
   * Deletes a file.
   * 
   * @param fileId - the file identifier.
   */
  void delete(ObjectId fileId);
}
