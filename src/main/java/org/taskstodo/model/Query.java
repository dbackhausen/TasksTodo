package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.mongodb.core.mapping.Document;
import org.taskstodo.util.ObjectIdJsonSerializer;

@Document(collection="query")
public class Query extends BasicEntity {
  @JsonSerialize(using=ObjectIdJsonSerializer.class)
  private ObjectId taskId;
  private String queryString;
  private String engine;
  private String url;
  
  // --
  
  public Query() {
  }
  
  public Query(ObjectId taskId) {
    setTaskId(taskId);
  }
  
  // --
  
  public void setTaskId(ObjectId taskId) {
    this.taskId = taskId;
  }
  
  public ObjectId getTaskId() {
    return taskId;
  }
  
  public String getQueryString() {
    return queryString;
  }
  
  public void setQueryString(String queryString) {
    if (!isEqual(this.queryString, queryString)) {
      this.queryString = queryString;
      setModified(new Date());
    }
  }
  
  public String getEngine() {
    return engine;
  }
  
  public void setEngine(String engine) {
    if (!isEqual(this.engine, engine)) {
      this.engine = engine;
      setModified(new Date());
    }
  }

  public String getUrl() {
    return url;
  }
  
  public void setUrl(String url) {
    if (!isEqual(this.url, url)) {
      this.url = url;
      setModified(new Date());
    }
  }
  
  @Override
  public String toString() {
    return "Query [taskId=" + taskId + ", queryString=" + queryString + ", engine=" + engine + ", url=" + url + "]";
  }
}
