package com.greenfox.model;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name= "TB_Message")
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  long id;
  String message;
  String userName;
  Timestamp timestamp;

  public ChatMessage(String userName, String message){
    this.id = 1000000 + (long)(Math.random() * 9999999);
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
}
