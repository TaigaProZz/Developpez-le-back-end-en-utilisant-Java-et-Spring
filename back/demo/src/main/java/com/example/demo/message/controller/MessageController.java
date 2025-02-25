package com.example.demo.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.message.model.Message;
import com.example.demo.message.service.MessageService;

@Controller
public class MessageController {

  @Autowired
  private MessageService messageService;
  
  @GetMapping(path = "/")
  public @ResponseBody Iterable<Message> getAllUsers() {
      return this.messageService.getAllMessages();
  }
}
