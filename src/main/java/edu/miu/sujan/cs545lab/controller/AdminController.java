package edu.miu.sujan.cs545lab.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admins")
public class AdminController {

  @GetMapping
  public String getAdmin() {
    return "This is admin";
  }
}
