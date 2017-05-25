package com.greenfox.service;

import com.greenfox.model.OkResponse;
import com.greenfox.model.ReceivedMessage;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class Forward {
  private RestTemplate restTemplate;
  private static final String URI = System.getenv("CHAT_APP_PEER_ADDRESS") + "/api/message/receive";

  public Forward(){
    this.restTemplate = new RestTemplate();
  }

  public void forwardMessage(ReceivedMessage receivedMessage){
    restTemplate.postForObject(URI, receivedMessage, OkResponse.class);
  }
}
