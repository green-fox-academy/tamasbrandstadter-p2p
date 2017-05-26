package com.greenfox.controller;

import com.greenfox.model.ChatMessage;
import com.greenfox.model.User;
import com.greenfox.repository.MessageRepository;
import com.greenfox.repository.UserRepository;
import com.greenfox.service.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class RegisterController {
  private final UserRepository userRepository;
  private final MessageRepository messageRepository;
  private String errorTextOnWebPage;

  @Autowired
  public RegisterController(UserRepository userRepository, MessageRepository messageRepository) {
    this.userRepository = userRepository;
    this.messageRepository = messageRepository;
  }

  @RequestMapping("/enter")
  public String register(Model model, HttpServletRequest request) {
    Logger.showLogWithOutParameter(request);
    if (userRepository.count() > 0) {
      return "redirect:/";
    } else {
      model.addAttribute("error", errorTextOnWebPage);
      return "enter";
    }
  }

  @PostMapping("/enter/add")
  public String addNewUser(HttpServletRequest request, @RequestParam("name") String name) {
    Logger.showLog(request, "name=" + name);
    if (name.isEmpty()) {
      errorTextOnWebPage = "The username field is empty.";
      return "redirect:/enter";
    } else {
      userRepository.save(new User(name));
      messageRepository.save(new ChatMessage("APP", "Hi there! Submit your message using send button."));
      return "redirect:/";
    }
  }
}
