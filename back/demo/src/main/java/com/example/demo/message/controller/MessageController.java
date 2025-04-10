package com.example.demo.message.controller;

import com.example.demo.message.dto.SendMessageDto;
import com.example.demo.message.dto.SendMessageResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.message.service.MessageService;

import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

  private final MessageService messageService;

  public MessageController(MessageService messageService) {
    this.messageService = messageService;
  }

  // POST method to save message
  @PostMapping()
  public ResponseEntity<SendMessageResponseDto> sendMessage(@RequestBody SendMessageDto sendMessageDto) {
    // save message to db
    SendMessageResponseDto sendMessageResponseDto = messageService.saveMessage(sendMessageDto);

    return ResponseEntity.ok(sendMessageResponseDto);
  }
}
