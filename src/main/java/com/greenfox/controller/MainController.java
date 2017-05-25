package com.greenfox.controller;

import com.greenfox.model.*;
import com.greenfox.service.Broadcast;
import com.greenfox.repository.MessageRepository;
import com.greenfox.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
  private String errorTextOnWebPage;
  private static String logLevel = System.getenv("CHAT_APP_LOGLEVEL");
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private MessageRepository messageRepository;
  @Autowired
  private Broadcast broadcast;

  @RequestMapping("/")
  public String main(Model model) {
    Log log = new Log("/", "REQUEST", "");
    if (!logLevel.equals("ERROR")) {
      log.showLog();
    }
    if (userRepository.count() == 0) {
      return "redirect:/enter";
    } else {
      model.addAttribute("error", errorTextOnWebPage);
      model.addAttribute("userName", userRepository.findOne((long) 1).getName());
      model.addAttribute("messageList", messageRepository.findAll());
      return "index";
    }
  }

  @RequestMapping("/update")
  public String update(@RequestParam("newName") String newName) {
    Log log = new Log("/update", "REQUEST", "newname=" + newName);
    if (!logLevel.equals("ERROR")) {
      log.showLog();
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
      log.showLog();
    }
    user.setName(newName);
    userRepository.save(user);
  }

  @PostMapping("/send")
  public String send(@RequestParam("message") String message) {
    Log log = new Log("/send", "REQUEST", "message=" + message);
    if (!logLevel.equals("ERROR")) {
      log.showLog();
    }
    ChatMessage chatMessage = new ChatMessage(userRepository.findOne((long) 1).getName(), message);
    messageRepository.save(chatMessage);
    broadcast.broadcastMessage(chatMessage);
    return "redirect:/";
  }
}
