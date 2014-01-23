package org.taskstodo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class IndexController {
  @RequestMapping(value = "/")
  public String getIndex(ModelMap model) {  
//    return "forward:/tasks/list";  
    return "index";
  }
}
