package org.taskstodo.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tag {
  private String value;
  
  // --
  
  public Tag(String value) {
    setValue(value);
  }
  
  // --
  
  public String getValue() {
    return value;
  }
  
  public void setValue(String value) {
    this.value = value;
  }
}
