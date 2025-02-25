package com.example.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.user.service.UserService;

@Controller
public class UserController {

  @Autowired
  private UserService UserService;
  

}
