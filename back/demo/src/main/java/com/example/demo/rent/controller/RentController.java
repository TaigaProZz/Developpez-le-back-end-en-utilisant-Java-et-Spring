package com.example.demo.rent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.rent.service.RentService;

@RestController
public class RentController {

  @Autowired
  private RentService rentService;
  

}
