package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.mongodb.core.mapping.Document;
import org.taskstodo.util.ObjectIdJsonSerializer;

@Document
public class Tab extends BasicEntity {
  @JsonSerialize(using=ObjectIdJsonSerializer.class)
  private ObjectId taskId;
  private String tabId;
  private String title;
  private String url;
  private String thumbnail;
  private boolean deleted;
  
  // --
  
  public Tab() {
  }
  
  public Tab(ObjectId taskId) {
    setTaskId(taskId);
  }
  
  // --
  
  public void setTaskId(ObjectId taskId) {
    this.taskId = taskId;
  }
  
  public ObjectId getTaskId() {
    return taskId;
  }
  
  public String getTabId() {
    return tabId;
  }
  
  public void setTabId(String tabId) {
    this.tabId = tabId;
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
  
  public String getThumbnail() {
    return thumbnail;
  }
  
  public void setThumbnail(String thumbnail) {
    if (this.thumbnail != thumbnail) {
      this.thumbnail = thumbnail;
      setModified(new Date());
    }
  }
  
  public boolean isDeleted() {
    return deleted;
  }

  public void setDeleted(boolean deleted) {
    if (this.deleted != deleted) {
      this.deleted = deleted;
      setModified(new Date());
    }
  }
}
