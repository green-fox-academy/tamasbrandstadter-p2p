package com.greenfox.controller;

import com.greenfox.model.*;
import com.greenfox.repository.MessageRepository;
import com.greenfox.service.Forward;
import com.greenfox.service.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReceiveRestController {
  private static String logLevel = System.getenv("CHAT_APP_LOGLEVEL");
  private static final String CLIENT_ID = System.getenv("CHAT_APP_UNIQUE_ID");
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private MessageValidator messageValidator;
  @Autowired
  private SimpMessagingTemplate simpMessagingTemplate;
  @Autowired
  private Forward forward;

  @PostMapping("/api/message/receive")
  @CrossOrigin("*")
  public Object receiveMessage(@RequestBody ReceivedMessage receivedMessage) {
    Log log = new Log("/api/message/receive", "POST", "");
    if (!logLevel.equals("ERROR")) {
      log.showLog();
    }
    List<String> missingList = messageValidator.validateMessage(receivedMessage);
    if (missingList.isEmpty() && !receivedMessage.getClient().getId().equals(CLIENT_ID)) {
      ChatMessage chatMessage = receivedMessage.getChatMessage();
      messageRepository.save(chatMessage);
      convertAndSend(chatMessage);
      forward.forwardMessage(receivedMessage);
      return new OkResponse();
    } else if (!missingList.isEmpty()) {
      String missingFields = messageValidator.getMissingFields(missingList);
      messageValidator.clearList();
      return new ErrorResponse("Missing field(s): " + missingFields);
    } else {
      return new OkResponse();
    }
  }

  private void convertAndSend(ChatMessage chatMessage) {
    simpMessagingTemplate.convertAndSend("/topic", chatMessage);
  }
}





