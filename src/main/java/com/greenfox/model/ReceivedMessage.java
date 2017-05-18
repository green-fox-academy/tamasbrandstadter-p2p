package com.greenfox.model;

public class ReceivedMessage {
  private ChatMessage chatMessage;
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
