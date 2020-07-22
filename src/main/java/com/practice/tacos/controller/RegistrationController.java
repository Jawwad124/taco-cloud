package com.practice.tacos.controller;

import com.practice.tacos.form.RegistrationForm;
import com.practice.tacos.model.repository.UserRepository;
import com.practice.tacos.model.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/register")
public class RegistrationController
{
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;

  @GetMapping
  public String getRegistrationForm()
  {
    return "registration";
  }

  @PostMapping
  public String registerUser(RegistrationForm registrationForm)
  {
    userRepository.save(registrationForm.toUser(passwordEncoder));
    return "redirect:/login";
  }
}
