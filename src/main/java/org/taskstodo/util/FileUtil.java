package org.taskstodo.util;

import org.bson.types.ObjectId;
import org.taskstodo.model.File;

import com.mongodb.gridfs.GridFSDBFile;

public class FileUtil {
  public static File copy(GridFSDBFile gridFile, File file) {
    file.setId((ObjectId) gridFile.getId());
    file.setTaskId((ObjectId) gridFile.getMetaData().get("taskId"));
    file.setFilename(gridFile.getFilename());
    file.setContentType(gridFile.getContentType());
    file.setLength(gridFile.getLength());
    file.setUploadDate(gridFile.getUploadDate());
    
    return file;
  }
}
