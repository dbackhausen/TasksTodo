package org.taskstodo.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Goal extends BasicEntity {
  private String title;
  private String description;
  
  // --
  
  public Goal() {
  }
  
  public Goal(String title) {
    setTitle(title);
  }

  // --
  
  public String getTitle() {
    return title;
  }
  
  public void setTitle(String title) {
    if (!isEqual(this.title, title)) {
      this.title = title;
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
}
