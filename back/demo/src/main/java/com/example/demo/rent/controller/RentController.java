package com.example.demo.rent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.example.demo.rent.service.RentService;

@Controller
public class RentController {

  @Autowired
  private RentService rentService;
  

}
