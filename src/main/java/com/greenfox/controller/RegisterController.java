package com.greenfox.controller;

import com.greenfox.model.User;
import com.greenfox.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RegisterController {
  @Autowired
  private UserRepository userRepository;

  @RequestMapping("/enter")
  public String register(){
    return "enter";
  }

  @PostMapping("/register/add")
  public String addNewUser(@RequestParam("name") String name){
    userRepository.save(new User(name));
    return "redirect:/register/";
  }
}
