package com.greenfox.controller;

import com.greenfox.model.ChatMessage;
import com.greenfox.model.ErrorResponse;
import com.greenfox.model.OkResponse;
import com.greenfox.model.ReceivedMessage;
import com.greenfox.repository.MessageRepository;
import com.greenfox.service.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReceiveRestController {
  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private MessageValidator messageValidator;

  @PostMapping("/api/message/receive")
  @CrossOrigin("*")
  public Object receiveMessage(@RequestBody ReceivedMessage receivedMessage) {
    List<String> missingList = messageValidator.validateMessage(receivedMessage);
    if (missingList.isEmpty()) {
      ChatMessage chatMessage = receivedMessage.getChatMessage();
      messageRepository.save(chatMessage);
      return new OkResponse();
    } else {
      String missingFields = "";
      for (String aMissingList : missingList) {
        missingFields += aMissingList + ", ";
      }
      return new ErrorResponse("Missing field(s): " + missingFields );
    }
  }
}





