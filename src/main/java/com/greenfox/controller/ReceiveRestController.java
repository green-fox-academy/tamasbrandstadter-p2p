package com.greenfox.controller;

import com.greenfox.model.*;
import com.greenfox.repository.MessageRepository;
import com.greenfox.service.Forward;
import com.greenfox.service.Logger;
import com.greenfox.service.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class ReceiveRestController {
  private static final String CLIENT_ID = System.getenv("CHAT_APP_UNIQUE_ID");
  private final MessageRepository messageRepository;
  private final MessageValidator messageValidator;
  private final SimpMessagingTemplate simpMessagingTemplate;
  private final Forward forward;

  @Autowired
  public ReceiveRestController(MessageRepository messageRepository, MessageValidator messageValidator,
                               SimpMessagingTemplate simpMessagingTemplate, Forward forward) {
    this.messageRepository = messageRepository;
    this.messageValidator = messageValidator;
    this.simpMessagingTemplate = simpMessagingTemplate;
    this.forward = forward;
  }

  @PostMapping("/api/message/receive")
  @CrossOrigin("*")
  public Object receiveMessage(HttpServletRequest request, @RequestBody ReceivedMessage receivedMessage) {
    Logger.showLogWithOutParameter(request);
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





