package com.example.demo.message.service;

import com.example.demo.message.dto.SendMessageDto;
import com.example.demo.message.dto.SendMessageResponseDto;
import com.example.demo.rent.service.RentService;
import org.springframework.stereotype.Service;

import com.example.demo.message.model.Message;
import com.example.demo.message.repository.MessageRepository;

@Service
public class MessageService {

  private final MessageRepository messageRepository;
  private final RentService rentService;

  public MessageService(MessageRepository messageRepository, RentService rentService) {
    this.messageRepository = messageRepository;
    this.rentService = rentService;
  }

  /**
   * Save a Message entity to the database.
   *
   * @param sendMessageDto the Message object to be saved
   */
  public SendMessageResponseDto saveMessage(SendMessageDto sendMessageDto) {
    rentService.getRentalDtoById(sendMessageDto.getRental_id());
    Message message = new Message();

    message.setMessage(sendMessageDto.getMessage());
    message.setUser_id(sendMessageDto.getUser_id());
    message.setRental_id(sendMessageDto.getRental_id());

    this.messageRepository.save(message);
    return new SendMessageResponseDto("Message send with success");
  }
}
