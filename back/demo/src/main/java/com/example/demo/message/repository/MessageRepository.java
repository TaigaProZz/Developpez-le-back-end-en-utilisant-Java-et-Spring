package com.example.demo.message.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.message.model.Message;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>  {
  
}
