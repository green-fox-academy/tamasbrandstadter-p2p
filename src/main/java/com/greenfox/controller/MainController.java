package com.greenfox.controller;

import com.greenfox.model.ChatMessage;
import com.greenfox.model.Log;
import com.greenfox.model.OkResponse;
import com.greenfox.model.User;
import com.greenfox.service.ErrorMessage;
import com.greenfox.repository.MessageRepository;
import com.greenfox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
  private String errorTextOnWebPage;
  private static String logLevel = System.getenv("CHAT_APP_LOGLEVEL");
  private final String URI = System.getenv("CHAT_APP_PEER_ADDRESS")+"/api/message/receive";
  private static String clientId = System.getenv("CHAT_APP_UNIQUE_ID");
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private MessageRepository messageRepository;

  @RequestMapping("/")
  public String main(Model model) {
    Log log = new Log("/", "REQUEST", "");
    if (!logLevel.equals("ERROR")) {
      log.setLogLevel(logLevel);
      System.out.println(log.toString());
    }
    if (userRepository.count() == 0) {
      return "redirect:/enter";
    } else {
      model.addAttribute("errorTextOnWebPage", errorTextOnWebPage);
      model.addAttribute("userName", userRepository.findOne((long) 1).getName());
      model.addAttribute("messageList", messageRepository.findAll());
      return "index";
    }
  }

  @RequestMapping("/update")
  public String update(@RequestParam("newName") String newName) {
    Log log = new Log("/update", "REQUEST", "newname=" + newName);
    if (!logLevel.equals("ERROR")) {
      log.setLogLevel(logLevel);
      System.out.println(log.toString());
    }
    if (newName.isEmpty()) {
      errorTextOnWebPage = "The username field is empty.";
      return "redirect:/";
    } else {
      User user = userRepository.findOne((long) 1);
      updateExecute(user, newName);
      errorTextOnWebPage = "";
      return "redirect:/";
    }
  }

  @PutMapping("/update/execute")
  public void updateExecute(User user, String newName) {
    Log log = new Log("/update/execute", "PUT", "");
    if (!logLevel.equals("ERROR")) {
      log.setLogLevel(logLevel);
      System.out.println(log.toString());
    }
    user.setName(newName);
    userRepository.save(user);
  }

  @PostMapping("/send")
  public String send(@RequestParam("message") String message) {
    ChatMessage chatMessage = new ChatMessage(userRepository.findOne((long)1).getName(), message);
    messageRepository.save(chatMessage);
    RestTemplate restTemplate = new RestTemplate();
    Map<String, String> uriParams = new HashMap<>();
    uriParams.put("text", chatMessage.getMessage());
    uriParams.put("username", chatMessage.getUserName());
    uriParams.put("id", String.valueOf(chatMessage.getId()));
    uriParams.put("timestamp", String.valueOf(chatMessage.getTimestamp()));
    uriParams.put("id", clientId);
    restTemplate.postForObject(URI, chatMessage, OkResponse.class, uriParams);
    return "redirect:/";
  }

  @ExceptionHandler(Exception.class)
  public ErrorMessage showError(Exception e) {
    System.err.println(e.getMessage());
    return new ErrorMessage("Error.");
  }
}
