package org.taskstodo.model;

import java.util.Date;

import org.bson.types.ObjectId;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.mongodb.morphia.annotations.Entity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.taskstodo.util.ObjectIdJsonSerializer;

@Entity
@Document
public abstract class BasicEntity {
  @Id
  @JsonSerialize(using=ObjectIdJsonSerializer.class)
  private ObjectId id;
  private Date created = new Date();
  private Date modified;
  
  // --

  public ObjectId getId() {
    return id;
  }

  public void setId(ObjectId id) {
    this.id = id;
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
    
    if (o1 instanceof ObjectId && o2 instanceof ObjectId) {
      return (((ObjectId) o1).toString()).equals(((ObjectId) o2).toString());
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
