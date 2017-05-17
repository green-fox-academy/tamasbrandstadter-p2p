package com.greenfox.model;

import org.springframework.stereotype.Component;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Component
public class ChatMessage {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private String message;

  public ChatMessage(String message){
    this.id = id;
    this.message = message;
  }
}
