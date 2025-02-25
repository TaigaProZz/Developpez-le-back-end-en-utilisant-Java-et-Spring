package com.example.demo.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.user.service.UserService;

@RestController
public class UserController {

  @Autowired
  private UserService UserService;
  

}
