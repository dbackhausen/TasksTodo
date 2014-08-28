package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The goal, which is registered to an user and can contain tasks.
 * 
 * {
 *   "_id" : ObjectId("53037589300445563c8fdc6d"),
 *   "_class" : "org.taskstodo.model.Goal",
 *   "title" : "Sample goal",
 *   "description" : "Sample description",
 *   "created" : ISODate("2014-02-18T15:00:25.882Z"),
 *   "modified" : ISODate("2014-02-18T15:00:25.881Z")
 * }
 */
@Document
public class Goal extends BasicEntity {
  private String title;
  private String description;
  private ObjectId userId;
  
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
  
  public ObjectId getUserId() {
    return userId;
  }
  
  public void setUserId(ObjectId userId) {
    if (!isEqual(this.userId, userId)) {
      this.userId = userId;
      setModified(new Date());
    }
  }
  
  // --

  @Override
  public String toString() {
    return "Goal [title=" + title + ", description=" + description
        + ", userId=" + userId + "]";
  }
}
