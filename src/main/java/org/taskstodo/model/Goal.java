package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.data.mongodb.core.mapping.Document;
import org.taskstodo.util.ObjectIdJsonSerializer;

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
  @JsonSerialize(using=ObjectIdJsonSerializer.class)
  private ObjectId userId;
  private int urgency;
  private int priority;
  private int level = 0;
  private int position = 1;
  
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
  
  public int getUrgency() {
    return urgency;
  }

  public void setUrgency(int urgency) {
    if (this.urgency != urgency) {
      this.urgency = urgency;
      setModified(new Date());
    }
  }

  public int getPriority() {
    return priority;
  }

  public void setPriority(int priority) {
    if (this.priority != priority) {
      this.priority = priority;
      setModified(new Date());
    }
  }
  
  public int getLevel() {
    return level;
  }
  
  public void setLevel(int level) {
    if (this.level != level) {
      this.level = level;
      setModified(new Date());
    }
  }
  
  public int getPosition() {
    return position;
  }
  
  public void setPosition(int position) {
    if (this.position != position) {
      this.position = position;
      setModified(new Date());
    }
  }
  
  // --

  @Override
  public String toString() {
    return "Goal [title=" + title + ", description=" + description
        + ", userId=" + userId + ", urgency=" + urgency + ", priority="
        + priority + ", level=" + level + ", position=" + position + "]";
  } 
}
