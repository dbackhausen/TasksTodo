package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public abstract class BasicEntity {
  @Id
  private ObjectId id;
  private String identifier;
  private Date created = new Date();
  private Date modified;
  
  // --

  public ObjectId getId() {
    return id;
  }
  
  public String getIdAsString() {
    return id != null ? id.toString() : null;
  }
  
  public void setId(ObjectId id) {
    this.id = id;
    this.identifier = id.toString();
  }
  
  public String getIdentifier() {
    return identifier;
  }
  
  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }
  
  protected boolean isEqual(Object o1, Object o2) {
    if (o1 == null && o2 == null) {
      return true;
    }

    if ((o1 == null && o2 != null) || (o1 != null && o2 == null)) {
      return false;
    }
    
    if (o1 instanceof String && o2 instanceof String) {
      return ((String) o1).equals((String) o2);
    }
    
    if (o1 instanceof Date && o2 instanceof Date) {
      return ((Date) o1).getTime() == ((Date) o2).getTime();
    }
    
    if (o1 instanceof Integer && o2 instanceof Integer) {
      return ((Integer) o1).intValue() == ((Integer) o2).intValue();
    }
    
    if (o1 instanceof Double && o2 instanceof Double) {
      return ((Double) o1).doubleValue() == ((Double) o2).doubleValue();
    }
    
    if (o1 instanceof Float && o2 instanceof Float) {
      return ((Float) o1).floatValue() == ((Float) o2).floatValue();
    }
    
    return false;
  }
}
