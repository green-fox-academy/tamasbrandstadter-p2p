package com.greenfox.controller;

import com.greenfox.model.User;
import com.greenfox.service.ErrorMessage;
import com.greenfox.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
  @Autowired
  private UserRepository userRepository;
  private String error;

  @RequestMapping("/")
  public String main(Model model) {
    if (userRepository.count() == 0) {
      return "redirect:/enter";
    } else {
      model.addAttribute("error", error);
      return "index";
    }
  }

  @RequestMapping("/update")
  public String update(Model model, @RequestParam("newName") String newName) {
    if (newName.isEmpty()) {
      error = "The username field is empty.";
      return "redirect:/";
    } else {
      User user = userRepository.findOne((long)1);
      updateExecute(user, newName);
      error = "";
      return "redirect:/";
    }
  }

  @PutMapping("/update/execute")
  public void updateExecute(User user, String newName) {
    user.setName(newName);
    userRepository.save(user);
  }

  @ExceptionHandler(Exception.class)
  public ErrorMessage showError(Exception e) {
    System.err.println(e.getMessage());
    return new ErrorMessage("Error.");
  }
}
