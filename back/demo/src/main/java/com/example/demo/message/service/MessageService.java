package com.example.demo.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.message.model.Message;
import com.example.demo.message.repository.MessageRepository;

import lombok.Data;

@Data
@Service
public class MessageService {
  @Autowired
  private MessageRepository messageRepository;


  public Iterable<Message> getAllMessages() {
    return this.messageRepository.findAll();
  }

}
