package com.greenfox.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name= "TB_Message")
public class ChatMessage {
  @Id
  private long id;
  private String message;
  private String userName;
  private Timestamp timestamp;

  public ChatMessage(String userName, String message){
    this.id = 1000000 + (long)(Math.random() * 1000000);
    this.userName = userName;
    this.message = message;
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }

  public ChatMessage(long id, String userName, String message, Timestamp timestamp) {
    this.id = id;
    this.userName = userName;
    this.message = message;
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }

  public ChatMessage() {

  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public long generateRandomNumber() {
    return id = 1000000 + (long)(Math.random() * 1000000);
  }

}
