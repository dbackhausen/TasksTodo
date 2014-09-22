package org.taskstodo.dao;

import java.io.IOException;
import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.mongodb.BasicDBObject;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSFile;

@Repository
public class FileDAOImpl implements FileDAO {
  /* The Logger */
  private static final Logger LOGGER = LoggerFactory.getLogger(FileDAOImpl.class);

  @Autowired
  private MongoTemplate mongoTemplate;
  
  @Autowired
  private GridFsTemplate gridTemplate;
  
  public FileDAOImpl() {
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.FileDAO#save(org.bson.types.ObjectId, org.springframework.web.multipart.MultipartFile)
   */
  @Override
  public GridFSFile save(ObjectId taskId, MultipartFile file) throws IOException {
    BasicDBObject metadata = new BasicDBObject();
    metadata.append("taskId", taskId);
    GridFSFile result = gridTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
    
    LOGGER.debug("File " +  result.getFilename() + " successfully saved!");
    
    return result;
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.FileDAO#load(org.bson.types.ObjectId)
   */
  @Override
  public GridFSDBFile load(ObjectId id) {
    Query query = Query.query(Criteria.where("_id").is(id));
    return gridTemplate.findOne(query);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.FileDAO#getFiles(org.bson.types.ObjectId)
   */
  @Override
  public List<GridFSDBFile> list(ObjectId taskId) {
    Criteria c = Criteria.where("metadata.taskId").is(taskId);
    c.andOperator(Criteria.where("metadata.deleted").is(false));
    
    Query query = Query.query(c);
//    query.with(new Sort(Sort.Direction.ASC, "uploadDate"));
    return gridTemplate.find(query);
  }
  
  /* (non-Javadoc)
   * @see org.taskstodo.dao.FileDAO#remove(org.bson.types.ObjectId)
   */
  @Override
  public void delete(ObjectId id) {
    Query query = Query.query(Criteria.where("_id").is(id));
    gridTemplate.delete(query);
  }
}
