package com.greenfox.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name= "TB_Message")
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String message;

  public ChatMessage(String message){
    this.id = id;
    this.message = message;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
