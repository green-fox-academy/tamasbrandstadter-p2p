package com.greenfox.controller;

import com.greenfox.model.User;
import com.greenfox.service.ErrorMessage;
import com.greenfox.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
  @Autowired
  private UserRepository userRepository;
  private String error;

  @RequestMapping("/enter")
  public String register(Model model) {
    if (userRepository.count() > 0) {
      return "redirect:/";
    } else {
      model.addAttribute("error", error);
      return "enter";
    }
  }

  @PostMapping("/enter/add")
  public String addNewUser(Model model, @RequestParam("name") String name){
    if (name.isEmpty()) {
      error = "The username field is empty.";
      model.addAttribute("error", error);
      return "redirect:/enter";
    } else {
      userRepository.save(new User(name));
      error = "";
      model.addAttribute("error", error);
      return "redirect:/";
    }
  }

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ErrorMessage showError(MissingServletRequestParameterException e) {
    return new ErrorMessage("The username field is empty.");
  }
}
