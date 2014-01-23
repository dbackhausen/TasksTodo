package org.taskstodo.service;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.taskstodo.dao.FileDAO;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Service
public class FileServiceImpl implements FileService {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(FileServiceImpl.class);
  
  @Autowired
  private FileDAO fileDAO;
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.FileService#saveFile(org.bson.types.ObjectId, org.springframework.web.multipart.MultipartFile)
   */
  @Override
  public GridFSFile saveFile(ObjectId taskId, MultipartFile mfile) throws IOException {
    GridFSFile file = fileDAO.save(taskId, mfile);
    return file;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.FileService#loadFile(org.bson.types.ObjectId)
   */
  @Override
  public GridFSDBFile loadFile(ObjectId id) {
    GridFSDBFile file = fileDAO.load(id);
    LOGGER.info((file != null ? ("Loading file " + id.toString()) : ("File " + id.toString() + " not found!")));
    return file;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.FileService#getFiles(org.bson.types.ObjectId)
   */
  @Override
  public List<GridFSDBFile> getFiles(ObjectId taskId) {
    List<GridFSDBFile> files = fileDAO.list(taskId);
    LOGGER.info("Found " + files.size() + " files for task " + taskId.toString());
    return files;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.service.FileService#delete(org.bson.types.ObjectId)
   */
  @Override
  public void delete(ObjectId fileId) {
    fileDAO.delete(fileId);
  }
}
