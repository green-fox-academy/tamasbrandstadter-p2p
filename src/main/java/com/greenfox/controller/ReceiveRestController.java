package com.greenfox.controller;

import com.greenfox.model.*;
import com.greenfox.repository.MessageRepository;
import com.greenfox.service.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class ReceiveRestController {
  private static String logLevel = System.getenv("CHAT_APP_LOGLEVEL");
  private static final String URI = System.getenv("CHAT_APP_PEER_ADDRESS") + "/api/message/receive";
  private static final String CLIENT_ID = System.getenv("CHAT_APP_UNIQUE_ID");
  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private MessageValidator messageValidator;

  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;

  @PostMapping("/api/message/receive")
  @CrossOrigin("*")
  public Object receiveMessage(@RequestBody ReceivedMessage receivedMessage) {
    Log log = new Log("/api/message/receive", "POST", "");
    if (!logLevel.equals("ERROR")) {
      log.setLogLevel(logLevel);
      System.out.println(log.toString());
    }
    List<String> missingList = messageValidator.validateMessage(receivedMessage);
    if (missingList.isEmpty() && !receivedMessage.getClient().getId().equals(CLIENT_ID)) {
      ChatMessage chatMessage = receivedMessage.getChatMessage();
      RestTemplate restTemplate = new RestTemplate();
      restTemplate.postForObject(URI, receivedMessage, OkResponse.class);
      messageRepository.save(chatMessage);
      convertAndSend(chatMessage);
      return new OkResponse();
    } else if (!missingList.isEmpty()) {
      String missingFields = "";
      for (String missing : missingList) {
        missingFields += missing + ", ";
      }
      //messageValidator.clearList();
      return new ErrorResponse("Missing field(s): " + missingFields);
    } else {
      return new OkResponse();
    }
  }

  public void convertAndSend(ChatMessage chatMessage) {
    simpMessagingTemplate.convertAndSend("/topic", chatMessage);
  }
}





