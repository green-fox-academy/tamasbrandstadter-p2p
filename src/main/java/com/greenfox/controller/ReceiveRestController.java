package com.greenfox.controller;

import com.greenfox.model.*;
import com.greenfox.repository.MessageRepository;
import com.greenfox.service.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReceiveRestController {
  private static String logLevelEnv = System.getenv("CHAT_APP_LOGLEVEL");
  @Autowired
  private MessageRepository messageRepository;

  @Autowired
  private MessageValidator messageValidator;

  @PostMapping("/api/message/receive")
  @CrossOrigin("*")
  public Object receiveMessage(@RequestBody ReceivedMessage receivedMessage) {
    Log log = new Log("/api/message/receive", "POST", "");
    if (!logLevelEnv.equals("ERROR")) {
      log.setLogLevel(logLevelEnv);
      System.out.println(log.toString());
    }
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





