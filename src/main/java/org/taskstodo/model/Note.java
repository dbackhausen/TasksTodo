package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.mongodb.core.mapping.Document;
import org.taskstodo.util.ObjectIdJsonSerializer;

@Document
public class Note extends BasicEntity {
  @JsonSerialize(using=ObjectIdJsonSerializer.class)
  private ObjectId taskId;
  private String body;
  
  // --
  
  public Note() {
  }
  
  public Note(ObjectId taskId) {
    setTaskId(taskId);
  }
  
  // --
  
  public void setTaskId(ObjectId taskId) {
    this.taskId = taskId;
  }
  
  public ObjectId getTaskId() {
    return taskId;
  }
  
  public String getBody() {
    return body;
  }
  
  public void setBody(String body) {
    if (!isEqual(this.body, body)) {
      this.body = body;
      setModified(new Date());
    }
  }
}
