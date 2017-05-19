package com.greenfox.controller;

import com.greenfox.model.ChatMessage;
import com.greenfox.model.OkResponse;
import com.greenfox.model.ReceivedMessage;
import com.greenfox.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ReceiveRestController {
  @Autowired
  private MessageRepository messageRepository;

  @PostMapping("/api/message/receive")
  @CrossOrigin("*")
  public Object receiveMessage(@RequestBody ReceivedMessage receivedMessage) {
    ChatMessage chatMessage = receivedMessage.getChatMessage();
    messageRepository.save(chatMessage);
    return new OkResponse();
  }
}





