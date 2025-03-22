package com.trackdatcert.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FrontendController {
    @GetMapping("/")
    public String trackCertPage(Model model) {
        model.addAttribute("message", "Hello, Thymeleaf!");
        return "track-certs";
    }
}
