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

  /**
   * Save a Message entity to the database.
   *
   * @param message the Message object to be saved
   * @return the saved Message object
   */
  public Message saveMessage(Message message) {
    return this.messageRepository.save(message);
  }

}
