package com.greenfox.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReceivedMessage {
  @JsonProperty(value = "message")
  private ChatMessage chatMessage;
  @JsonProperty(value = "client")
  private Client client;

  public ReceivedMessage(ChatMessage chatMessage, Client client){
    this.chatMessage = chatMessage;
    this.client = client;
  }

  public ReceivedMessage() {

  }

  public ChatMessage getChatMessage() {
    return chatMessage;
  }

  public void setChatMessage(ChatMessage chatMessage) {
    this.chatMessage = chatMessage;
  }

  public Client getClient() {
    return client;
  }

  public void setClient(Client client) {
    this.client = client;
  }
}
