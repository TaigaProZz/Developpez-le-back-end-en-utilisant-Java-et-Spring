package com.example.demo.message.service;

import com.example.demo.message.dto.SendMessageDto;
import com.example.demo.rent.dto.GetRentalDto;
import com.example.demo.rent.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.message.model.Message;
import com.example.demo.message.repository.MessageRepository;

import lombok.Data;

@Data
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
   * @return the saved Message object
   */
  public Message saveMessage(SendMessageDto sendMessageDto) {
    rentService.getRentalDtoById(sendMessageDto.getRental_id());
    System.out.println(sendMessageDto);
    Message message = new Message();

    message.setMessage(sendMessageDto.getMessage());
    message.setUser_id(sendMessageDto.getUser_id());
    message.setRental_id(sendMessageDto.getRental_id());

    return this.messageRepository.save(message);
  }

}
