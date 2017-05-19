package com.greenfox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name= "TB_Message")
public class ChatMessage {
  @Id
  @JsonProperty(value = "id")
  private long id;
  @JsonProperty(value = "text")
  private String message;
  @JsonProperty(value = "username")
  private String userName;
  @JsonProperty(value = "timestamp")
  private Timestamp timestamp;

  public ChatMessage(String userName, String message){
    this.id = 1000000 + (long)(Math.random() * 1000000);
    this.userName = userName;
    this.message = message;
    this.timestamp = new Timestamp(System.currentTimeMillis());
  }

  public ChatMessage(long id, String username, String text, Timestamp timestamp) {
    this.id = id;
    this.userName = username;
    this.message = text;
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

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }
}
