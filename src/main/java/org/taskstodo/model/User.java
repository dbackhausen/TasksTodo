package org.taskstodo.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User extends BasicEntity {
  private String username;
  private String password;
  
  // --
  
  public User() {
  }
  
  // --

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    if (!isEqual(this.username, username)) {
      this.username = username;
      setModified(new Date());
    }
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    if (!isEqual(this.password, password)) {
      this.password = password;
      setModified(new Date());
    }
  }
}