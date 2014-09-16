package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.taskstodo.util.ObjectIdJsonSerializer;

public class File extends BasicEntity {
  @JsonSerialize(using=ObjectIdJsonSerializer.class)
  private ObjectId taskId;
  private String filename;
  private long length;
  private String contentType;
  private Date uploadDate;
  
  public ObjectId getTaskId() {
    return taskId;
  }
  
  public void setTaskId(ObjectId taskId) {
    this.taskId = taskId;
  }
  
  public String getFilename() {
    return filename;
  }
  
  public void setFilename(String filename) {
    this.filename = filename;
  }
  
  public long getLength() {
    return length;
  }
  
  public void setLength(long length) {
    this.length = length;
  }
  
  public String getContentType() {
    return contentType;
  }
  
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }
  
  public Date getUploadDate() {
    return uploadDate;
  }

  public void setUploadDate(Date uploadDate) {
    this.uploadDate = uploadDate;
  }
}
