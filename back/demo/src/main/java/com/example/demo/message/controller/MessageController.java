package com.example.demo.message.controller;

import com.example.demo.message.dto.SendMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.message.model.Message;
import com.example.demo.message.service.MessageService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }
  
  /**
   * Handles the HTTP POST request to create and save a new message.
   *
   * @param sendMessageDto the data transfer object containing the message details
   * such as the message content, user ID, and rental ID
   * @return a ResponseEntity containing a success response message
   */
  @PostMapping()
  public ResponseEntity<?> sendMessage(@RequestBody SendMessageDto sendMessageDto) {
    Message message = new Message();

    message.setMessage(sendMessageDto.getMessage());
    message.setUser_id(sendMessageDto.getUser_id());
    message.setRental_id(sendMessageDto.getRental_id());

    messageService.saveMessage(message);

    Map<String, Object> response = new HashMap<>();
    response.put("message", "Message send with success");
    return ResponseEntity.ok(response);
  }
}
