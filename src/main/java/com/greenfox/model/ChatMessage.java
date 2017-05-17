package com.greenfox.model;

import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class ChatMessage {

  private Long id;
  private String message;

  public ChatMessage(String message){
    this.id = id;
    this.message = message;
  }
}
