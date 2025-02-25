package com.example.demo.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.message.model.Message;
import com.example.demo.message.service.MessageService;

@RestController
@RequestMapping("/messages")
public class MessageController {

  @Autowired
  private MessageService messageService;
  
  @GetMapping(path = "/")
  public @ResponseBody Iterable<Message> getAllUsers() {
      return this.messageService.getAllMessages();
  }
}
