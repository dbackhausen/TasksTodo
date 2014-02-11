package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Link extends BasicEntity {
  private ObjectId taskId;
  private String title;
  private String url;
  private String description;
  private int relevance = 0;
  
  // --
  
  public Link() {
  }
  
  public Link(ObjectId taskId) {
    setTaskId(taskId);
  }
  
  // --
  
  public void setTaskId(ObjectId taskId) {
    this.taskId = taskId;
  }
  
  public ObjectId getTaskId() {
    return taskId;
  }
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    if (!isEqual(this.title, title)) {
      this.title = title;
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
  
  public String getDescription() {
    return description;
  }
  
  public void setDescription(String description) {
    if (!isEqual(this.description, description)) {
      this.description = description;
      setModified(new Date());
    }
  }

  public int getRelevance() {
    return relevance;
  }

  public void setRelevance(int relevance) {
    if (this.relevance != relevance) {
      this.relevance = relevance;
      setModified(new Date());
    }
  }
}