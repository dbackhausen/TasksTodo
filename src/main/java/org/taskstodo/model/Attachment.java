package org.taskstodo.model;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.annotation.Transient;
import org.taskstodo.util.ObjectIdJsonSerializer;

public class Attachment extends BasicEntity {
  @JsonSerialize(using=ObjectIdJsonSerializer.class)
  private ObjectId taskId;
  private String filename;
  private long size;
  private String contentType;
  
  @Transient
  private String url;
  
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
  
  public long getSize() {
    return size;
  }
  
  public void setSize(long size) {
    this.size = size;
  }
  
  public String getContentType() {
    return contentType;
  }
  
  public void setContentType(String contentType) {
    this.contentType = contentType;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  public String getUrl() {
    return url;
  }
}
