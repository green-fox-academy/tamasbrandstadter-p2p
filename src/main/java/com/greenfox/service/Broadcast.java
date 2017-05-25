package com.greenfox.service;

import com.greenfox.model.ChatMessage;
import com.greenfox.model.Client;
import com.greenfox.model.OkResponse;
import com.greenfox.model.ReceivedMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Broadcast {
  private RestTemplate restTemplate;
  private static final String URI = System.getenv("CHAT_APP_PEER_ADDRESS") + "/api/message/receive";
  private static final String CLIENT_ID = System.getenv("CHAT_APP_UNIQUE_ID");

  public Broadcast() {
    this.restTemplate = new RestTemplate();
  }

  public void broadcastMessage(ChatMessage chatMessage) {
    restTemplate.postForObject(URI, new ReceivedMessage(chatMessage, new Client(CLIENT_ID)), OkResponse.class);
  }
}
