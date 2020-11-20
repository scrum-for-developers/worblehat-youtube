package de.codecentric.psd.worblehat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/** Controller for Navigation */
@Controller
public class NavigationController {

  @GetMapping(value = "/")
  public String home() {
    return "home";
  }
}
