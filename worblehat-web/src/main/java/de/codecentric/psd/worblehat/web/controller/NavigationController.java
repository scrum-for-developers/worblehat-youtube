package de.codecentric.psd.worblehat.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/** Controller for Navigation */
@Controller
public class NavigationController {

  @GetMapping(value = "/")
  public String home() {
    return "home";
  }
}
