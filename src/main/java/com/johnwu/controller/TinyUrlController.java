package com.johnwu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TinyUrlController {
	
	@RequestMapping("/")
    public String index() {
        return "index.html";
    }
}
