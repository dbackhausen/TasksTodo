package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * The task, which is assigned to a goal and can be assigned to another task.
 * 
 * {
 *   "_id" : ObjectId("53037589300445563c8fdc6d"),
 *   "_class" : "org.taskstodo.model.Task",
 *   "title" : "Sample task",
 *   "description" : "Sample description",
 *   "goal": ObjectId("33037589300445563c8fdc6d"),
 *   "parentTask": ObjectId("73037589300445563c8fdc6d"),
 *   "dueDate" : ISODate("2014-02-18T15:00:25.882Z"),
 *   "completedDate" : ISODate("2014-02-18T15:00:25.882Z"),
 *   "reminderDate" : ISODate("2014-02-18T15:00:25.882Z"),
 *   "urgency": 1,
 *   "priority": 3,
 *   "position": 1,
 *   "created" : ISODate("2014-02-18T15:00:25.882Z"),
 *   "modified" : ISODate("2014-02-18T15:00:25.881Z")
 * }
 */
@Document
public class Task extends BasicEntity {
  private String title;
  private String description;
  private ObjectId goalId;
  private ObjectId parentId;
  private Date dueDate = null;
  private Date completedDate = null;
  private Date reminderDate = null;
  private int urgency;
  private int priority;
  private int position = 1;
  
  // --
  
  public Task() {
  }
  
  public Task(String title) {
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

  public ObjectId getGoalId() {
    return goalId;
  }
  
  public String getGoalIdAsString() {
    return goalId != null ? goalId.toString() : null;
  }
  
  public void setGoalId(ObjectId goalId) {
    this.goalId = goalId;
  }
  
  public ObjectId getParentId() {
    return parentId;
  }
  
  public String getParentIdAsString() {
    return parentId != null ? parentId.toString() : null;
  }
  
  public void setParentId(ObjectId parentId) {
    this.parentId = parentId;
  }
  
  public Date getDueDate() {
    return dueDate;
  }

  public void setDueDate(Date dueDate) {
    if (!isEqual(this.dueDate, dueDate)) {
      this.dueDate = dueDate;
      setModified(new Date());
    }
  }

  public Date getCompletedDate() {
    return completedDate;
  }

  public void setCompletedDate(Date completedDate) {
    if (!isEqual(this.completedDate, completedDate)) {
      this.completedDate = completedDate;
      setModified(new Date());
    }
  }

  public Date getReminderDate() {
    return reminderDate;
  }

  public void setReminderDate(Date reminderDate) {
    if (!isEqual(this.reminderDate, reminderDate)) {
      this.reminderDate = reminderDate;
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
  
  public int getPosition() {
    return position;
  }
  
  public void setPosition(int position) {
    this.position = position;
  }
  
  // --

  @Override
  public String toString() {
    return "Task [id=" + getId() + ", title=" + title + ", description="
        + description + ", dueDate=" + dueDate + ", completedDate="
        + completedDate + ", reminderDate=" + reminderDate + ", urgency="
        + urgency + ", priority=" + priority + ", created=" + getCreated()
        + ", modified=" + getModified() + "]";
  }
}
